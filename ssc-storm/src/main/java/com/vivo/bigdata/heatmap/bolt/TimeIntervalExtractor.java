package com.vivo.bigdata.heatmap.bolt;

import com.vivo.bigdata.heatmap.vo.LatLng;
import org.apache.storm.shade.org.eclipse.jetty.util.ajax.JSON;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class TimeIntervalExtractor extends BaseBasicBolt {


    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {


        Long time = tuple.getLongByField("time");
        LatLng geocode = (LatLng)tuple.getValueByField("geocode");
        String city = tuple.getStringByField("city");
        System.out.println("TimeIntervalExtractor time>>" + time + "geocode>>"  + JSON.toString(geocode) + "city>>" + city);

        Long timeInterval = time / (15 * 1000);

        basicOutputCollector.emit(new Values(timeInterval,geocode,city));



    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

        outputFieldsDeclarer.declare(new Fields("time-interval","geocode","city"));

    }


}
