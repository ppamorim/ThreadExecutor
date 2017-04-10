package com.github.ppamorim.threadexecutor

import android.os.Handler
import android.os.Looper
import android.os.Message

val MESSAGE_POST_RESULT = 0x1

class InternalHandler(looper: Looper): Handler(looper) {

  lateinit var result: () -> Unit

  override fun handleMessage(msg: Message?) {
    super.handleMessage(msg)
    msg?.let { msg ->
      when (msg.what) {
        MESSAGE_POST_RESULT -> result()
      }
    }
  }

  fun obtainMessage(what: Int, any: Any? = null, result: () -> Unit): Message {
    this.result = result
    any?.let { any ->
      return obtainMessage(what, any)
    }
    return obtainMessage(what)
  }

}