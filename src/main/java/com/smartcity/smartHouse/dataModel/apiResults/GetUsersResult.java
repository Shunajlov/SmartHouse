package com.smartcity.smartHouse.dataModel.apiResults;

import java.util.ArrayList;

public class GetUsersResult {
    private ArrayList<UserResult> users;

    public GetUsersResult(ArrayList<UserResult> users) {
        this.users = users;
    }

    public ArrayList<UserResult> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserResult> users) {
        this.users = users;
    }
}
