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

import android.os.Message

/**
 * Interface that will use the instance of
 * main thread to inform the result of the
 * async task to the main thread.
 */
interface MainThread {
  fun post(runnable: Runnable): Boolean
  fun post(runnable: () -> Unit): Boolean
  fun sendMessage(message: Message): Boolean
  fun sendEmptyMessage(what: Int): Boolean
  fun obtainMessage(what: Int): Message
  fun obtainMessage(what: Int, any: Any): Message
}
