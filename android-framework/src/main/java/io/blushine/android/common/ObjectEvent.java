package io.blushine.android.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for object events. Used for add, edit, and remove actions.
 */
public abstract class ObjectEvent<ObjectType> {
private final Actions mAction;
private final List<ObjectType> mObjects = new ArrayList<>();

protected ObjectEvent(ObjectType object, Actions action) {
	mAction = action;
	mObjects.add(object);
}

protected ObjectEvent(List<ObjectType> objects, Actions action) {
	if (objects.isEmpty()) {
		throw new IllegalArgumentException("objects is empty");
	}
	
	mAction = action;
	mObjects.addAll(objects);
}

/**
 * @return all objects in this event
 */
public List<ObjectType> getObjects() {
	return mObjects;
}

/**
 * @return the first object in this event
 */
public ObjectType getFirstObject() {
	return mObjects.get(0);
}

/**
 * Get the action to take on the object event
 * @return the action to take on the object event
 */
public Actions getAction() {
	return mAction;
}

/**
 * The different actions
 */
public enum Actions {
	/** Add one or several objects to the DB */
	ADD,
	/** Edit one or several objects in the DB */
	EDIT,
	/** Remove one or several objects to the DB */
	REMOVE,
	/** Called after {@link #ADD}, i.e., after an object has been added to the DB */
	ADDED,
	/** Called after {@link #EDIT}, i.e., after an object has been edited in the DB */
	EDITED,
	/** Called after {@link #REMOVE}, i.e., after an objects has been removed from the DB */
	REMOVED,
	/** Result/response from a GET all objects call */
	GET_RESPONSE,
}
}
