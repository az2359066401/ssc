package xyz.ixiaoban.bigdata.sparksql

import org.apache.spark.sql.types.{StructType, StructField, StringType}
import org.apache.spark.sql.{SaveMode, Row, SQLContext}
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql
/**
 * Created by Administrator on 2015-12-2.
 */
class SparkSqlDemo {

}

case class Person(name: String, age: Int)

object SparkSqlDemo{

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("sqldemo").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    //加载数据为rdd然后转为DF
    val peopleRdd = sc.textFile("C:\\home\\work-program\\IdeaProjects\\sparktest\\temp\\input\\people.txt").map(_.split(","))

    //通过反射机制将RDD转为dataframe
    val peopleDF = peopleRdd.map(p => Person(p(0), p(1).trim.toInt)).toDF()

    //使用structType定义schema来转换RDD
//    val schema = StructType(Array(StructField("name",StringType,true),StructField("age",StringType,true)))
/*    val schemaString ="name age"
    val schema =  StructType(schemaString.split(" ").map(fieldName => StructField(fieldName, StringType, true)))
    val rowRdd = peopleRdd.map(p=>Row(p(0),p(1)))
    val peopleDF = sqlContext.createDataFrame(rowRdd,schema)*/

//    peopleDF.write.parquet("C:\\home\\work-program\\IdeaProjects\\sparktest\\temp\\input\\people.parquet")
    //还可以指定save模式 overrice  append error ignore
//    peopleDF.write.option("SaveMode","append").parquet("")

    //直接从parquet文件中创建dataframe
/*    val peopleDF = sqlContext.read.parquet("C:\\\\home\\\\work-program\\\\IdeaProjects\\\\sparktest\\\\temp\\\\input\\\\people.parquet")
    //或者手动指定类型
    val peopleDF = sqlContext.read.format("parquet").load("C:\\\\home\\\\work-program\\\\IdeaProjects\\\\sparktest\\\\temp\\\\input\\\\people.parquet")*/


    val teenager1 = peopleDF.filter("age>=13 and age<=19")
    teenager1.show()
    println("----------------")

    //可以将df注册为sparksql中的表，然后用sql语句来查询数据，查询返回的结果为DataFrame
    peopleDF.registerTempTable("people")

    val teenagers3 = sqlContext.sql("SELECT name FROM people WHERE age >= 13 AND age <= 19")
    teenagers3.show()
    println("----------------")

    // DataFrame完全兼容Rdd的api操作
    teenagers3.map(t => "Name: " + t(0)).collect().foreach(println)
  }



}
