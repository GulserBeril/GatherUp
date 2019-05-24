package com.gatherup.gahterup.Model;

public class NotificationModel {

    String inviter;
    String inviter_name;
    String project_id;
    String project_name;
    Boolean state;
    String user_id;
    String notificationid;
    String notification_type;

    public NotificationModel() {
    }

    public String getInviter_name() {
        return inviter_name;
    }

    public void setInviter_name(String inviter_name) {
        this.inviter_name = inviter_name;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNotificationid() {
        return notificationid;
    }

    public void setNotificationid(String notificationid) {
        this.notificationid = notificationid;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }
}
