package com.gatherup.gahterup.Model;

import java.util.ArrayList;

public class ProjectModel {

    String created_userid;
    String numberofparticipant;
    String projectdescription;
    String projectname;
    ArrayList<String> projectneeds;

    public ProjectModel() {
    }

    public String getCreated_userid() {
        return created_userid;
    }

    public void setCreated_userid(String created_userid) {
        this.created_userid = created_userid;
    }

    public String getNumberofparticipant() {
        return numberofparticipant;
    }

    public void setNumberofparticipant(String numberofparticipant) {
        this.numberofparticipant = numberofparticipant;
    }

    public String getProjectdescription() {
        return projectdescription;
    }

    public void setProjectdescription(String projectdescription) {
        this.projectdescription = projectdescription;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public ArrayList<String> getProjectneeds() {
        return projectneeds;
    }

    public void setProjectneeds(ArrayList<String> projectneeds) {
        this.projectneeds = projectneeds;
    }
}
