package com.vivo.bigdata.heatmap.bolt;

import com.alibaba.fastjson.JSON;
import com.vivo.bigdata.heatmap.vo.LatLng;
import org.apache.storm.Config;
import org.apache.storm.Constants;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.*;

public class HeatMapBuilder extends BaseBasicBolt {


    private Map<Long, List<LatLng>> heatmaps;

    //选择元组落入的时间间隔   基于时间间隔指标将经纬度坐标数据添加到列表中
    //将聚合的热力图数据基于时间维度发射
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {


        //如果我们需要调用一个心跳元组，那么需要在这里做些修改 如果是常规元组 维持原样
        System.out.println("HeatMapBuilder isTickTuple>> " + isTickTuple(tuple));
        if (isTickTuple(tuple)){
            // take periodic action
            emitHeatMap(basicOutputCollector);
        }else {
//            "time-interval","geocode","city"
            LatLng geocode = (LatLng)tuple.getValueByField("geocode");
            Long timeInterval = tuple.getLongByField("time-interval");
            String city = tuple.getStringByField("city");
            System.out.println("HeatMapBuilder geocode>> " + JSON.toJSONString(geocode));
            System.out.println("HeatMapBuilder timeInterval>> " + timeInterval);
            System.out.println("HeatMapBuilder city>> " + city);
            List<LatLng> checkins =  getCheckinsForInterval(timeInterval);

            checkins.add(geocode);
        }


    }


    //发射出去的热力图都被默认包含先于当前时间段的签到数据 这也是为什么我们当前的时间段放入方法 selectTimeInterval() 并获得返回的当前时间段初始时间点
    private void emitHeatMap(BasicOutputCollector basicOutputCollector) {
        long now = System.currentTimeMillis();
        Long emitUpToTimeInterval = selectTimeInterval(now);
        Set<Long> timeIntervalAvaible = heatmaps.keySet();
        System.out.println("HeatMapBuilder heatmaps>> " + JSON.toJSONString(heatmaps));
        System.out.println("HeatMapBuilder timeIntervalAvaible>> " + JSON.toJSONString(timeIntervalAvaible));
        System.out.println("HeatMapBuilder emitUpToTimeInterval>> " + emitUpToTimeInterval);
        for (Long timeInterval : timeIntervalAvaible) {
            if (timeInterval <= emitUpToTimeInterval){
                List<LatLng> hotzones = heatmaps.remove(timeInterval);
                System.out.println("HeatMapBuilder hotzones>> " + JSON.toJSONString(hotzones));
                basicOutputCollector.emit(new Values(timeInterval,hotzones));
            }
        }

    }

    private boolean isTickTuple(Tuple tuple) {
        String sourceComponent = tuple.getSourceComponent();
        String sourceStreamId = tuple.getSourceStreamId();
        System.out.println("HeatMapBuilder sourceComponent>> " + sourceComponent);
        System.out.println("HeatMapBuilder sourceStreamId>> " + sourceStreamId);
        //心跳元组是很好识别的 因为它依靠系统组件来实现心跳流的发射 而不是使用我们自己拓扑上实现默认流而定义的组件
        return sourceComponent.equals(Constants.SYSTEM_COMPONENT_ID) && sourceStreamId.equals(Constants.SYSTEM_TICK_STREAM_ID);
    }

    private List<LatLng> getCheckinsForInterval(Long timeInterval) {
        List<LatLng> hotzones = heatmaps.get(timeInterval);
        if (hotzones == null){
            hotzones = new ArrayList<LatLng>();
            heatmaps.put(timeInterval,hotzones);
        }
        return hotzones;
    }

    private Long selectTimeInterval(Long time) {
        return time / (15*1000);
    }


    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("time-interval","hotzones"));
    }


    //初始化内存映射
    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        heatmaps = new HashMap<Long, List<LatLng>>();
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        //重写这个方法以便更灵活地配置组件运行方式（此例中为心跳元组的频率）
        Config config = new Config();
        config.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS,1);
        return config;
    }



}
