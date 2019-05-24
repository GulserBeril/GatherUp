package com.gatherup.gahterup.Model;

import java.util.ArrayList;

public class UserModel {

    String id;
    String name;
    String surname;
    String email;
    String password;
    String birthdate;
    String universityname;
    String entranceyear;
    ArrayList<String> abilities;
    String year;
    String duty;
    String position;
    String projectname;
    String projectdescription;
    String imageURL;


    public UserModel() {
    }

    public UserModel(String id, String name, String surname, String email, String password, String birthdate, String universityname, String entranceyear, ArrayList<String> abilities, String year, String duty, String position, String projectname, String projectdescription, String imageURL) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.universityname = universityname;
        this.entranceyear = entranceyear;
        this.abilities = abilities;
        this.year = year;
        this.duty = duty;
        this.position = position;
        this.projectname = projectname;
        this.projectdescription = projectdescription;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String name) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getUniversityname() {
        return universityname;
    }

    public void setUniversityname(String universityname) {
        this.universityname = universityname;
    }

    public ArrayList<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<String> abilities) {
        this.abilities = abilities;
    }

    public String getEntranceyear() {
        return entranceyear;
    }

    public void setEntranceyear(String entranceyear) {
        this.entranceyear = entranceyear;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getProjectdescription() {
        return projectdescription;
    }

    public void setProjectdescription(String projectdescription) {
        this.projectdescription = projectdescription;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
