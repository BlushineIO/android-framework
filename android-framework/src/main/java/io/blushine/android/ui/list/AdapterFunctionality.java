package io.blushine.android.ui.list;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Base class for adapter functionality
 */
public interface AdapterFunctionality<T> {

void applyFunctionality(AdvancedAdapter<T, ?> adapter, RecyclerView recyclerView);
}
