package com.example.healthyapp.Model.Objects;

public class ScreenSelectorRequest {
    String topic_message;

    public ScreenSelectorRequest(String topic_message) {
        this.topic_message = topic_message;
    }

    public String getTopic_message() {
        return topic_message;
    }

    public void setTopic_message(String topic_message) {
        this.topic_message = topic_message;
    }
}
