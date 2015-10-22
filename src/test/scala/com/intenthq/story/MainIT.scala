package com.intenthq.story

import org.specs2.mutable.Specification

class MainIT extends Specification {

  "main" >> {
    "should use exit code 1 if no command provided" >> {
      Main.task(Array()) must_== 1
    }
    "extract" >> {
      "should use exit code 1 if no url provided" >> {
        Main.task(Array("extract")) must_== 1
      }
      "should use exit code 1 if argument is not a valid url" >> {
        Main.task(Array("extract", "not-a-url")) must_== 1
      }
      "should use exit code 0 if argument is a valid url" >> {
        Main.task(Array("extract", "http://engineering.intenthq.com/2015/03/what-is-good-code-a-scientific-definition/")) must_== 0
      }
    }
  }
}
