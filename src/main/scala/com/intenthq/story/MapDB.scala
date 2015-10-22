package com.intenthq.story

import java.io.File

import org.mapdb.DBMaker

object MapDB {
  def create(readOnly: Boolean) = {
    import scala.language.existentials
    val maker = DBMaker
      .newFileDB(new File("/tmp/scalabcn.mapdb"))
      .transactionDisable
      .compressionEnable
      .mmapFileEnable
      .cacheSize(20)
      .asyncWriteEnable
    if (readOnly) maker.readOnly
    maker.make
  }
}