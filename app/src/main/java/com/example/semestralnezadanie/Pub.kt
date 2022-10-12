package com.example.semestralnezadanie

class Pub {

    private var name: String? = null
    private var date: String? = null

    constructor(name: String?, date: String?)
    {
        this.name = name
        this.date = date
    }

    fun getName(): String?
    {
        return name
    }

    fun getDate(): String? {
        return date
    }
}