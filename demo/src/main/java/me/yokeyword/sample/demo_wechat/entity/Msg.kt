package me.yokeyword.sample.demo_wechat.entity

/**
 * Created by YoKeyword on 16/6/30.
 */
class Msg {
    lateinit var message: String

    constructor() {}

    constructor(msg: String) {
        message = msg
    }
}
