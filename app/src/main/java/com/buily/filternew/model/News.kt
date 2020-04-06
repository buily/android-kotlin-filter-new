package com.buily.filternew.model

data class News(
    var title: String,
    var desc: String,
    var image: String,
    var link: String,
    var pubDate: String
) {

    constructor() : this("", "", "", "", "")
}