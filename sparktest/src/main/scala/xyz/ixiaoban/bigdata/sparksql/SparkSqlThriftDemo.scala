package xyz.ixiaoban.bigdata.sparksql
import java.sql.DriverManager
/**
 * Created by Administrator on 2015-12-2.
 */
object SparkSqlThriftDemo {
  def main(args: Array[String]) {
    Class.forName("org.apache.hive.jdbc.HiveDriver")
    val conn = DriverManager.getConnection("jdbc:hive2://hdp-node-01:10000", "hadoop", "");
    try {
      val state = conn.createStatement()
      val res = state.executeQuery("select * from dw_weblog.t_ods_detail limit 10")
      while (res.next()) {
        val remote_addr = res.getString("remote_addr")
        println(remote_addr)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
    conn.close()
  }


}
