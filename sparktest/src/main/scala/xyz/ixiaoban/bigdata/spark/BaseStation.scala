package xyz.ixiaoban.bigdata.spark

import org.apache.spark.{SparkConf, SparkContext}
import java.util.{Date, Locale}
import java.text.SimpleDateFormat
import java.text.DateFormat._
/**
  * Created by Administrator on 2016-5-11.
  */
object BaseStation {


  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("BaseStation")
    sparkConf.setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val file = sc.textFile("x:/aggregate_flickering.txt")

    val resRdd = file.map(line => {
      val now = new Date
      val df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      //按| 切割原始数据
      val arr = line.split("\\|")
      //取到原始数据第一个基站
      val b1 = arr(0)
      //取到原始数据第二列 “1”
      val b2 = arr(1)
      //取到原始数据第三列：所有其他基站及其value
      val bothers = arr(2).split(",")
      val res = new StringBuilder
//      var res = ""
      //将所有其他基站及value进行切割，并跟第一个基站进行拼接，得到结果
      for (pair <- bothers) {
        res.append(df.format(now) + "\t" + b1 + "\t" + pair.split("=")(0) + "\t" + pair.split("=")(1) + "\t" + b2 + "\t" + "0"+"~")
      }
      res.toString()
    })

    //保存结果数据到文件
    resRdd.flatMap(_.split("~")).saveAsTextFile("x:/res.txt")


    /*val arr = resRdd.collect()
    for(a<-arr){
      println(a)
    }*/
  }

}
