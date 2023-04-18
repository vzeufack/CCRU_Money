/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.model.State;
import app.model.Status;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vannel
 */
public class StatusQueries {
    private static final String URL = "jdbc:derby:.\\db\\ccrumoney";
    private static final String USERNAME = "ccru";
    private static final String PASSWORD = "ccru";
    
    private Connection connection;
    private PreparedStatement insertNewStatus;
    private PreparedStatement selectAllStatus;
    private PreparedStatement deleteStatusQuery;
    private PreparedStatement editStatusQuery;
   
    public StatusQueries() {
      try {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
         
        insertNewStatus = connection.prepareStatement("INSERT INTO status (statusDate, state, memberId) VALUES (?, ?, ?)");
        selectAllStatus = connection.prepareStatement("SELECT * FROM status WHERE memberId = ? ORDER BY statusDate");
        deleteStatusQuery = connection.prepareStatement("DELETE FROM status WHERE id = ?");
        editStatusQuery = connection.prepareStatement("UPDATE status SET statusDate = ?, state = ? WHERE id = ?");
      } 
      catch (SQLException sqlException) {
        sqlException.printStackTrace();
        System.exit(1);
      } 
    }
    
    public int addStatus(LocalDate date, State state, String memberId) {
      try {
        insertNewStatus.setDate(1, Date.valueOf(date));
        insertNewStatus.setInt(2, state.ordinal());
        insertNewStatus.setString(3, memberId);

        return insertNewStatus.executeUpdate();         
      }
      catch (SQLException sqlException) {
        sqlException.printStackTrace();
        return 0;
      }
    }
    
    public int editStatus(Status updatedStatus) {
      try {        
        editStatusQuery.setDate(1, Date.valueOf(updatedStatus.getDate()));
        editStatusQuery.setInt(2, updatedStatus.getState().ordinal());
        editStatusQuery.setLong(3, updatedStatus.getId());

        return editStatusQuery.executeUpdate();         
      }
      catch (SQLException sqlException) {
        sqlException.printStackTrace();
        return 0;
      }
    }
    
    public List<Status> getAllStatus(String memberId) {
      try{
         selectAllStatus.setString(1, memberId);
         ResultSet resultSet = selectAllStatus.executeQuery();
         List<Status> results = new ArrayList<>();
         
         while (resultSet.next()) {
            results.add(new Status(
               resultSet.getLong("id"),
               resultSet.getDate("statusDate").toLocalDate(),
               State.values()[resultSet.getInt("state")],
               resultSet.getString("memberId")));
         }

         return results;
      }
      catch (SQLException sqlException) {
         sqlException.printStackTrace();         
      }
      
      return null;
   }
    
    public int deleteStatus(Long id){
        try {
            deleteStatusQuery.setLong(1, id);
            return deleteStatusQuery.executeUpdate();         
        }
        catch (SQLException sqlException) {
          sqlException.printStackTrace();
          return 0;
        }
    }
}
