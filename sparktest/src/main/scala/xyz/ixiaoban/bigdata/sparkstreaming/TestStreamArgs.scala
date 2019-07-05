package xyz.ixiaoban.bigdata.sparkstreaming

import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.SparkConf

import java.util.Date

/**
 * @author Administrator
 */
object TestStreamArgs {

  def main(args: Array[String]): Unit = {
    println("args....")

    var temData = "tmp11"


    val conf = new SparkConf()
    conf.setMaster("local[2]")
    conf.setAppName("streaming-test")

    val ssc = new StreamingContext(conf,Seconds(60));
    val ds =  ssc.textFileStream("hdfs://hdp-node-01:9000/wordcount/input/test.txt")

    val res = ds.map( line => {

      val now = new Date()
      val cc = now.getTime
      val sign = cc % 5
      if(3 == sign){
        temData = "333"
      }else if(1 == sign){
        temData = "111"
      }else if(2 == sign){
        temData = "222"
      }else {
        temData = "other"
      }
      val result = line + temData
      result
    }).foreachRDD(rdd=>rdd.saveAsTextFile("hdfs://hdp-node-01:9000/wordcount/sscout"))

    ssc.start()
    ssc.awaitTermination()

  }
}
