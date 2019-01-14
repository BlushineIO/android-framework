package io.blushine.android;

import android.content.Intent;

/**
 * Called when an activity gets a result from starting another activity.
 * Useful for letting other classes handle results.
 */
public class ActivityResultEvent {
private int mRequestCode;
private int mResultCode;
private Intent mData;

ActivityResultEvent(int requestCode, int resultCode, Intent data) {
	mRequestCode = requestCode;
	mResultCode = resultCode;
	mData = data;
}

/**
 * Get the request code
 * @return The integer request code originally supplied to startActivityForResult(), allowing you to identify who this result came from.
 */
public int getRequestCode() {
	return mRequestCode;
}

/**
 * Get the result code
 * @return The integer result code returned by the child activity through its setResult().
 */
public int getResultCode() {
	return mResultCode;
}

/**
 * Get the data
 * @return An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
 */
public Intent getData() {
	return mData;
}
}
