package io.blushine.android.task

import java.util.concurrent.Executors

class ThreadPools {
	companion object {
		/**
		 * Default cached threadpool
		 */
		val CACHED_THREAD_POOL = Executors.newCachedThreadPool()
	}
}

