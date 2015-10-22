package com.intenthq.story

import java.net.URL

case class Config(command: Option[Command] = None, url: Option[URL] = None)

sealed trait Command
case object ExtractTopics extends Command
case object StartServer extends Command

object Main {

  def main(args: Array[String]): Unit = {
    task(args)
  }

  def task(args: Array[String]): Int =
    options(args).flatMap { config =>
      config.url.flatMap { url =>
        println("Going to extract")
        TopicExtractor.extract(url).map { result =>
          println(s"Extracted topics from $url")
          println("Text is ---------------------------------------------------------------------------------")
          println(result.text)
          println("Topics are ------------------------------------------------------------------------------")
          println(result.topics)
          0
        }
      }
    }.getOrElse(1)

  def options(args: Array[String]): Option[Config] = {
    val parser = new scopt.OptionParser[Config]("story") {
      cmd("extract") action { (_, c) =>
        c.copy(command = Some(ExtractTopics) ) } text "Extracts topics from a url"
      cmd("server") action { (_, c) =>
        c.copy(command = Some(StartServer) ) } text "Starts the server"
      arg[String]("url") valueName "<url>" action { (x, c) =>
        c.copy(url = Some(new URL(x))) } text "URL to extract topics from"
      checkConfig(_.command.map(_ => success).getOrElse(failure("You must define one of the commands")))
    }
    parser.parse(args, Config())
  }
}