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
package com.threadexecutor.sample.di.components

import android.app.Application
import com.github.ppamorim.threadexecutor.InteractorExecutor
import com.github.ppamorim.threadexecutor.MainThread
import com.threadexecutor.sample.ThreadExecutorApplication
import com.threadexecutor.sample.di.ApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * ApplicationComponent is the top level component
 * for this architecture. It provides a generic
 * dependencies [InteractorExecutor]
 * or [MainThread] and exposes them to
 * sub components and to external dependant classes.

 * Scope [Singleton] is used to limit dependency
 * instances across whole execution.

 * @author Pedro Paulo de Amorim
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
  fun inject(threadExecutorApplication: ThreadExecutorApplication)
  fun application(): Application
  fun executor(): InteractorExecutor
  fun mainThread(): MainThread
}
