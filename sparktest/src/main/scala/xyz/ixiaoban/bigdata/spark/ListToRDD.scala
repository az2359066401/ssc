package xyz.ixiaoban.bigdata.spark

import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Administrator on 2015-11-30.
 */
case class User(name:String,psw:String){

}

object ListToRDD {
  def main(args: Array[String]): Unit = {

    val zs= User("zhangsan","123")
    val ls= User("ls","123")
    val ww= User("ww","123")

    val users = List(zs,ls,ww)
    val conf = new SparkConf
    conf.setMaster("local[3]").setAppName("savelist")
    val sc = new SparkContext(conf)
    val result = "hdfs://hdp-node-01:9000/savelist/output/"
    val listRdd = sc.parallelize(users)
    listRdd.saveAsTextFile(result)
    sc.stop()
  }
}
