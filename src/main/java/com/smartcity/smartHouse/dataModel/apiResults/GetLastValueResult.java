package com.smartcity.smartHouse.dataModel.apiResults;

import java.io.Serializable;

public class GetLastValueResult implements Serializable {
    public String id;
    public Integer value;

    public GetLastValueResult(String id, Integer value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
