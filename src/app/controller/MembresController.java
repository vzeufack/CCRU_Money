package app.controller;

import app.model.Membre;
import app.model.Role;
import app.model.State;
import app.model.Status;
import app.model.Voice;
import app.repository.MembreQueries;
import app.repository.StatusQueries;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MembresController {
    private final MembreQueries memberQueries = new MembreQueries();
    private final StatusQueries statusQueries = new StatusQueries();
    private String currentMemberId;

    @FXML
    private GridPane addMemberGridPane;
    
    @FXML
    private GridPane showMemberGridPane;

    @FXML
    private GridPane memberGridPane;
    
    @FXML
    private Label idLabel;
    
    @FXML
    private TableView<Status> statusListTableView;
    
    @FXML
    private TableColumn<Status, String> statusIDTableColumn;
    
    @FXML
    private TableColumn<Status, String> stateTableColumn;

    @FXML
    private TableColumn<Status, String> dateTableColumn;

    @FXML
    private TableColumn<Status, Void> statusActionTableColumn;
    
    @FXML
    private Label lastNameLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label voiceLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label initialBalanceLabel;
    
    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField emailTextField;
    
    @FXML
    private TableView<Membre> memberListTableView;
    
    @FXML
    private TableColumn<Membre, String> idTableColumn;
    
    @FXML
    private TableColumn<Membre, String> lastNameTableColumn;

    @FXML
    private TableColumn<Membre, String> firstNameTableColumn;

    @FXML
    private TableColumn<Membre, String> phoneNumberTableColumn;

    @FXML
    private TableColumn<Membre, String> VoiceTableColumn;
    
    @FXML
    private TableColumn<Membre, Void> actionTableColumn;

    @FXML
    private ChoiceBox<Role> roleChoiceBox;
    private Role role = Role.CHORISTE;

    @FXML
    private ChoiceBox<Voice> voiceChoiceBox;
    private Voice voice;
    
    @FXML
    private Button submitMemberBtn;
    
    @FXML
    private TextField idTextField;
    
    @FXML
    private ChoiceBox<State> initialStateChoiceBox;
    private State initialState = State.ACTIF;

    @FXML
    private TextField initialBalanceTextField;
    
    private final StatusController statusController = new StatusController();
    int selectedStatusIndex = 0;
    LocalDate statusDate = LocalDate.now();
    private final DatePicker datePicker = new DatePicker(LocalDate.now());
    private final ChoiceBox statusChoiceBox = new ChoiceBox();
    Stage popup = new Stage();
    Button addStatusBtn = new Button();
    Status currentStatus;
    
    public void initialize(){
        roleChoiceBox.getItems().setAll(Role.values());
        roleChoiceBox.getSelectionModel().selectFirst();
        voiceChoiceBox.getItems().add(null);
        voiceChoiceBox.getItems().addAll(Voice.values());
        initialStateChoiceBox.getItems().setAll(State.values());
        initialStateChoiceBox.getSelectionModel().selectFirst();
        
        roleChoiceBox.setOnAction((event) -> {
            role = (Role)roleChoiceBox.getSelectionModel().getSelectedItem();
        });
        
        voiceChoiceBox.setOnAction((event) -> {
            voice = (Voice)voiceChoiceBox.getSelectionModel().getSelectedItem();
        });
        
        initialStateChoiceBox.setOnAction((event) -> {
            initialState = (State)initialStateChoiceBox.getSelectionModel().getSelectedItem();
        });
        
        
        //setup member table view table
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        phoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        VoiceTableColumn.setCellValueFactory(new PropertyValueFactory<>("voice"));
        updateListOfMembers();
        addButtonToTable();
        
        initStatusTable();
        
        //setup status table view
        statusIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        stateTableColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        addActionButtonsToStatusList();
    }
    
    void initStatusTable(){
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Ajouter un status");
        
        statusChoiceBox.getItems().add("Actif");
        statusChoiceBox.getItems().add("Inactif");
        statusChoiceBox.getSelectionModel().select(0);

        statusChoiceBox.setOnAction((e) -> {
            selectedStatusIndex = statusChoiceBox.getSelectionModel().getSelectedIndex();
        });
        
        datePicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                statusDate = datePicker.getValue();
            }
        });
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setPrefSize(300, 100);
        grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        grid.add(new Label("Status:"), 0, 0);
        grid.add(statusChoiceBox, 1, 0);
        grid.add(new Label("Date:"), 0, 1);
        grid.add(datePicker, 1, 1);
        
        grid.add(addStatusBtn, 1, 2);
        
        addStatusBtn.setOnAction((ActionEvent ev) -> {
            if(addStatusBtn.getText().equals("Ajouter"))
                statusController.addStatus(statusDate, State.values()[selectedStatusIndex], currentMemberId);
            else
                statusController.editStatus(new Status(currentStatus.getId(), statusDate, State.values()[selectedStatusIndex], currentMemberId));
            updateListOfStatus();
            popup.close();
        });
        
        grid.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(grid);
        popup.setScene(scene1);
    }
    
    /*private String getMemberInfoAsString(Membre member){
        StringBuilder info = new StringBuilder();
        info.append(String.format("%s %s", member.getLastName(), member.getFirstName()));
        if(member.getPhoneNumber() != null)
            info.append(String.format("\n%s", member.getPhoneNumber()));
        info.append(String.format("\n%s", member.getRole()));
        if(member.getVoice() != null)
            info.append(String.format("\n%s", member.getVoice()));
        if(member.getCountry() != null)
            info.append(String.format("\n%s", member.getCountry()));
        if(member.getCity() != null)
            info.append(String.format("\n%s", member.getCity()));
        if(member.getAddress() != null)
            info.append(String.format("\n%s", member.getAddress()));
        if(member.getEmail() != null)
            info.append(String.format("\n%s", member.getEmail()));
        
        return info.toString();
    }*/
    
    @FXML
    void displayAddStatusForm(ActionEvent event){
        addStatusBtn.setText("Ajouter");
        popup.showAndWait();
    }
    
    void showEditStatusForm(Status status){
        datePicker.setValue(status.getDate());
        statusChoiceBox.getSelectionModel().select(status.getState().ordinal());
        addStatusBtn.setText("Modifier");
        popup.showAndWait();
    }
    
    void showStatusDeletionConfirmation(Status status){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer status " + status.getState());
        alert.setContentText("Etes-vous sûr?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            statusQueries.deleteStatus(status.getId());
            updateListOfStatus();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
    
    void displayMemberDetails(Membre member){
        currentMemberId = member.getId();
        updateListOfStatus();
        
        idLabel.setText(member.getId());
        lastNameLabel.setText(member.getLastName());
        firstNameLabel.setText(member.getFirstName());
        phoneNumberLabel.setText(member.getPhoneNumber());
        roleLabel.setText(member.getRole().name());
        voiceLabel.setText(member.getVoice().name());
        countryLabel.setText(member.getCountry());
        cityLabel.setText(member.getCity());
        addressLabel.setText(member.getAddress());
        emailLabel.setText(member.getEmail());
        initialBalanceLabel.setText(member.getInitialBalance() + "");
        
        showMemberGridPane.toFront();
    }
    
    void updateListOfMembers(){
       List<Membre> allMembers = memberQueries.getAllMembers();
       ObservableList<Membre> list = FXCollections.observableArrayList(allMembers);
       memberListTableView.setItems(list);
    }
    
    void updateListOfStatus(){
        List<Status> allStatus = statusQueries.getAllStatus(currentMemberId);
        ObservableList<Status> list = FXCollections.observableArrayList(allStatus);
        statusListTableView.setItems(list);
    }

    @FXML
    void addMemberButtonPressed(ActionEvent event) {
       clearMemberCreationForm();
       idTextField.setEditable(true);
       submitMemberBtn.setText("Créer");
       addMemberGridPane.toFront(); 
    }
    
    void clearMemberCreationForm(){
        idTextField.clear();
        idTextField.setEditable(true);
        lastNameTextField.clear();
        firstNameTextField.clear();
        phoneNumberTextField.clear();
        roleChoiceBox.getSelectionModel().selectFirst();
        voiceChoiceBox.getSelectionModel().select(null);
        countryTextField.clear();
        cityTextField.clear();
        addressTextField.clear();
        emailTextField.clear();
        initialBalanceTextField.clear();
    }
    
    @FXML
    void showMemberList(ActionEvent event){
       memberGridPane.toFront(); 
    }
    
    void showDeletionConfirmation(Membre member){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer " + member.getId());
        alert.setContentText("Etes-vous sûr?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            memberQueries.deleteMembre(member.getId());
            updateListOfMembers();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
    
    void showEditMemberForm(Membre member) {
        idTextField.setText(member.getId());
        idTextField.setEditable(false);
        lastNameTextField.setText(member.getLastName());
        firstNameTextField.setText(member.getFirstName());
        phoneNumberTextField.setText(member.getPhoneNumber());
        roleChoiceBox.getSelectionModel().select(member.getRole());
        voiceChoiceBox.getSelectionModel().select(member.getVoice());
        countryTextField.setText(member.getCountry());
        cityTextField.setText(member.getCity());
        addressTextField.setText(member.getAddress());
        emailTextField.setText(member.getEmail());
        initialBalanceTextField.setText(member.getInitialBalance() + "");
        
        submitMemberBtn.setText("Modifier");
        addMemberGridPane.toFront(); 
    }
    
    @FXML
    void createMember(ActionEvent event) {
        double balance = 0.0f;
        try{
            balance = Double.parseDouble(initialBalanceTextField.getText());
        }
        catch(NumberFormatException e){}
        
        if(submitMemberBtn.getText().equalsIgnoreCase("Créer")){
            memberQueries.addMembre(
                idTextField.getText(), lastNameTextField.getText(), firstNameTextField.getText(), phoneNumberTextField.getText(),
                role, voice, countryTextField.getText(), cityTextField.getText(), addressTextField.getText(),
                emailTextField.getText(), balance);
            
            statusQueries.addStatus(LocalDate.now(), initialState, idTextField.getText());
        }
        else{
            memberQueries.editMembre(
                idTextField.getText(), lastNameTextField.getText(), firstNameTextField.getText(), phoneNumberTextField.getText(),
                role, voice, countryTextField.getText(), cityTextField.getText(), addressTextField.getText(),
                emailTextField.getText(), balance);
        }
        
        List<Membre> allMembers = memberQueries.getAllMembers();
        ObservableList<Membre> list = FXCollections.observableArrayList(allMembers);
        memberListTableView.setItems(list);
        memberGridPane.toFront();
    }
    
    private void addButtonToTable() {
        Callback<TableColumn<Membre, Void>, TableCell<Membre, Void>> cellFactory = new Callback<TableColumn<Membre, Void>, TableCell<Membre, Void>>() {
            @Override
            public TableCell<Membre, Void> call(final TableColumn<Membre, Void> param) {
                final TableCell<Membre, Void> cell;
                cell = new TableCell<Membre, Void>() {
                    private final Button readBtn = new Button("R");
                    {
                        readBtn.setOnAction((ActionEvent event) -> {
                            Membre data = getTableView().getItems().get(getIndex());
                            displayMemberDetails(data);
                        });
                    }
                    
                    private final Button updateBtn = new Button("U");
                    {
                        updateBtn.setOnAction((ActionEvent event) -> {
                            Membre data = getTableView().getItems().get(getIndex());
                            showEditMemberForm(data);
                        });
                    }
                    
                    private final Button deleteBtn = new Button("D");
                    {
                        deleteBtn.setOnAction((ActionEvent event) -> {
                            Membre data = getTableView().getItems().get(getIndex());
                            showDeletionConfirmation(data);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        HBox hbox = new HBox(10,readBtn, updateBtn, deleteBtn);
                        hbox.setAlignment(Pos.CENTER);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hbox);
                        }
                    }
                };
                return cell;
            }
        };

        actionTableColumn.setCellFactory(cellFactory);
    }
    
    private void addActionButtonsToStatusList() {
        Callback<TableColumn<Status, Void>, TableCell<Status, Void>> cellFactory = new Callback<TableColumn<Status, Void>, TableCell<Status, Void>>() {
            @Override
            public TableCell<Status, Void> call(final TableColumn<Status, Void> param) {
                final TableCell<Status, Void> cell;
                cell = new TableCell<Status, Void>() {                    
                    private final Button updateBtn = new Button("U");
                    {
                        updateBtn.setOnAction((ActionEvent event) -> {
                            currentStatus = getTableView().getItems().get(getIndex());
                            showEditStatusForm(currentStatus);
                        });
                    }
                    
                    private final Button deleteBtn = new Button("D");
                    {
                        deleteBtn.setOnAction((ActionEvent event) -> {
                            currentStatus = getTableView().getItems().get(getIndex());
                            showStatusDeletionConfirmation(currentStatus);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        HBox hbox = new HBox(10, updateBtn, deleteBtn);
                        hbox.setAlignment(Pos.CENTER);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hbox);
                        }
                    }
                };
                return cell;
            }
        };

        statusActionTableColumn.setCellFactory(cellFactory);
    }
}