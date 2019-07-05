package xyz.ixiaoban.bigdata.utils

import java.net.ServerSocket
import java.io.PrintWriter
import scala.util.Random
import java.util.Random

class DataSender {

}

/**
 *
 * 用来模拟往一个socket端口上发送数据
 *
 * 1、准备数据内容
 * 2、产生随机数据
 * 3、创建socket监听器，等待客户端连接
 * 4、往客户端发送随机数据
 *
 */

object DataSender {

  //准备数据内容池并返回指定的数据
  def generateData(index: Int) = {

    import scala.collection.mutable.ArrayBuffer

    //准备数据内容池
    val ab = ArrayBuffer[Char]()
    for (i <- 65 to 90) {
      ab += i.toChar
    }
    //返回随机数据
    ab(index).toString()

  }

  //创建网络监听器，连接客户端，发送数据
  def main(args: Array[String]): Unit = {

    val listener = new ServerSocket(9999)
    while (true) {

      val socket = listener.accept()
      new Thread {

        override def run() {
          println("client from : " + socket.getInetAddress)
          val out = new PrintWriter(socket.getOutputStream,true)

          while (true) {
            Thread.sleep(200)
            import java.util.Random
            val data = generateData((new Random()).nextInt(5))
            out.write(data + "\n")
            out.flush()
            println(data)

          }
          socket.close()

        }

      }.start()

    }

  }

}