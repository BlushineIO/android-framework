package io.blushine.android.ui.list;

import android.annotation.SuppressLint;
import androidx.annotation.IdRes;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Add drag and drop functionality to the {@link AdvancedAdapter}.
 */
class MoveFunctionality<T> implements PostBindFunctionality<T> {
private static final String TAG = MoveFunctionality.class.getSimpleName();
private static final int INVALID_MOVE_BUTTON = -1;
private MoveListener<T> mListener;
private androidx.recyclerview.widget.ItemTouchHelper mItemTouchHelper;
@IdRes private int mMoveButtonId;

/**
 * Long press to start drag and drop
 * @param listener listens to when an item has been moved
 */
MoveFunctionality(MoveListener<T> listener) {
	this(listener, INVALID_MOVE_BUTTON);
}

/**
 * Press the specified button id to start dragging
 * @param listener listens to when an item has been moved
 * @param moveButtonId resource id of the button to start moving the item
 */
MoveFunctionality(MoveListener<T> listener, @IdRes int moveButtonId) {
	mListener = listener;
	mMoveButtonId = moveButtonId;
}

@Override
public void applyFunctionality(AdvancedAdapter<T, ?> adapter, RecyclerView recyclerView) {
	MoveCallback moveCallback = new MoveCallback(adapter);
	mItemTouchHelper = new androidx.recyclerview.widget.ItemTouchHelper(moveCallback);
	mItemTouchHelper.attachToRecyclerView(recyclerView);
}


@SuppressLint("ClickableViewAccessibility")
@Override
public void onPostBind(AdvancedAdapter<T, ?> adapter, final RecyclerView.ViewHolder viewHolder, int position) {
	// Start dragging when we press the move button
	if (mMoveButtonId != INVALID_MOVE_BUTTON) {
		View button = viewHolder.itemView.findViewById(mMoveButtonId);
		if (button != null) {
			button.setOnTouchListener((v, event) -> {
				if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
					mItemTouchHelper.startDrag(viewHolder);
				}
				return false;
			});
		} else {
			Log.w(TAG, "onPostBind() — Couldn't find button to start moving list item");
		}
	}
}

private class MoveCallback extends ItemTouchHelper.Callback {
	private static final int POSITION_NOT_SET = -1;
	private AdvancedAdapter<T, ?> mAdapter;
	private int mFromPosition = POSITION_NOT_SET;
	private int mToPosition = POSITION_NOT_SET;
	
	MoveCallback(AdvancedAdapter<T, ?> advancedAdapter) {
		mAdapter = advancedAdapter;
	}
	
	@Override
	public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
		int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
		return makeMovementFlags(dragFlags, 0);
	}
	
	@Override
	public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
		T item = mAdapter.getItem(viewHolder.getAdapterPosition());
		int fromPosition = viewHolder.getAdapterPosition();
		mToPosition = target.getAdapterPosition();
		mAdapter.move(fromPosition, mToPosition);
		
		if (mFromPosition == POSITION_NOT_SET) {
			mFromPosition = fromPosition;
		}
		return true;
	}
	
	@Override
	public boolean isLongPressDragEnabled() {
		return mMoveButtonId == INVALID_MOVE_BUTTON;
	}
	
	@Override
	public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
		// Does nothing
	}
	
	@Override
	public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
		super.clearView(recyclerView, viewHolder);
		
		if (mFromPosition != mToPosition) {
			T item = mAdapter.getItem(viewHolder.getAdapterPosition());
			mListener.onMoved(item, mFromPosition, mToPosition);
		}
		mFromPosition = POSITION_NOT_SET;
		mToPosition = POSITION_NOT_SET;
	}
}
}
