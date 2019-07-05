package xyz.ixiaoban.bigdata.sparksql

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkContext, SparkConf}
/**
 * Created by Administrator on 2015-12-2.
 */
/**
 * bin/spark-submit --class xyz.ixiaoban.bigdata.sparksql.HiveSqlDemo --master spark://hdp-node-01:7077 /home/hadoop/sparktest-1.0-SNAPSHOT.jar
 */
object HiveSqlDemo {


  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("HiveFromSpark").setMaster("local[1]")
    val sc = new SparkContext(sparkConf)

    // A hive context adds support for finding tables in the MetaStore and writing queries
    // using HiveQL. Users who do not have an existing Hive deployment can still create a
    // HiveContext. When not configured by the hive-site.xml, the context automatically
    // creates metastore_db and warehouse in the current directory.
    val hiveContext = new HiveContext(sc)
    import hiveContext.implicits._
    import hiveContext.sql

    sql("use dw_weblog")
//    sql("select remote_addr,count(*) from t_ods_detail group by remote_addr").collect.foreach(println)
    sql("create table sparksql as select remote_addr,count(*) from t_ods_detail group by remote_addr")
    /*    val dataPath="hdfs://hdp-node-01:9000/stu.data"
        sql("CREATE TABLE IF NOT EXISTS stu (id INT, name STRING)")
        sql(s"LOAD DATA INPATH '$dataPath' INTO TABLE people")

        // Queries are expressed in HiveQL
        println("Result of 'SELECT *': ")
        sql("SELECT * FROM stu").collect().foreach(println)

        // Aggregation queries are also supported.
        val count = sql("SELECT COUNT(*) FROM stu").collect().head.getLong(0)
        println(s"COUNT(*): $count")*/
  }

}
