package com.smartcity.smartHouse.dataModel.apiResults;

import java.util.ArrayList;

public class GetHousesRequest extends BasicResult {
    private ArrayList<GetHouseRequest> houses;

    public GetHousesRequest(ArrayList<GetHouseRequest> houses) {
        this.houses = houses;
    }

    public ArrayList<GetHouseRequest> getHouses() {
        return houses;
    }

    public void setHouses(ArrayList<GetHouseRequest> houses) {
        this.houses = houses;
    }
}
