/*
* Copyright (C) 2015 Pedro Paulo de Amorim
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

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

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
class ThreadExecutor @Inject constructor() : InteractorExecutor {

  private var corePoolSize = 3
  private var maxPoolSize = 5
  private var keepAliveTime = 120
  private var timeUnit = TimeUnit.SECONDS
  private val workQueue = LinkedBlockingQueue<Runnable>()
  private var threadPoolExecutor: ThreadPoolExecutor? = null

  init {
    threadPoolExecutor = ThreadPoolExecutor(corePoolSize, maxPoolSize,
        keepAliveTime.toLong(), timeUnit, workQueue)
  }

  override fun run(interactor: Interactor) {
    if (interactor == null) {
      throw IllegalArgumentException("Interactor must not be null")
    }
    threadPoolExecutor!!.rejectedExecutionHandler = rejectedExecutionHandler
    threadPoolExecutor!!.submit { interactor.run() }
  }

  fun setThreadPoolExecutor(threadPoolExecutor: ThreadPoolExecutor): ThreadExecutor {
    this.threadPoolExecutor = threadPoolExecutor
    return this
  }

  fun setCorePoolSize(corePoolSize: Int): ThreadExecutor {
    this.corePoolSize = corePoolSize
    return this
  }

  fun setMaxPoolSize(maxPoolSize: Int): ThreadExecutor {
    this.maxPoolSize = maxPoolSize
    return this
  }

  fun setKeepAliveTime(keepAliveTime: Int): ThreadExecutor {
    this.keepAliveTime = keepAliveTime
    return this
  }

  fun setTimeUnit(timeUnit: TimeUnit): ThreadExecutor {
    this.timeUnit = timeUnit
    return this
  }

  fun build() {
    threadPoolExecutor = ThreadPoolExecutor(corePoolSize, maxPoolSize,
        keepAliveTime.toLong(), timeUnit, workQueue)
  }

  private val rejectedExecutionHandler = RejectedExecutionHandler { r, executor -> threadPoolExecutor!!.shutdown() }

}
