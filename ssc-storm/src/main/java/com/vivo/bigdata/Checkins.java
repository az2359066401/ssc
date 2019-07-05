package com.vivo.bigdata;

import org.apache.storm.shade.org.apache.commons.io.IOUtils;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Checkins extends BaseRichSpout {//继承 BaseRichSpout 的子类

    //使用 list 来存储 从checkins.txt 文件读取的静态数据

    private List<String> checkins;

    private int nextEmitIndex; //用于定位列表中当前的位置  因为我们稍后需要回收checkins的静态列表

    private SpoutOutputCollector outputCollector;

    //shiyong Apache 通用 IO API 从 checkins.txt 中读取行数据 让后保存至内存中的List 里
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {

        this.outputCollector = spoutOutputCollector;
        this.nextEmitIndex = 0;

        try {
            checkins = IOUtils.readLines(ClassLoader.getSystemResourceAsStream("checkins.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //索引指南下一条需要发射的条目数据  如果已经读取到文件最末端 则执行回收
    public void nextTuple() {
        //当 Storm 向 spout 请求下一个元组时 需要从内存List 中查询下一个checkins 数据 并解析为时间和地址组件
        String checkin = checkins.get(nextEmitIndex);
        String[] parts = checkin.split(",");
        Long time = Long.valueOf(parts[0]);
        String address = parts[1];
        outputCollector.emit(new Values(time, address));

        nextEmitIndex = (nextEmitIndex + 1) % checkins.size();
        //利用 在 open 方法里面提供的SpoutOutput-Collector 来发射字段至指定目标



    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

        outputFieldsDeclarer.declare(new Fields("time", "address"));//向 Storm 声明 该 Spout 会发射一个包含时间和地址两个字段的元组


    }
}
