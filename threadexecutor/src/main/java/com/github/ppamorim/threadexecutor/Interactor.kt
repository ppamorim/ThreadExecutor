package com.github.ppamorim.threadexecutor

/**
 * Interactor that will be used to execute
 * a determined task on the async thread.
 */
interface Interactor {
  fun run()
}
