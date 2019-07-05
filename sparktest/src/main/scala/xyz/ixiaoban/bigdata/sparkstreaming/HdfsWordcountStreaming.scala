package xyz.ixiaoban.bigdata.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import xyz.ixiaoban.bigdata.utils.LogLevelSetter

class HdfsWordcountStreaming {

}

object HdfsWordcountStreaming {

  def main(args: Array[String]): Unit = {
/*
    if (args.length < 1) {

      System.err.println("Usage: HdfsWordcountStreaming <hdfs-path>")
      System.exit(1)

    }*/
    
    LogLevelSetter.setLevel
    
    val conf = new SparkConf
    conf.setMaster("local[2]").setAppName("hdfswordcount")
    val ssc = new StreamingContext(conf,Seconds(2))
    
    val fileDstream = ssc.textFileStream("c:/wordcount/input/")
    val wordsDstream = fileDstream.flatMap { x => x.split(" ") }.map { x => (x,1) }
    val wordcountsDstream = wordsDstream.reduceByKey(_+_)
    
    wordcountsDstream.print()
    
    ssc.start()
    ssc.awaitTermination()
    

  }

}