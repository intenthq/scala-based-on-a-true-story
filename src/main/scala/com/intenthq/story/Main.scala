package com.intenthq.story

import java.net.URL

import scala.collection.convert.Wrappers.JMapWrapper

case class Context(wordToTopic: Map[String, Int])

case class Config(command: Option[Command] = None, url: Option[URL] = None)

sealed trait Command {
  def run(config: Config): Int
}

case object ExtractTopics extends Command {
  def run(config: Config): Int =
    config.url.flatMap { url =>
      println("Going to extract")
      TopicExtractor.extract(url, TextSplitter.split).map { result =>
        println(s"Extracted topics from $url")
        println("Text is ---------------------------------------------------------------------------------")
        println(result.text)
        println("Topics are ------------------------------------------------------------------------------")
        println(result.topics)
        0
      }
    }.getOrElse(1)

  implicit val context = {
    val map = MapDB.create(true).getHashMap[String, Int]("default")

    Context(JMapWrapper(map).toMap)
  }

}

case object SampleDb extends Command {
  def run(config: Config): Int = {
    val mapdb = MapDB.create(false)
    val map = mapdb.getHashMap[String, Int]("default")
    List("scala", "java", "code", "readable", "dry", "testing", "decoupled", "ruby", "messi", "queen")
      .zipWithIndex.foreach { case (word, index) => map.put(word, index) }
    mapdb.close()
    0
  }
}

object Main {

  def main(args: Array[String]): Unit = {
    task(args)
  }

  def task(args: Array[String]): Int =
    options(args).flatMap( config => config.command.map(_.run(config)) ).getOrElse(1)

  def options(args: Array[String]): Option[Config] = {
    val parser = new scopt.OptionParser[Config]("story") {
      cmd("extract") action { (_, c) =>
        c.copy(command = Some(ExtractTopics) ) } text "Extracts topics from a url"
      cmd("sample-db") action { (_, c) =>
        c.copy(command = Some(SampleDb) ) } text "Generates a sample db"
      arg[String]("url") optional() valueName "<url>" action { (x, c) =>
        c.copy(url = Some(new URL(x))) } text "URL to extract topics from"
      checkConfig(_.command.map(_ => success).getOrElse(failure("You must define one of the commands")))
    }
    parser.parse(args, Config())
  }

}
