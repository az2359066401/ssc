package com.vivo.bigdata.heatmap;

import com.vivo.bigdata.heatmap.bolt.GeocodeLookup;
import com.vivo.bigdata.heatmap.bolt.HeatMapBuilder;
import com.vivo.bigdata.heatmap.bolt.Persistor;
import com.vivo.bigdata.heatmap.spoult.Checkins;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

public class HeatMapTopologyBuilder {


    public static StormTopology build() {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("checkins", new Checkins());
        builder.setBolt("geocode-loopup", new GeocodeLookup())
                .shuffleGrouping("checkins");
        builder.setBolt("heatmap-builder", new HeatMapBuilder())
                .globalGrouping("geocode-lookup");
        builder.setBolt("persistor", new Persistor())
                .shuffleGrouping("heatmap-builder");
        return builder.createTopology();
    }

}
