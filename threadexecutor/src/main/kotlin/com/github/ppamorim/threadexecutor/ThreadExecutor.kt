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

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val DEFAULT_CORE_POOL_SIZE = 3
const val DEFAULT_MAX_POOL_SIZE = 3
const val DEFAULT_KEEP_ALIVE_TIME = 120L

/**
 * This class will create the instance of
 * BlockingQueue to be used with ThreadPool.
 * The thread pool has some limitations and
 * automatically will release any thread of
 * the memory.
 * Some configurations can be used to change
 * some variables of ThreadPool. Use the method
 * .build to finished every configuration.
 */
class ThreadExecutor @Inject constructor(): InteractorExecutor {

  var corePoolSize: Int = DEFAULT_CORE_POOL_SIZE
  var maxPoolSize: Int = DEFAULT_MAX_POOL_SIZE
  var keepAliveTime: Long = DEFAULT_KEEP_ALIVE_TIME
  var timeUnit: TimeUnit = TimeUnit.SECONDS

  private val workQueue by lazy { LinkedBlockingQueue<Runnable>() }

  private val threadPoolExecutor by lazy {
    ThreadPoolExecutor(
        corePoolSize,
        maxPoolSize,
        keepAliveTime,
        timeUnit,
        workQueue)
  }

  override fun run(interactor: Interactor) {
    threadPoolExecutor.rejectedExecutionHandler = rejectedExecutionHandler
    threadPoolExecutor.submit { interactor.run() }
  }

  private val rejectedExecutionHandler = RejectedExecutionHandler { _, _ ->
    threadPoolExecutor.shutdown()
  }

}
