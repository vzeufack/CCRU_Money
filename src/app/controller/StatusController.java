/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.controller;

import app.model.Membre;
import app.model.State;
import app.model.Status;
import app.repository.StatusQueries;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vannel
 */
public class StatusController {
    private final StatusQueries statusQueries = new StatusQueries();
    
    void addStatus(LocalDate date, State state, String memberId){
        statusQueries.addStatus(date, state, memberId);        
    }
    
    void editStatus(Status updatedStatus){
        statusQueries.editStatus(updatedStatus);
    }
}
