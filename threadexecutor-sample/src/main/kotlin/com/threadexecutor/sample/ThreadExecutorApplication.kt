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
package com.threadexecutor.sample

import android.app.Application
import com.threadexecutor.sample.di.ApplicationModule
import com.threadexecutor.sample.di.components.ApplicationComponent
import com.threadexecutor.sample.di.components.DaggerApplicationComponent

class ThreadExecutorApplication: Application() {

  /**
   * This lazy init generates a component that injects
   * a Thread and Pool of Threads provided by
   * ThreadExecutor library.
   */
  private val applicationComponent: ApplicationComponent by lazy {
    DaggerApplicationComponent.builder()
        .applicationModule(ApplicationModule(this))
        .build()
  }

  override fun onCreate() {
    super.onCreate()
    applicationComponent.inject(this)
  }

}