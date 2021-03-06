package io.blushine.android.common;

import androidx.annotation.RawRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import io.blushine.android.AppActivity;
import io.blushine.android.R;
import io.blushine.android.info.InfoActivity;

/**
 * Used for checking if a document has been changed. E.g. privacy policy.
 */
public class DocumentChangeChecker {
private static DocumentChangeChecker mInstance = null;
private DocumentChangePrefsGateway mPrefsGateway = new DocumentChangePrefsGateway();

/**
 * Enforces singleton pattern
 */
private DocumentChangeChecker() {
}

/**
 * Get singleton instance
 * @return get instance
 */
public static DocumentChangeChecker getInstance() {
	if (mInstance == null) {
		mInstance = new DocumentChangeChecker();
	}
	return mInstance;
}

/**
 * Check if a document has been changed. If it has been changed a pop-up will be displayed asking if
 * the user wants to display the document.
 * @param rawId document to check
 * @param titleId document title to display in pop-up
 * @param messageId message to display in the pop-up
 */
public void checkDocument(@RawRes final int rawId, @StringRes final int titleId, @StringRes int messageId) {
	boolean hasChanged = mPrefsGateway.checkChangedAndUpdate(rawId);

	// Display dialog
	if (hasChanged) {
		AlertDialog dialog = new AlertDialog.Builder(AppActivity.getActivity())
				.setTitle(titleId)
				.setMessage(messageId)
				.setPositiveButton(R.string.accept, null)
				.setNeutralButton(R.string.view_changes, (dialog1, which) -> InfoActivity.show(titleId, rawId))
				.show();
	}
}
}
