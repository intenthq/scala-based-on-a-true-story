package com.intenthq.story

import java.net.URL

import com.google.common.base.Charsets
import com.google.common.io.Resources
import com.intenthq.gander.Gander

case class Results(text: String, topics: List[Int])

object TopicExtractor {

  def extract(url: URL): Option[Results] = {
    val html = downloadHTML(url)
    Gander.extract(html).flatMap( extracted =>
      extracted.cleanedText.map( content =>
        Results(content, extractTopics(content))
      )
    )
  }

  def downloadHTML(url: URL): String = Resources.toString(url, Charsets.UTF_8)

  private def extractTopics(text: String): List[Int] = List(1, 2, 3, 4)

}