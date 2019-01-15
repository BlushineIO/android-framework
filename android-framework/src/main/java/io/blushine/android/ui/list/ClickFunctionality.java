package io.blushine.android.ui.list;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Edit an item by long clicking on it
 */
class ClickFunctionality<T> implements PostBindFunctionality<T> {
private ClickListener<T> mListener;

ClickFunctionality(ClickListener<T> listener) {
	if (listener == null) {
		throw new IllegalArgumentException("listener is null");
	}
	
	mListener = listener;
}

@Override
public void applyFunctionality(AdvancedAdapter<T, ?> adapter, RecyclerView recyclerView) {
	// Does nothing
}

@Override
public void onPostBind(final AdvancedAdapter<T, ?> adapter, RecyclerView.ViewHolder viewHolder, int position) {
	viewHolder.itemView.setOnClickListener(view -> {
		int adapterPosition = viewHolder.getAdapterPosition();
		T item = adapter.getItem(adapterPosition);
		mListener.onClick(item);
	});
}
}
