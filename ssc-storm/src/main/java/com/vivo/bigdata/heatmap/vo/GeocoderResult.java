package com.vivo.bigdata.heatmap.vo;

import java.io.Serializable;
import java.util.List;

public class GeocoderResult  implements Serializable {

    private Geometry geometry ;

    private List<GeocoderAddressComponent> addressComponents;


    public List<GeocoderAddressComponent> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<GeocoderAddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
