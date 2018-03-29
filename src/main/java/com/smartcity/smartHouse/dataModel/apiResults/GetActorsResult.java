package com.smartcity.smartHouse.dataModel.apiResults;

import java.util.ArrayList;

public class GetActorsResult extends BasicResult {
    private ArrayList<GetActorResult> actors;

    public GetActorsResult(ArrayList<GetActorResult> actors) {
        this.actors = actors;
    }

    public ArrayList<GetActorResult> getActors() { return actors; }

    public void setActors(ArrayList<GetActorResult> actors) {
        this.actors = actors;
    }
}
