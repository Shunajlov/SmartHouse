package com.smartcity.smartHouse.dataModel;

import java.util.ArrayList;

public class RoomersHouseResult extends BasicAnswer {
    private ArrayList<AuthRoomerResult> roomerList;

    public RoomersHouseResult(ArrayList<AuthRoomerResult> roomerList) {
        this.roomerList = roomerList;
    }

    public ArrayList<AuthRoomerResult> getRoomerList() {
        return roomerList;
    }

    public void setRoomerList(ArrayList<AuthRoomerResult> roomerList) {
        this.roomerList = roomerList;
    }
}
