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

import android.os.Looper
import javax.inject.Inject

class MainThreadImpl @Inject constructor(): MainThread {

  private val handler: InternalHandler by lazy {
    InternalHandler(Looper.getMainLooper())
  }

  override fun post(runnable: Runnable) = handler.post(runnable)

  override fun post(runnable: () -> Unit) = handler.post { runnable() }

  override fun sendMessage(what: Int, sent: () -> Unit) =
      handler.obtainMessage(what, sent).sendToTarget()

  override fun sendMessage(what: Int, any: Any, sent: () -> Unit) =
      handler.obtainMessage(what, any, sent).sendToTarget()

  override fun sendEmptyMessage(what: Int) = handler.sendEmptyMessage(what)

}
