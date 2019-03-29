package com.gatherup.gahterup.Model;

public class User {

    String name;
    String surname;
    String email;
    String password;
    String birthdate;
    String universityName;
    String enteranceyear;
    String workyear;
    String duty;
    String position;
    String projectName;
    String projectDescription;



    public User() {
    }

    public User(String name, String surname, String email, String password,String birthdate,String universityName,String enteranceyear,
                String workyear,String duty,String position,String projectName,String projectDescription) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.birthdate=birthdate;
        this.universityName=universityName;
        this.enteranceyear=enteranceyear;
        this.workyear=workyear;
        this.duty=duty;
        this.position=position;
        this.projectName=projectName;
        this.projectDescription=projectDescription;

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

    public String getBirthdate(){ return birthdate; }

    public void setBirthdate(String birthdate){ this.birthdate = birthdate; }

    public String getUniversityName() { return universityName; }

    public void setUniversityName(String universityName) { this.universityName = universityName; }

    public String getEnteranceyear() { return enteranceyear; }

    public void setEnteranceyear(String enteranceyear) { this.enteranceyear = enteranceyear; }

    public String getWorkyear() { return workyear; }

    public void setWorkyear(String workyear) { this.workyear = workyear; }

    public String getDuty() { return duty; }

    public void setDuty(String duty) { this.duty = duty; }

    public String getPosition() { return position; }

    public void setPosition(String position) { this.position = position; }

    public String getProjectName() { return projectName; }

    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getProjectDescription() { return projectDescription; }

    public void setProjectDescription(String projectDescription) { this.projectDescription = projectDescription; }

}
