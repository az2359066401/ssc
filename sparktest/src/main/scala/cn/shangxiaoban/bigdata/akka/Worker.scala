package cn.shangxiaoban.bigdata.akka

import java.util.UUID
import scala.concurrent.duration._
import akka.actor.{ActorSelection, Props, ActorSystem, Actor}
import akka.actor.Actor.Receive
import com.typesafe.config.ConfigFactory

/**
 * Worker为整个集群的从节点
 * Worker继承了Actor
 */
case class SendHeartBeat()

class Worker extends Actor{

  //Worker端持有Master端的引用（代理对象）
  var master: ActorSelection = null
  //生成一个UUID，作为Worker的标识
  val id = UUID.randomUUID().toString

  //构造方法执行完执行一次
  override def preStart(): Unit = {
    //Worker向MasterActorSystem发送建立连接请求
    master = context.system.actorSelection("akka.tcp://MasterActorSystem@127.0.0.1:8888/user/Master")
    //Worker向Master发送注册消息
    master ! RegisterWorker(id, "127.0.0.1", 10240, 8)
  }

  //该方法会被反复执行，用于接收消息，通过case class模式匹配接收消息
  override def receive: Receive = {
    //Master向Worker的反馈信息
    case RegisteredWorker(masterUrl) => {
      import context.dispatcher
      //启动定时任务，向Master发送心跳
      context.system.scheduler.schedule(0 millis, 5000 millis, self, SendHeartBeat)
    }

    case SendHeartBeat => {
      println("worker send heartbeat")
      master ! HeartBeat(id)
    }
  }
}

object Worker {
  def main(args: Array[String]) {
    val clientPort = 2555
    //创建WorkerActorSystem的必要参数
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.port = $clientPort
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    val actorSystem = ActorSystem("WorkerActorSystem", config)
    //启动Actor，Master会被实例化，生命周期方法会被调用
    actorSystem.actorOf(Props[Worker], "Worker")
  }
}
