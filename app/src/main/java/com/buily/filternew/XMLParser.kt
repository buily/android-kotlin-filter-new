package com.buily.filternew

import com.buily.filternew.model.News
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class XMLParser : DefaultHandler() {

    val arr = mutableListOf<News>()
    private var isItem: Boolean = false

    var item: News? = null
    private lateinit var builder: StringBuilder

    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: Attributes?
    ) {
        super.startElement(uri, localName, qName, attributes)
        if (qName == Constant.ITEM) {
            item = News()
            isItem = true
        }
        if (qName.equals(Constant.IMAGE)) {
            val img: String? = attributes?.getValue("url")
            img?.let {
                item?.image = img
            }
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

            Constant.TITLE -> item?.title = builder.toString()
            Constant.LINK -> item?.link = builder.toString()
            Constant.PUB_DATE -> item?.pubDate = builder.toString()
            Constant.DESC -> {
                if (!isItem) return
                val s: String = builder.toString()
                val v = "target=\"_blank\">"
                val v2 = "</a>"
                val s2: List<String> = s.split(v)
                val s3: List<String> = s2[1].split(v2)
                item?.desc = s3[0]
                isItem = true
            }
            Constant.ITEM -> item?.let { arr.add(it) }
            else -> return
        }
    }
}