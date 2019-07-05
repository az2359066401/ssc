package com.vivo.bigdata;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;

public class ClusterSumDemoGroupingStormTopology {


    public static class DataSourceSpout extends BaseRichSpout{


        private SpoutOutputCollector collector;

        /**
         * DataSourceSpout 需要继承BaseRichSpout
         * open 初始化方法 只调用一次
         * @param map                   配置参数
         * @param topologyContext       上下文
         * @param spoutOutputCollector  数据发射器
         */
        public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
            //指定 collector
            this.collector = spoutOutputCollector;
        }


        int number = 0 ;


        /**
         * 会产生数据  在生产上肯定是从消息队列中获取消息
         * 这是一个死循环  会一直不断执行
         */
        public void nextTuple() {

            this.collector.emit(new Values(number++));
            System.out.println("这是一个死循环  会一直不断执行 >>number " + number);


            Utils.sleep(1000);


        }

        //声明输出字段
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

            outputFieldsDeclarer.declare(new Fields("num"));

        }
    }

    public static class SumBolt extends BaseRichBolt{


        private OutputCollector collector;

        public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
            this.collector  = outputCollector;
        }

        int sum = 0;
        public void execute(Tuple tuple) {


            Integer num = (Integer) tuple.getValueByField("num");
            System.out.println("tuple 中的值为 num>> " +num );
            sum +=num;
            System.out.println("求和阶段" );


            System.out.println("当前求和为sum >>:" + sum);
        }

        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

        }
    }


    public static void main(String[] args) {
        System.out.println("构建 topology");


        TopologyBuilder topologyBuilder = new TopologyBuilder();


        topologyBuilder.setSpout("DataSourceSpout",new DataSourceSpout(),2);
        topologyBuilder.setBolt("SumBolt",new SumBolt()).shuffleGrouping("DataSourceSpout");


        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("SumDemoGroupingStomTopology",new Config(),topologyBuilder.createTopology());







    }




}
