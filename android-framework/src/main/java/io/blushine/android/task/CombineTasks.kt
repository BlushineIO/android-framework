package io.blushine.android.task

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import java.util.concurrent.Callable

/**
 * @file
 * Various tasks that wait for each task to finish before completing and then returns all in a combined result
 */
class CombineTask2<T1Result, T2Result>(
		val firstCallable: Callable<T1Result>,
		val secondCallable: Callable<T2Result>
) : Callable<Pair<T1Result, T2Result>> {

	override fun call(): Pair<T1Result, T2Result> {
		val firstTask = Tasks.call(ThreadPools.CACHED_THREAD_POOL, firstCallable)
		val secondTask = Tasks.call(ThreadPools.CACHED_THREAD_POOL, secondCallable)

		val firstResult = Tasks.await(firstTask)
		val secondResult = Tasks.await(secondTask)

		return Pair(firstResult, secondResult)
	}

	fun run(): Task<Pair<T1Result, T2Result>> {
		return Tasks.call(ThreadPools.CACHED_THREAD_POOL, this)
	}
}