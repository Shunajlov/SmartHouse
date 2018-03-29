package com.smartcity.smartHouse.dataModel.apiResults;

import java.util.ArrayList;

public class GetUsersResult {
    private ArrayList<GetUserResult> users;

    public GetUsersResult(ArrayList<GetUserResult> users) {
        this.users = users;
    }

    public ArrayList<GetUserResult> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<GetUserResult> users) {
        this.users = users;
    }
}
