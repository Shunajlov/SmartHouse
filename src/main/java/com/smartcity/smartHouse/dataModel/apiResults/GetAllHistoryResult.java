package com.smartcity.smartHouse.dataModel.apiResults;

import java.util.ArrayList;

public class GetAllHistoryResult {
    private ArrayList<GetHistoryResult> events;

    public GetAllHistoryResult(ArrayList<GetHistoryResult> events) {
        this.events = events;
    }

    public ArrayList<GetHistoryResult> getEvents() {
        return events;
    }
    public void setEvents(ArrayList<GetHistoryResult> houses) {
        this.events = events;
    }
}
