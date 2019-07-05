package cn.shangxiaoban.bigdata.mobilelog

import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Administrator on 2016-1-19.
 */
class MobileLocation {

}

object MobileLocation {
  def main(args: Array[String]): Unit = {

/*
    val str="2D1F3FDDC422AE58451E11B1A5FC470F|EAC12DE32BFF251C94C0E46159CFC837|460|01|94D832D0F9794A891DBA6B78BD3E8C49|A34BACF839B923770B2C360EEFA26748|3C9B5F1CD6A030BCB7BED9EAC80589FB|2015-11-19 06:25:46.806808|41062|11511|2015-11-19 06:25:47.606312|1|32|011|19|201511"
    val ar = str.split("\\|")
    println(ar.length)
    println(ar.mkString("\t"))
*/

    val masterurl = "local[1]"
    val input = "c:\\tttt.txt"

    val conf = new SparkConf
    conf.setMaster(masterurl).setAppName("lac")
    val sc = new SparkContext(conf)

    //加载文件创建rdd
    val hometime = sc.textFile(input).map(_.split(",")).filter(x=>x(1)<="d" && x(1)>="a")
    val officetime = sc.textFile(input).map(_.split(",")).filter(x=>x(1)<="z" && x(1)>="w")


    hometime.map(x=>((x(0)+":"+x(2)+x(3)),1)).reduceByKey(_+_).map(x=>(x._1.split(":")(0)+":"+x._2,x._1.split(":")(1)))
    .sortByKey(false).map(x=>(x._1.split(":")(0),(x._1.split(":")(1),x._2))).reduceByKey((x,y)=>x).map(x=>(x._1,x._2._2))
    .saveAsTextFile("c:\\homelac")
//    .collect().foreach(x=>println(x))

    officetime.map(x=>((x(0)+":"+x(2)+x(3)),1)).reduceByKey(_+_).map(x=>(x._1.split(":")(0)+":"+x._2,x._1.split(":")(1)))
      .sortByKey(false).map(x=>(x._1.split(":")(0),(x._1.split(":")(1),x._2))).reduceByKey((x,y)=>x).map(x=>(x._1,x._2._2))
      .saveAsTextFile("c:\\officelac")
    //    .collect().foreach(x=>println(x))

    sc.stop()
  }
}