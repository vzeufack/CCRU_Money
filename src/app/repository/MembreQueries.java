/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.model.Membre;
import app.model.Role;
import app.model.Voice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vannel
 */
public class MembreQueries {
    private static final String URL = "jdbc:derby:.\\db\\ccrumoney";
    private static final String USERNAME = "ccru";
    private static final String PASSWORD = "ccru";
    
    private Connection connection;
    private PreparedStatement insertNewMembre;
    private PreparedStatement editMemberSQL;
    private PreparedStatement deleteMemberSQL;
    private PreparedStatement selectAllMembers;
   
    public MembreQueries() {
      try {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        
        selectAllMembers = connection.prepareStatement(
            "SELECT * FROM member ORDER BY lastName, firstName");
         
        insertNewMembre = connection.prepareStatement(         
           "INSERT INTO member " +                           
           "(id, lastName, firstName, phoneNumber, responsibility, voice, country, city, address, email, initialBalance) " +     
           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        
        editMemberSQL = connection.prepareStatement(         
           "UPDATE member " +                           
           "SET lastName = ?, firstName =?, phoneNumber = ?, responsibility = ?, voice = ?, country = ?, city = ?, address = ?, email = ?, initialBalance = ?" +     
           "WHERE id = ?");
        
        deleteMemberSQL = connection.prepareStatement("DELETE FROM member WHERE id = ?");
      } 
      catch (SQLException sqlException) {
        sqlException.printStackTrace();
        System.exit(1);
      } 
    }
    
    public int addMembre(String id, String nom, String prenom, String numero, Role role, Voice pupitre,
                         String pays, String ville, String addresse, String email, double initialBalance) {
      try {
        insertNewMembre.setString(1, id);
        insertNewMembre.setString(2, nom);
        insertNewMembre.setString(3, prenom);
        insertNewMembre.setString(4, numero);
        insertNewMembre.setInt(5, role.ordinal());
        insertNewMembre.setInt(6, pupitre != null? pupitre.ordinal():0);
        insertNewMembre.setString(7, pays);
        insertNewMembre.setString(8, ville);
        insertNewMembre.setString(9, addresse);
        insertNewMembre.setString(10, email);
        insertNewMembre.setDouble(11, initialBalance);

        return insertNewMembre.executeUpdate();         
      }
      catch (SQLException sqlException) {
        sqlException.printStackTrace();
        return 0;
      }
    }
    
    public int editMembre(String id, String nom, String prenom, String numero, Role role, Voice pupitre,
                         String pays, String ville, String addresse, String email, double initialBalance) {
      try {        
        editMemberSQL.setString(1, nom);
        editMemberSQL.setString(2, prenom);
        editMemberSQL.setString(3, numero);
        editMemberSQL.setInt(4, role.ordinal());
        editMemberSQL.setInt(5, pupitre != null? pupitre.ordinal():0);
        editMemberSQL.setString(6, pays);
        editMemberSQL.setString(7, ville);
        editMemberSQL.setString(8, addresse);
        editMemberSQL.setString(9, email);
        editMemberSQL.setDouble(10, initialBalance);
        editMemberSQL.setString(11, id);

        return editMemberSQL.executeUpdate();         
      }
      catch (SQLException sqlException) {
        sqlException.printStackTrace();
        return 0;
      }
    }
    
    public int deleteMembre(String id){
        try {
            deleteMemberSQL.setString(1, id);
            return deleteMemberSQL.executeUpdate();         
        }
        catch (SQLException sqlException) {
          sqlException.printStackTrace();
          return 0;
        }
    }
    
    public List<Membre> getAllMembers() {
      // executeQuery returns ResultSet containing matching entries
      try (ResultSet resultSet = selectAllMembers.executeQuery()) {
         List<Membre> results = new ArrayList<>();
         
         while (resultSet.next()) {
            results.add(new Membre(
               resultSet.getString("id"),
               resultSet.getString("lastName"),
               resultSet.getString("firstName"),
               resultSet.getString("phoneNumber"),
               Role.values()[resultSet.getInt("responsibility")],
               Voice.values()[resultSet.getInt("voice")],
               resultSet.getString("country"),
               resultSet.getString("city"),
               resultSet.getString("address"),
               resultSet.getString("email"),
               resultSet.getDouble("initialBalance")));
         } 

         return results;
      }
      catch (SQLException sqlException) {
         sqlException.printStackTrace();         
      }
      
      return null;
   }
}
