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

import java.util.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

const val DEFAULT_KEEP_ALIVE_TIME = 30L

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

  val CPU_COUNT = Runtime.getRuntime().availableProcessors()

  var corePoolSize: Int = Math.max(2, Math.min(CPU_COUNT - 1, 4))
  var maxPoolSize: Int = CPU_COUNT * 2 + 1
  var keepAliveTime: Long = DEFAULT_KEEP_ALIVE_TIME
  var timeUnit: TimeUnit = TimeUnit.SECONDS

  private val workQueue by lazy { LinkedBlockingQueue<Runnable>(128) }

  private val threadPoolExecutor by lazy {
    val threadPoolExecutor = ThreadPoolExecutor(
        corePoolSize,
        maxPoolSize,
        keepAliveTime,
        timeUnit,
        workQueue,
        threadFactory,
        rejectedExecutionHandler)
    threadPoolExecutor.allowCoreThreadTimeOut(true)
    threadPoolExecutor
  }

  private val serialExecutor by lazy {
    SerialExecutor(threadPoolExecutor)
  }

  private val threadFactory = object: ThreadFactory {
    private val count = AtomicInteger(1)
    override fun newThread(r: Runnable): Thread {
      return Thread(r, "AsyncTask #${count.getAndIncrement()}")
    }
  }

  private val rejectedExecutionHandler: RejectedExecutionHandler =
      RejectedExecutionHandler { _, _ ->
        threadPoolExecutor.shutdown()
      }

  override fun run(serial: Boolean, interactor: Interactor) {
    if (serial) {
      serialExecutor.execute { interactor.run() }
    } else {
      threadPoolExecutor.submit { interactor.run() }
    }
  }

}

class SerialExecutor(val threadPoolExecutor: ThreadPoolExecutor): Executor {

  private val tasks = ArrayDeque<Runnable>()
  private var active: Runnable? = null

  override fun execute(runnable: Runnable?) {
    synchronized(this) {
      tasks.offer(Runnable {
        try {
          runnable?.run()
        } finally {
          scheduleNext()
        }
      })
      if (active == null) {
        scheduleNext()
      }
    }
  }

  fun scheduleNext() {
    synchronized(this) {
      active = tasks.poll()
      active?.let {
        threadPoolExecutor.submit(it)
      }
    }
  }

}