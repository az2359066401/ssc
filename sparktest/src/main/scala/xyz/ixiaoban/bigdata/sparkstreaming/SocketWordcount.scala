package xyz.ixiaoban.bigdata.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.storage.StorageLevel
import xyz.ixiaoban.bigdata.utils.LogLevelSetter

class SocketWordcount {

}

object SocketWordcount {

  def main(args: Array[String]): Unit = {

    //将log日志级别设置为warn
    LogLevelSetter.setLevel
    
    val conf = new SparkConf()
    conf.setMaster("local[2]").setAppName("socketwordcount").setSparkHome("G:\\spark-1.5.1-bin-hadoop2.6\\spark-1.5.1-bin-hadoop2.6")

    //创建一个sparkStreaming的context
    val ssc = new StreamingContext(conf, Seconds(2))

    val stDStream = ssc.socketTextStream("localhost", 9999, StorageLevel.MEMORY_AND_DISK)

    //切分单词,注意，此处对dtream的操作其实是针对该dstream中一批rdd来操作
    val words = stDStream.flatMap { x => x.split(" ") }.map { x => (x, 1) }
    val wordcounts = words.reduceByKey(_ + _)
    wordcounts.print()

    ssc.start()
    ssc.awaitTermination()

  }

}