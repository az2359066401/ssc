package com.vivo.bigdata.heatmap.vo;

import java.io.Serializable;

public class Geometry  implements Serializable {

    private LatLng location;

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
