package xyz.ixiaoban.bigdata.sparksql

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.SQLContext

/**
 * Created by Administrator on 2016-1-28.
 */
class DataFrameTest {

}

object DataFrameTest{

  val conf = new SparkConf()
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)

//  val people = sqlContext.read.parquet("...")  // in Scala


  val ageCol = people("age")  // in Scala

  people("age") + 10  // in Scala

  val people = sqlContext.read.parquet("...")
  val department = sqlContext.read.parquet("...")

/*  people.filter("age > 30")
    .join(department, people("deptId") === department("id"))
    .groupBy(department("name"), people("gender"))
    .agg(avg())
    .agg(avg(people("salary")), max(people("age")))*/


}