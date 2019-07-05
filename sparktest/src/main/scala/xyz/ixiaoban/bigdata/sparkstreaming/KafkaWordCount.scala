/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// scalastyle:off println
package xyz.ixiaoban.bigdata.sparkstreaming

import org.apache.kafka.clients.producer.{ProducerConfig, KafkaProducer, ProducerRecord}
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf
import redis.clients.jedis.Jedis

import xyz.ixiaoban.bigdata.utils.LogLevelSetter

/**
 * Consumes messages from one or more topics in Kafka and does wordcount.
 * Usage: KafkaWordCount <zkQuorum> <group> <topics> <numThreads>
 * <zkQuorum> is a list of one or more zookeeper servers that make quorum
 * <group> is the name of kafka consumer group
 * <topics> is a list of one or more kafka topics to consume from
 * <numThreads> is the number of threads the kafka consumer should use
 *
 * Example:
 * `$ bin/run-example \
 * org.apache.spark.examples.streaming.KafkaWordCount zoo01,zoo02,zoo03 \
 * my-consumer-group topic1,topic2 1`
 */
object KafkaWordCount {
  def main(args: Array[String]) {
    LogLevelSetter.setLevel()
    if (args.length < -1) {
      System.err.println("Usage: KafkaWordCount <zkQuorum> <group> <topics> <numThreads>")
      System.exit(1)
    }
    println("------------------------------------------------------------------")

    //    val Array(zkQuorum, group, topics, numThreads) = args
    val zkQuorum = "hdp-node-01:2181,hdp-node-02:2181,hdp-node-03:2181"
    val group = "urls"
    val topics = "urltopic"
    val numThreads = "3"

    val sparkConf = new SparkConf().setAppName("KafkaWordCount")
      .setMaster("local[3]")
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    ssc.checkpoint("checkpoint")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1L))
      .reduceByKeyAndWindow(_ + _, _ - _, Minutes(12), Seconds(4), 2)
    wordCounts.foreachRDD(rdd => {
      val batch = rdd.collect()
      for (i <- batch) {
        RedisClient.jedis.zadd("topurls", i._2.toDouble, i._1.toString)
      }
      /*      rdd.map(x=>{
              println(x)
              RedisClient.jedis.zadd("topurls",x._2.toDouble,x._1.toString)

            })*/

    })

    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }
}

object RedisClient extends Serializable {
  val redisHost = "hdp-node-01"
  val redisPort = 6379
  val redisTimeout = 30000

  lazy val jedis = new Jedis(redisHost)
  lazy val hook = new Thread {
    override def run = {
      println("Execute hook thread: " + this)
      jedis.close()
    }
  }
  sys.addShutdownHook(hook.run)
}

