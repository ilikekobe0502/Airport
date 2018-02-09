package com.whatsmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo on 2017/10/31.
 */

public class StoreQueryData {
    @SerializedName("terminalsId")
    private String terminalsId;
    @SerializedName("areaId")
    private String areaId;
    @SerializedName("floorId")
    private String floorId;
    @SerializedName("storeTypeId")
    private String storeTypeId;

    public String getTerminalsId() {
        return terminalsId;
    }

    public void setTerminalsId(String terminalsId) {
        this.terminalsId = terminalsId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(String storeTypeId) {
        this.storeTypeId = storeTypeId;
    }
}
