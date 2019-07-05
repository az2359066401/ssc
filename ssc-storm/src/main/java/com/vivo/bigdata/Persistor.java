package com.vivo.bigdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Persistor extends BaseBasicBolt {


    private final Logger logger = LoggerFactory.getLogger(Persistor.class);

    private Jedis jedis;

    private ObjectMapper objectMapper;


    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {

        Long timeInterval = tuple.getLongByField("time-interval");
        List<LatLng> hotzones = (List<LatLng>) tuple.getValueByField("hotzones");

        try {
            String key = "checkins-" + timeInterval;
            String value = objectMapper.writeValueAsString(hotzones);
            //基于时间段将热力图的JSON写入Redis的键值
            jedis.set(key, value);
        } catch (JsonProcessingException e) {
            //此处不对任何数据库失败操作做重试 因为这是一个不可靠的流
            e.printStackTrace();
            logger.error("Error persisting for time:" + timeInterval, e);
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        //这是最后一个 bolt 所以就不会有新的元组从中发射过来  也就是说不会有新的字段需要申明了
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        jedis = new Jedis("localhost");
        objectMapper = new ObjectMapper();
    }

    //当strom停止之后  关闭与redis 的连接
    @Override
    public void cleanup() {
        //当Storm的拓扑停止之后 关闭 Redis 的连接
        if (jedis.isConnected())
            jedis.quit();
    }

    private List<String> asListOfStrings(List<LatLng> hotzones){
        List<String> hotzonesStandard = new ArrayList<String>(hotzones.size());
        for (LatLng latLng : hotzones) {
            hotzonesStandard.add(latLng.toUrlValue());
        }
        return hotzonesStandard;
    }





}
