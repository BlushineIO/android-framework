package io.blushine.android.info;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import io.blushine.android.AppActivity;
import io.blushine.android.AppFragment;
import io.blushine.android.R;
import io.blushine.utils.Strings;

/**
 * Display some text information in a fragment
 * <p/>
 * Remember to call {@link #setArguments(String, String)}, {@link #setArguments(int, int...)}, or
 * {@link #setArguments(int, String, int...)}.
 */
public class InfoFragment extends AppFragment {
private static final String TITLE_KEY = "title";
private static final String TEXT_KEY = "text";
private static final String TAG = InfoFragment.class.getSimpleName();
private static final String DEFAULT_VALUE = "NOT SET";
private String mTitle = DEFAULT_VALUE;
private String mText = DEFAULT_VALUE;

@Override
protected void onDeclareArguments() {
	super.onDeclareArguments();
	declareArgument(TITLE_KEY, ArgumentRequired.REQUIRED);
	declareArgument(TEXT_KEY, ArgumentRequired.REQUIRED);
}

@Override
protected void onArgumentsSet() {
	super.onArgumentsSet();
	mTitle = getArgument(TITLE_KEY);
	mText = getArgument(TEXT_KEY);
}

@Nullable
@Override
public View onCreateViewImpl(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.fragment_info, container, false);
	
	// Toolbar title
	Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
	toolbar.setTitle(mTitle);
	toolbar.setNavigationOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			back();
		}
	});
	
	// Text
	TextView infoText = (TextView) view.findViewById(R.id.info_text);
	// HTML
	if (Strings.isHtml(mText)) {
		Spanned htmlFormattedText;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			htmlFormattedText = Html.fromHtml(mText, Html.FROM_HTML_MODE_LEGACY);
		} else {
			htmlFormattedText = Html.fromHtml(mText);
		}
		infoText.setText(htmlFormattedText);
	}
	// Plain Text
	else {
		infoText.setText(mText);
	}
	
	return view;
}

/**
 * Set the arguments for this fragment
 * @param titleId toolbar title id of this info fragment
 * @param rawIds the text file(s) to concatenate and show
 */
public void setArguments(@StringRes int titleId, @RawRes int... rawIds) {
	setArguments(createArguments(titleId, rawIds));
}

/**
 * Create arguments for this fragment using text files
 * @param titleId toolbar title id of this info fragment
 * @param rawIds the text file(s) to concatenate and show
 */
static Bundle createArguments(@StringRes int titleId, @RawRes int... rawIds) {
	return createArguments(titleId, "", rawIds);
}

/**
 * Create arguments for this fragment using text files
 * @param titleId toolbar title id of this info fragment
 * @param separator separator between the text files
 * @param rawIds the text file(s) to concatenate and show
 */
static Bundle createArguments(@StringRes int titleId, String separator, @RawRes int... rawIds) {
	Resources resources = AppActivity.getActivity().getResources();
	String title = resources.getString(titleId);
	String text = "";
	
	try {
		boolean firstTime = true;
		for (int rawId : rawIds) {
			// Separator
			if (!firstTime) {
				text += separator;
			} else {
				firstTime = false;
			}
			
			// App header
			InputStream inputStream = resources.openRawResource(rawId);
			byte[] bytes = new byte[inputStream.available()];
			int read = inputStream.read(bytes);
			if (read > 0) {
				text += new String(bytes);
			}
		}
	} catch (IOException e) {
		Log.e(TAG, "show() — Couldn't parse raw text file", e);
	}
	
	return createArguments(title, text);
}

/**
 * Create arguments for this fragment
 * @param title the toolbar title of this info fragment
 * @param text the text to display in the fragment
 */
static Bundle createArguments(String title, String text) {
	Bundle arguments = new Bundle(2);
	arguments.putString(TITLE_KEY, title);
	arguments.putString(TEXT_KEY, text);
	return arguments;
}

/**
 * Set the arguments for this fragment
 * @param titleId toolbar title id of this info fragment
 * @param separator separator between the text files
 * @param rawIds the text file(s) to concatenate and show
 */
public void setArguments(@StringRes int titleId, String separator, @RawRes int... rawIds) {
	setArguments(createArguments(titleId, separator, rawIds));
}

/**
 * Set the arguments for this fragment
 * @param title the toolbar title of this info fragment
 * @param text the text to display in the fragment
 */
public void setArguments(String title, String text) {
	setArguments(createArguments(title, text));
}
}
