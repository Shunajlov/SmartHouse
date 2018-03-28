package com.smartcity.smartHouse.dataModel;

import java.io.Serializable;

public class BasicAnswer implements Serializable {
    private int status = 0;
    private String text = "";

    public BasicAnswer() {
    }

    public BasicAnswer(String text) {
        this.text = text;
    }

    public BasicAnswer(int status, String text) {
        this.status = status;
        this.text = text;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
