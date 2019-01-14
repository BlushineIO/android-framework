package io.blushine.android.common

/**
 * Base class for object events. Used for add, edit, and remove actions.
 */
abstract class ObjectEvent<ObjectType>(val action: Actions, val objects: List<ObjectType>) {
	/**
	 * @return the first object in this event
	 */
	val firstObject: ObjectType
		get() = this.objects[0]

	protected constructor(action: Actions) : this(action, listOf())
	protected constructor(action: Actions, `object`: ObjectType) : this(action, listOf(`object`))


	/**
	 * @return true if the event has objects
	 */
	fun hasObjects(): Boolean {
		return !this.objects.isEmpty()
	}

	/**
	 * The different actions
	 */
	enum class Actions {
		/** Add one or several objects to the DB  */
		ADD,
		/** Edit one or several objects in the DB  */
		EDIT,
		/** Remove one or several objects to the DB  */
		REMOVE,
		/** Called after [.ADD], i.e., after an object has been added to the DB  */
		ADDED,
		/** Called after [.EDIT], i.e., after an object has been edited in the DB  */
		EDITED,
		/** Called after [.REMOVE], i.e., after an objects has been removed from the DB  */
		REMOVED,
		/** Result/response from a GET all objects call  */
		GET_RESPONSE,
		/** Failed to add objects  */
		ADD_FAILED,
		/** Failed to edit objects  */
		EDIT_FAILED,
		/** Failed to remove objects  */
		REMOVE_FAILED,
		/** Failed to get objects  */
		GET_FAILED
	}
}
