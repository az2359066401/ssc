package xyz.ixiaoban.bigdata.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.storage.StorageLevel
import org.apache.spark.HashPartitioner
import xyz.ixiaoban.bigdata.utils.LogLevelSetter

class StatefulWordcountStreaming {

}

object StatefulWordcountStreaming {
  
  /**
   * String ：   就是我们的单词，也就是key
   * Seq[Int]: 是我们这一batch rdd中的关联到上面那个key的所有values
   * Option[Int]: 是上面那个key所关联的旧的状态值
   * 我们这个程序的逻辑就是将这一批次的values累加，然后再累加到旧的状态值上，就得到当前时间点为止，这个key的最新状态值
   */
  val updateFunc = (iter:Iterator[(String, Seq[Int], Option[Int])] ) => {
    
    iter.flatMap(it=> Some(it._2.sum + it._3.getOrElse(0)).map { x => (it._1, x) })
    
  }
  
  
  
  
  def main(args: Array[String]): Unit = {
    
    LogLevelSetter.setLevel
    
    val conf = new SparkConf
    conf.setMaster("local[2]").setAppName("statewc")
    val ssc = new StreamingContext(conf,Seconds(2))
    
    ssc.checkpoint(".")
    
    val socketDs = ssc.socketTextStream("localhost", 9999, StorageLevel.MEMORY_AND_DISK)
    
    //构造一个起始状态数据集
    val initialRDD = ssc.sparkContext.parallelize(List(("A",100),("B",200),("C",300),("D",400)))
    
    val wordsDs = socketDs.flatMap { x => x.split(" ") }.map { x => (x,1) }
    val stateDs = wordsDs.updateStateByKey(updateFunc, new HashPartitioner(ssc.sparkContext.defaultParallelism), true,initialRDD)
    
    stateDs.print()
    
    ssc.start()
    ssc.awaitTermination()
    
    
    
  }
  
  
  
  
}