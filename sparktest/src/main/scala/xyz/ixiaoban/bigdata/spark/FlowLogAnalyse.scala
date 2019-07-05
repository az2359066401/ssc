package xyz.ixiaoban.bigdata.spark

import org.apache.spark.serializer.KryoSerializer
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Administrator on 2015-11-30.
 */

case class FlowBean(val phone:String, val upFlow:Long, val dFlow:Long) extends Serializable {
  val sumFlow = upFlow + dFlow

  override def toString: String ={
    phone +"\t" + upFlow +"\t" + dFlow +"\t" + sumFlow
  }
}


object FlowLogAnalyse {
  def main(args: Array[String]): Unit = {
    val masterurl = "spark://hdp-node-01:7077"
    val input = "/sparktest/input/HTTP_20130313143750.dat"
    val output = "/sparktest/output"

    val conf = new SparkConf
    conf.setAppName("flow")//.setMaster(masterurl)
    //注册org.apache.spark.serializer.KryoSerializer作为spark的序列化机制，可以提高性能
    conf.registerKryoClasses(Array(FlowBean.getClass))
    val sc = new SparkContext(conf)

    //加载文件创建rdd
    val linerdd = sc.textFile(input).map { x => x.split("\t") }
    linerdd.cache()

    val beanrdd = linerdd.map { x => (x(1),FlowBean(x(1),x(x.length-3).toLong,x(x.length-2).toLong)) }
    val sumrdd = beanrdd.reduceByKey((bean1,bean2)=>FlowBean(bean1.phone,bean1.upFlow + bean2.upFlow,bean1.dFlow+bean2.dFlow))
    val sortrdd = sumrdd.map(x=>(x._2.sumFlow,x)).sortByKey(false)
    sortrdd.map(x=>x._2._2).saveAsTextFile(output);

    sc.stop
  }
}
