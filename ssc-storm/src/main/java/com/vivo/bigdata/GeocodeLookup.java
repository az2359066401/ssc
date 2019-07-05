package com.vivo.bigdata;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class GeocodeLookup  extends BaseBasicBolt {

    //GeocodeLookup 是继承BaseBasicBolt 的子类
    private Geocoder geocoder;


    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {

        String address = tuple.getStringByField("address");
        Long time = tuple.getLongByField("time");

        GeocoderRequestBuilder request = new GeocoderRequestBuilder.Builder()
                .setAddress(address)
                .setLanguage("en")
                .build();

        //使用元组中的地址数据查询谷歌地图的解析接口
        GeocodeResponse response = geocoder.geocode(request);
        GeocoderStatus status =  response.getStatus();

        if (GeocoderStatus.OK.equals(status)){//基于时间发射由谷歌地图解析接口返回的第一个结果
            GeocoderResult firstResult  = geocoder.getResults().get(0);
            String latLng = firstResult.getGeometry().getLocation();
            basicOutputCollector.emit(new Values(time,latLng));
        }



    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        //通知 Strom 此 bolt 会发射两个字段  时间和坐标
            outputFieldsDeclarer.declare(new Fields("time","geocode"));
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        //初始化 谷歌 geocode
    }

}
