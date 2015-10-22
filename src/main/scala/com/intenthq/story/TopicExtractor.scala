package com.intenthq.story

import java.net.URL

import com.google.common.base.Charsets
import com.google.common.io.Resources
import com.intenthq.gander.Gander

case class Results(text: String, topics: Set[Int])

object TopicExtractor {

  def extract(url: URL, splitText: String => Set[String])(implicit context: Context): Option[Results] = {
    val html = downloadHTML(url)
    Gander.extract(html).flatMap( extracted =>
      extracted.cleanedText.map( content =>
        Results(content, extractTopics(content, splitText))
      )
    )
  }

  def downloadHTML(url: URL): String = Resources.toString(url, Charsets.UTF_8)

  private def extractTopics(text: String, splitText: String => Set[String])(implicit context: Context): Set[Int] =
    splitText(text).flatMap(identifyTopic)

  private def identifyTopic(word: String)(implicit context: Context): Set[Int] =
    context.wordToTopic.get(word.toLowerCase).toSet

}