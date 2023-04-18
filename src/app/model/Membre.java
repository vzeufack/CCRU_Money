/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

/**
 *
 * @author vannel
 */
public class Membre {
    private final String id;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private Role role;
    private Voice voice;
    private String country;
    private String city;
    private String address;
    private String email;
    private double initialBalance;

    public Membre(String id, String lastName, String firstName, String phoneNumber, Role role, Voice voice, 
                  String country, String city, String address, String email, double initialBalance) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.voice = voice;
        this.country = country;
        this.city = city;
        this.address = address;
        this.email = email;
        this.initialBalance = initialBalance;
    }
    
    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public double getInitialBalance(){
        return initialBalance;
    }
    
    public void setInitialBalance(double intialBalance){
        this.initialBalance = initialBalance;
    }
}
