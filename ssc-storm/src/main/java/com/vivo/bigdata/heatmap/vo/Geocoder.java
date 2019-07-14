package com.vivo.bigdata.heatmap.vo;

import com.alibaba.fastjson.JSON;
import com.vivo.bigdata.heatmap.constants.GeocoderStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Geocoder implements Serializable {


    private List<GeocoderResult> results;


    public void setResults(List<GeocoderResult> results) {
        this.results = results;
    }


    public List<GeocoderResult> getResults() {
        return results;
    }

    //使用元组中的地址数据查询谷歌地图的解析接口
    public GeocodeResponse geocode(GeocoderRequestBuilder request) {
        System.out.println("Geocoder  request>>" + JSON.toJSONString(request));
        List<GeocoderResult> geocoderResults = new ArrayList<GeocoderResult>();
        GeocoderResult ex = new GeocoderResult();
        List<GeocoderAddressComponent> addCom = new ArrayList<GeocoderAddressComponent>();
        GeocoderAddressComponent gec = new GeocoderAddressComponent();
        gec.setLongName("longName");
        gec.setShortName("shortName");
        String[] types = new String[5];
        types[0] = "a";
        types[1] = "b";
        types[2] = "locality";
        gec.setTypes(types);
        addCom.add(gec);
        Geometry ge = new Geometry();
        LatLng latLng = new LatLng();
        ge.setLocation(latLng);

        ex.setGeometry(ge);
        ex.setAddressComponents(addCom);
        geocoderResults.add(ex);
        this.results = geocoderResults;

        GeocodeResponse geocodeResponse = new GeocodeResponse();
        geocodeResponse.setGeocoderStatus(GeocoderStatus.OK);
        return geocodeResponse;
    }


}
