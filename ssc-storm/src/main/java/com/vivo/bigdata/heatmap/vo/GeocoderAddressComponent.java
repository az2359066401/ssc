package com.vivo.bigdata.heatmap.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class GeocoderAddressComponent  implements Serializable {


    private String[] types;

    private String longName;

    private String shortName;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }


    public String getTypes() {
        return JSON.toJSONString(types);
    }

    public void setTypes(String[] types) {
        this.types = types;
    }
}
