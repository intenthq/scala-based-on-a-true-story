package com.intenthq.story

import java.net.URL

import org.specs2.mutable.Specification

class TopicExtractorIT extends Specification {

  val url = new URL("http://engineering.intenthq.com/2015/03/what-is-good-code-a-scientific-definition/")

  "extract" >> {
    "should return the text and the topics for the webpage" >> {
      TopicExtractor.extract(url) must beSome { res: Results =>
        res.text must startWith("Here at Intent HQ we believe how important it is to write good code")
        res.text must endWith("Doesn’t sound bad at all, does it?")
        res.topics must_== List(1, 2, 3, 4)
      }
    }
  }
}
