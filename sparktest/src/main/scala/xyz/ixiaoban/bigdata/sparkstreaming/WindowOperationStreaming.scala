package xyz.ixiaoban.bigdata.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import xyz.ixiaoban.bigdata.utils.LogLevelSetter

class WindowOperationStreaming {

}

object WindowOperationStreaming {

  def main(args: Array[String]): Unit = {

    LogLevelSetter.setLevel
    
    val conf = new SparkConf
    conf.setMaster("local[2]").setAppName("windowreduce")
    val ssc = new StreamingContext(conf, Seconds(2))
    ssc.checkpoint(".")
    
    val socketDs = ssc.socketTextStream("localhost", 9999)

    val wordsDs = socketDs.flatMap { x => x.split(" ") }.map { x => (x, 1) }

    val wordcountsDs = wordsDs.reduceByKeyAndWindow(_ + _, _ - _, Seconds(6), Seconds(4))
    
    val temp = wordcountsDs.map(x=>(x._2,x._1))
    
    val sortedDs = temp.transform(_.sortByKey(false))
    
    val resultDs = sortedDs.map(x=>(x._2,x._1))
    
    resultDs.print()
    ssc.start()
    ssc.awaitTermination()

  }

}