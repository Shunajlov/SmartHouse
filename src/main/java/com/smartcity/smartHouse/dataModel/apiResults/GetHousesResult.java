package com.smartcity.smartHouse.dataModel.apiResults;

import java.util.ArrayList;

public class GetHousesResult extends BasicResult {
    private ArrayList<GetHouseResult> houses;

    public GetHousesResult(ArrayList<GetHouseResult> houses) {
        this.houses = houses;
    }

    public ArrayList<GetHouseResult> getHouses() {
        return houses;
    }

    public void setHouses(ArrayList<GetHouseResult> houses) {
        this.houses = houses;
    }
}
