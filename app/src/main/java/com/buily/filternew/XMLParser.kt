package com.buily.filternew

import com.buily.filternew.model.News
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.lang.StringBuilder

class XMLParser : DefaultHandler() {

    val arr = mutableListOf<News>()

    var item: News = News()
    lateinit var builder: StringBuilder

    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: Attributes?
    ) {
        super.startElement(uri, localName, qName, attributes)
        if (qName == Constant.ITEM) {
            item = News()
        }
        if (qName.equals(Constant.IMAGE)) {
            val img: String? = attributes?.getValue("url")
            item.image = img!!
        }

        builder = StringBuilder()
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        super.characters(ch, start, length)
        builder.append(ch, start, length)
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        super.endElement(uri, localName, qName)
        when (qName) {
            Constant.TITLE -> item.title = builder.toString()
            Constant.LINK -> item.link = builder.toString()
            Constant.PUB_DATE -> item.pubDate = builder.toString()
            Constant.DESC -> {
//                var s: String = builder.toString()
//                val v: String = "target=\"_blank\">"
//                val index = s.indexOf(v) + v.length
//                s = s.substring(index)
//                s = s.substring(0, s.indexOf("</a>"))
                item.desc = builder.toString()
            }
            Constant.ITEM -> arr.add(item)

            else -> return
        }


    }
}