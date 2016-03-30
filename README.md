# ThreadExecutor

[![Build Status](https://api.travis-ci.org/ppamorim/ThreadExecutor.svg?branch=master)](https://travis-ci.org/ppamorim/ThreadExecutor)

Simple library that expose the `ThreadPool`, using Dagger 2.

Why?
----

Are you sad to create every time the `ThreadPool` and `MainThread` to be exposed with
Dagger to the application? If yes, you can use this library to reduce your work.

Right now you can change any parameter of `ThreadExecutor`, using the exposed instance
to do it.

Custom configuration
--------------------

You can easily change any parameter of `InteractorExecutor`, just change what do you want and run `build()`.

```java

@Provides @Singleton InteractorExecutor provideThreadExecutor(ThreadExecutor executor) {
    executor.setMaxPoolSize(6)
        .setKeepAliveTime(240)
        .build();
    return executor;
  }

```

Import dependency
-----------------

Into your build.gradle:

```groovy

repositories {
  maven {
    url "https://jitpack.io"
  }
}

dependencies {
  compile 'com.github.ppamorim:threadexecutor:0.1'
}
```

Contributors
------------

* [Pedro Paulo de Amorim][11]

License
-------

    Copyright 2016 Pedro Paulo de Amorim

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[11]: https://github.com/ppamorim
