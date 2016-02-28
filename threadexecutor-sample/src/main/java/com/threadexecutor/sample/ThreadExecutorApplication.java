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
package com.threadexecutor.sample;

import android.app.Application;
import com.threadexecutor.sample.di.ApplicationModule;
import com.threadexecutor.sample.di.components.ApplicationComponent;
import com.threadexecutor.sample.di.components.DaggerApplicationComponent;

public class ThreadExecutorApplication extends Application {

  private ApplicationComponent applicationComponent;

  public ThreadExecutorApplication() {
    super();
  }

  @Override public void onCreate() {
    super.onCreate();
    initializeDependencyInjector().inject(this);
  }

  /**
   * This methid will generate the component tha will inject
   * the Thread and the Pool of Threads.
   * @return Component generate and loaded with his module.
   */
  private ApplicationComponent initializeDependencyInjector() {
    if (applicationComponent == null) {
      applicationComponent = DaggerApplicationComponent.builder()
          .applicationModule(new ApplicationModule(this))
          .build();
    }
    return applicationComponent;
  }

  /**
   * Expose the component for Activities and Fragments.
   * @return The main component of the app.
   */
  public ApplicationComponent component() {
    return applicationComponent;
  }


}
