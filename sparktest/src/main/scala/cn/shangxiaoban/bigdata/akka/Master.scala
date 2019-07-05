package cn.shangxiaoban.bigdata.akka

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.concurrent.duration._

/**
 * Created by Administrator on 2016-1-26.
 */
case class WorkerInfo(id:String,workerHost:String,memory:Int,cores:Int,var lastHeartbeat:Long=0)
case class RegisterWorker(id:String,workerHost:String,memory:Int,cores:Int)
case class RegisteredWorker(ip:String)
case class CheckOfTimeOutWorker()
case class HeartBeat(workerId:String)
class Master extends Actor{

  //保存WorkerID和Work信息的map
  val idToWorker = new mutable.HashMap[String, WorkerInfo]
  //保存所有Worker信息的Set
  val workers = new mutable.HashSet[WorkerInfo]
  //Worker超时时间
  val WORKER_TIMEOUT = 10 * 1000
  //重新receive方法

  //导入隐式转换，用于启动定时器
  import context.dispatcher

  //构造方法执行完执行一次
  override def preStart(): Unit = {
    //启动定时器，定时执行
    context.system.scheduler.schedule(0 millis, WORKER_TIMEOUT millis, self, CheckOfTimeOutWorker)
  }

  //该方法会被反复执行，用于接收消息，通过case class模式匹配接收消息
  override def receive: Receive = {
    //Worker向Master发送的注册消息
    case RegisterWorker(id, workerHost, memory, cores) => {
      if(!idToWorker.contains(id)) {
        val worker = new WorkerInfo(id, workerHost, memory, cores)
        workers.add(worker)
        idToWorker(id) = worker
        sender ! RegisteredWorker("192.168.1.101")
      }
    }

    //Worker向Master发送的心跳消息
    case HeartBeat(workerId) => {
      val workerInfo = idToWorker(workerId)
      workerInfo.lastHeartbeat = System.currentTimeMillis()
    }

    //Master自己向自己发送的定期检查超时Worker的消息
    case CheckOfTimeOutWorker => {
      val currentTime = System.currentTimeMillis()
      val toRemove = workers.filter(w => currentTime - w.lastHeartbeat > WORKER_TIMEOUT).toArray
      for(worker <- toRemove){
        workers -= worker
        idToWorker.remove(worker.id)
      }
      println("worker size: " + workers.size)
    }
  }
}

object Master {
  //程序执行入口
  def main(args: Array[String]) {

    val host = "127.0.0.1"
    val port = 8888
    //创建ActorSystem的必要参数
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    //ActorSystem是单例的，用来创建Actor
    val actorSystem = ActorSystem.create("MasterActorSystem", config)
    //启动Actor，Master会被实例化，生命周期方法会被调用
    actorSystem.actorOf(Props[Master], "Master")
  }
}

