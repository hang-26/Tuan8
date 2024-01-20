package com.example.towexcercises

class FileData  {
    var name: String = ""
    var path: String = ""
    var size: Long = 0L
    var icon: Int = 0
    constructor(name: String, path: String, size: Long, icon: Int) {
        this.name = name
        this.path = path
        this.size = size
        this.icon = icon
    }
}