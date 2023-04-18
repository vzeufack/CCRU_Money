/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import java.time.LocalDate;

/**
 *
 * @author vannel
 */
public class Status {
    private final Long id;
    private LocalDate date;
    private State state;
    private String memberId;

    public Status(Long id, LocalDate date, State state, String memberId) {
        this.id = id;
        this.date = date;
        this.state = state;
        this.memberId = memberId;
    }
    
    public Long getId(){
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public State getState() {
        return state;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    
    @Override
    public String toString(){
        return String.format("Date: %s, State: %s, MemberId: %s", date, state, memberId);
    }
}
