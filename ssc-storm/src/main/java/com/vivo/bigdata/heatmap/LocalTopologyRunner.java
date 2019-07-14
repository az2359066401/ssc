package com.vivo.bigdata.heatmap;

import com.vivo.bigdata.heatmap.bolt.GeocodeLookup;
import com.vivo.bigdata.heatmap.bolt.HeatMapBuilder;
import com.vivo.bigdata.heatmap.bolt.Persistor;
import com.vivo.bigdata.heatmap.bolt.TimeIntervalExtractor;
import com.vivo.bigdata.heatmap.spoult.Checkins;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class LocalTopologyRunner {

    //一个包含最简单main() 方法的java 类 ，用于启动拓扑
    public static void main(String[] args) {
        //调用 Storm的默认config配置 不做任何调整
        Config config = new Config();

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("checkins", new Checkins());
        builder.setBolt("geocode-loopup", new GeocodeLookup())
                .shuffleGrouping("checkins");
        builder.setBolt("time-interval-extractor",new TimeIntervalExtractor())
                .shuffleGrouping("geocode-loopup");
        builder.setBolt("heatmap-builder", new HeatMapBuilder())
                .fieldsGrouping("time-interval-extractor",new Fields("time-interval","city"));
        builder.setBolt("persistor", new Persistor())
                .shuffleGrouping("heatmap-builder");
//        StormTopology topology = HeatMapTopologyBuilder.build();
        //创建一个本地集群   提交拓扑并且在本地集群模式中运行它
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("LocalTopologyRunner",config,builder.createTopology());

    }

}
