/*
* Copyright (C) 2016 Pedro Paulo de Amorim
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
package com.threadexecutor.sample.di;

import android.app.Application;
import com.github.ppamorim.threadexecutor.InteractorExecutor;
import com.github.ppamorim.threadexecutor.MainThread;
import com.github.ppamorim.threadexecutor.MainThreadImpl;
import com.github.ppamorim.threadexecutor.ThreadExecutor;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * This module is necessary to inject the
 * instance of application context and
 * the thread to be used on the interactors.
 */
@Module public class ApplicationModule {

  private final Application application;

  public ApplicationModule(Application application) {
    this.application = application;
  }

  @Provides @Singleton Application provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton InteractorExecutor provideThreadExecutor(ThreadExecutor executor) {
    executor.setMaxPoolSize(6)
        .setKeepAliveTime(240);
    return executor;
  }

  @Provides @Singleton MainThread providePostExecutionThread(MainThreadImpl mainThread) {
    return mainThread;
  }

}
