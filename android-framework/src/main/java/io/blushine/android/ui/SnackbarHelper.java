package io.blushine.android.ui;

import androidx.annotation.StringRes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import io.blushine.android.AppActivity;
import io.blushine.android.AppFragmentHelper;
import io.blushine.android.FragmentEvent;
import io.blushine.android.R;
import io.blushine.utils.EventBus;

/**
 * Some helper methods for creating simple snackbars
 */
public class SnackbarHelper {
@Snackbar.Duration
private static final int DURATION_SHORT = Snackbar.LENGTH_SHORT;
@Snackbar.Duration
private static final int DURATION_MEDIUM = Snackbar.LENGTH_LONG;
private static final int LENGTH_MEDIUM_CHARACTERS = 0;
@Snackbar.Duration
private static final int DURATION_LONG = 6000;
private static final int LENGTH_LONG_CHARACTERS = 20;

/**
 * Can't instantiate the class
 */
private SnackbarHelper() {

}

/**
 * Create a simple {@link Snackbar} with a message
 * @param stringId id of the message to show
 */
public static void showSnackbar(@StringRes int stringId) {
	showSnackbar(getString(stringId));
}

/**
 * Create a simple {@link Snackbar} with a message
 * @param message the message to show
 */
public static void showSnackbar(String message) {
	showSnackbar(message, null, null);
}

private static String getString(@StringRes int stringId) {
	return AppActivity.getActivity().getResources().getString(stringId);
}

/**
 * Create a simple {@link Snackbar} with a message and an action
 * @param message the message to show
 * @param actionTitle button title
 * @param action the action to take
 */
public static void showSnackbar(String message, String actionTitle, View.OnClickListener action) {
	int duration = calculateDuration(message);
	SnackbarMessage snackbarMessage = new SnackbarMessage(message, actionTitle, action, duration);
	snackbarMessage.show();
}

private static int calculateDuration(String message) {
	if (message.length() > LENGTH_LONG_CHARACTERS) {
		return DURATION_LONG;
	} else if (message.length() > LENGTH_MEDIUM_CHARACTERS) {
		return DURATION_MEDIUM;
	} else {
		return DURATION_SHORT;
	}
}

/**
 * Create a simlpe {@link Snackbar} with a message and an undo action
 * @param message the message to show
 * @param undoAction the action to take
 */
public static void showSnackbarUndo(String message, View.OnClickListener undoAction) {
	showSnackbar(message, getString(R.string.undo), undoAction);
}

/**
 * Create a simlpe {@link Snackbar} with a message and an undo action
 * @param messageId id of the message to show
 * @param undoAction the action to take
 */
public static void showSnackbarUndo(@StringRes int messageId, View.OnClickListener undoAction) {
	showSnackbar(messageId, R.string.undo, undoAction);
}

/**
 * Create a simple {@link Snackbar} with a message
 * @param messageId id of the message to show
 * @param actionTitleId button title as a string resource id
 * @param action the action to take
 */
public static void showSnackbar(@StringRes int messageId, @StringRes int actionTitleId, View.OnClickListener action) {
	showSnackbar(getString(messageId), getString(actionTitleId), action);
}

/**
 * Check if any snackbar message is shown
 * @return true if any snackbar message is shown or queued
 */
public static boolean isShownOrQueued() {
	return SnackbarMessage.mLastMessage != null && SnackbarMessage.mLastMessage.isShown();
}

/**
 * Container for snackbar messages
 */
private static class SnackbarMessage {
	private static final EventBus mEventBus = EventBus.getInstance();
	private static SnackbarMessage mLastMessage = null;
	private boolean mNeverShown = true;
	private String mMessage;
	private String mActionTitle;
	private int mDuration;
	private View.OnClickListener mAction;
	private Snackbar mSnackbar;
	
	SnackbarMessage(String message, String actionTitle, View.OnClickListener action, int duration) {
		mMessage = message;
		mActionTitle = actionTitle;
		mAction = action;
		mDuration = duration;
	}
	
	@Subscribe
	public void onFragment(FragmentEvent event) {
		if (event.getEventType() == FragmentEvent.EventTypes.RESUME) {
			mEventBus.unregister(this);
			if (this == mLastMessage && (!mNeverShown || isShown())) {
				show();
			}
		}
	}
	
	private boolean isShown() {
		return mSnackbar != null && mSnackbar.isShownOrQueued();
	}
	
	void show() {
		if (AppFragmentHelper.getHelper() != null) {
			final View view = getView();
			mSnackbar = Snackbar.make(view, mMessage, mDuration);
			if (mActionTitle != null && mAction != null) {
				mSnackbar.setAction(mActionTitle, mAction);
			}
			mSnackbar.addCallback(new Snackbar.Callback() {
				@Override
				public void onDismissed(Snackbar snackbar, int event) {
					if (view instanceof FloatingActionButton) {
						fixFloatingActionButtonPosition((FloatingActionButton) view);
					}
					if (mLastMessage == SnackbarMessage.this) {
						mLastMessage = null;
					}
					mEventBus.post(new SnackbarDismissEvent());
				}
			});
			mSnackbar.show();
			mNeverShown = false;
		}
		
		mEventBus.register(this);
		mLastMessage = this;
	}
	
	private View getView() {
		View rootView = AppActivity.getRootView();
		if (rootView instanceof ViewGroup) {
			ViewGroup viewGroup = (ViewGroup) rootView;
			for (int i = 0; i < viewGroup.getChildCount(); i++) {
				View child = viewGroup.getChildAt(i);
				if (child instanceof FloatingActionButton) {
					return child;
				}
			}
		}
		return rootView;
	}
	
	private void fixFloatingActionButtonPosition(FloatingActionButton button) {
		button.setTranslationY(0);
	}
}
}
