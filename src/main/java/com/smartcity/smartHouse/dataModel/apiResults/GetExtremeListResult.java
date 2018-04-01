package com.smartcity.smartHouse.dataModel.apiResults;

import java.io.Serializable;
import java.util.ArrayList;

public class GetExtremeListResult implements Serializable {
    private ArrayList<GetExtremeResult> extremes;

    public GetExtremeListResult(ArrayList<GetExtremeResult> extremes) {
        this.extremes = extremes;
    }

    public ArrayList<GetExtremeResult> getExtremes() {
        return extremes;
    }
    public void setExtremes(ArrayList<GetExtremeResult> extremes) {
        this.extremes = extremes;
    }
}
