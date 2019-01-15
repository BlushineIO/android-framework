package io.blushine.android.common

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView

/**
 * Various helper classes for editor action listener
 */

/**
 * Checks whether IME_DONE or ENTER has been pressed
 */
fun TextView.OnEditorActionListener.isDoneOrEnterPressed(actionId: Int, keyEvent: KeyEvent): Boolean {
	return KeyHelper.isDoneOrEnterPressed(actionId, keyEvent)
}

class KeyHelper {
	companion object {
		@JvmStatic
		fun isDoneOrEnterPressed(actionId: Int, keyEvent: KeyEvent?): Boolean {
			return actionId == EditorInfo.IME_ACTION_DONE ||
					((keyEvent?.keyCode == KeyEvent.KEYCODE_ENTER || keyEvent?.keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) &&
							keyEvent.action == KeyEvent.ACTION_DOWN)
		}
	}
}