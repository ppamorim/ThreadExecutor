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
package com.github.ppamorim.threadexecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

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
public class ThreadExecutor implements InteractorExecutor {

  private int corePoolSize = 3;
  private int maxPoolSize = 5;
  private int keepAliveTime = 120;
  private TimeUnit timeUnit = TimeUnit.SECONDS;
  private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
  private ThreadPoolExecutor threadPoolExecutor;

  @Inject public ThreadExecutor() {
    threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
        keepAliveTime, timeUnit, workQueue);
  }

  @Override public void run(final Interactor interactor) {
    if (interactor == null) {
      throw new IllegalArgumentException("Interactor must not be null");
    }
    threadPoolExecutor.setRejectedExecutionHandler(rejectedExecutionHandler);
    threadPoolExecutor.submit(new Runnable() {
      @Override public void run() {
        interactor.run();
      }
    });
  }

  public ThreadExecutor setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
    this.threadPoolExecutor = threadPoolExecutor;
    return this;
  }

  public ThreadExecutor setCorePoolSize(int corePoolSize) {
    this.corePoolSize = corePoolSize;
    return this;
  }

  public ThreadExecutor setMaxPoolSize(int maxPoolSize) {
    this.maxPoolSize = maxPoolSize;
    return this;
  }

  public ThreadExecutor setKeepAliveTime(int keepAliveTime) {
    this.keepAliveTime = keepAliveTime;
    return this;
  }

  public ThreadExecutor setTimeUnit(TimeUnit timeUnit) {
    this.timeUnit = timeUnit;
    return this;
  }

  public void build() {
    threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
        keepAliveTime, timeUnit, workQueue);
  }

  private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
    @Override public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
      threadPoolExecutor.shutdown();
    }
  };

}
