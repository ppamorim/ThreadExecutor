/*
* Copyright (C) 2017 Pedro Paulo de Amorim
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.github.ppamorim.threadexecutor

import android.os.Handler
import android.os.Looper
import android.os.Message
import javax.inject.Inject

/**
 * This mehtod will expose te main thread using
 * the handler and MainLooper of the application.
 */
class MainThreadImpl @Inject constructor(): MainThread {

  private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }

  override fun post(runnable: Runnable) = handler.post(runnable)
  override fun sendMessage(message: Message) = handler.sendMessage(message)
  override fun sendEmptyMessage(what: Int) = handler.sendEmptyMessage(what)

}
