package com.intenthq.story

object TextSplitter {

  def split(text: String): Set[String] = text.split("""\W""").toSet

}