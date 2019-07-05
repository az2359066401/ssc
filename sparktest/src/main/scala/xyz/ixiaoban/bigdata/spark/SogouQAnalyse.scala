package xyz.ixiaoban.bigdata.spark

import org.apache.spark.{SparkContext, SparkConf}
import xyz.ixiaoban.bigdata.utils.LogLevelSetter

/**
 * Created by Administrator on 2015-11-30.
 */
object SogouQAnalyse {
  def main(args: Array[String]): Unit = {
    LogLevelSetter.setLevel
    val sb = new StringBuilder
    val masterurl = "local[2]"
//    val Array(input,wcoutput,ffoutput) = args
    val input = "C:\\home\\work-program\\IdeaProjects\\sparktest\\temp\\input\\SogouQ.reduced.u8"
    val wcoutput = "C:\\home\\work-program\\IdeaProjects\\sparktest\\temp\\sgoutput"
    val ffoutput="C:\\home\\work-program\\IdeaProjects\\sparktest\\temp\\sgoutputff"

    val conf = new SparkConf
    conf.setMaster(masterurl).setAppName("sogouq")
    val sc = new SparkContext(conf)
    val file = sc.textFile(input)
    file.cache()

    val count = file.count()
    println("文件总共有" + count + "行")

    val wcrdd = file.map { x => x.split("\t") }.filter { x => x(2).contains("汶川地震") }.map { x => x.mkString("\t") }

    val count_wc = wcrdd.count
    println("搜索关键字中包含汶川地震的行数为： " + count_wc)
    wcrdd.saveAsTextFile(wcoutput)

    val ffrdd = file.map { x => x.split("\t") }.filter { x => x(3).equals("1 1") }.map { x => x.mkString("\t") }
    val count_11 = ffrdd.count
    println("结果排序和用户点击都为第一位的记录条数为： " + count_11)
    ffrdd.saveAsTextFile(ffoutput)
    sc.stop()
  }


}
