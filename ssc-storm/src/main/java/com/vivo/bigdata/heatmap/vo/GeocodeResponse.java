package com.vivo.bigdata.heatmap.vo;

import com.vivo.bigdata.heatmap.constants.GeocoderStatus;

import java.io.Serializable;

public class GeocodeResponse  implements Serializable {


    private GeocoderStatus geocoderStatus;

    public GeocoderStatus getGeocoderStatus() {
        return geocoderStatus;
    }

    public void setGeocoderStatus(GeocoderStatus geocoderStatus) {
        this.geocoderStatus = geocoderStatus;
    }

}
