package com.toshiwa.Model.Notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationRes {
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("data")
    @Expose
    private NotificationResult notificationResult;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public NotificationResult getNotificationResult() {
        return notificationResult;
    }

    public void setData(NotificationResult notificationResult) {
        this.notificationResult = notificationResult;
    }
}
