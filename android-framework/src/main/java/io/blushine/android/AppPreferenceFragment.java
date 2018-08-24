package io.blushine.android;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Base class for settings fragments
 */
public abstract class AppPreferenceFragment extends PreferenceFragmentCompat {
private AppFragmentHelper mFragmentHelper = new AppFragmentHelper(this);

@Override
public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
	// TODO?
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	return super.onCreateView(inflater, container, savedInstanceState);
}

@Override
public void onViewCreated(View view, Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);
	mFragmentHelper.onViewRestored(view.getRootView(), savedInstanceState);
}

@Override
public void onStop() {
	super.onStop();
	mFragmentHelper.onStop();
}

/**
 * Requires that all activities has a fragment container with id fragment_container.
 */
public void show() {
	AppActivity activity = AppActivity.getActivity();
	FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
	fragmentTransaction.replace(R.id.fragment_container, this);
	fragmentTransaction.addToBackStack(getClass().getSimpleName());
	fragmentTransaction.commit();
}

@Override
public void onResume() {
	super.onResume();
	mFragmentHelper.onResume();
}

@Override
public void onPause() {
	super.onPause();
	mFragmentHelper.onPause();
}

/**
 * Dismiss this window.
 */
public void back() {
	dismiss();
}

/**
 * Dismiss this window.
 */
public void dismiss() {
	mFragmentHelper.dismiss();
}
}
