package xyz.ixiaoban.bigdata.spark

import org.apache.spark.{SparkContext, SparkConf}
import xyz.ixiaoban.bigdata.utils.LogLevelSetter

/**
 * Created by Administrator on 2015-11-30.
 */
//job提交完整语法
/**
 * bin/spark-submit --class org.apache.spark.examples.SparkPi \
    --master yarn-cluster \
    --num-executors 3 \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 1 \
    --queue thequeue \
    lib/spark-examples*.jar \
    10
 *
 */

//提交到standalone deploy模式
// bin/spark-submit --class xyz.ixiaoban.bigdata.spark.WordCount --master spark://hdp-node-01:7077 ~/sparktest-1.0-SNAPSHOT.jar

//提交为yarn-cluster模式
// bin/spark-submit --class xyz.ixiaoban.bigdata.spark.WordCount --master yarn-cluster ~/sparktest-1.0-SNAPSHOT.jar
//提交为yarn-client模式

object WordCount {
  def main(args: Array[String]) {
    val conf = new SparkConf()
    conf.setAppName("wc")
    val sc = new SparkContext(conf)
    val res = sc.textFile("hdfs://hdp-node-01:9000/somewords.txt").flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    res.saveAsTextFile("hdfs://hdp-node-01:9000/sparkwc/result")
//    res.collect.foreach(x=>println(x))
  }
}
