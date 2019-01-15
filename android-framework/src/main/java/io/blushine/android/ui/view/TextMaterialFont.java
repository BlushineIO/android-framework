package io.blushine.android.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import io.blushine.android.R;
import io.blushine.android.ui.Fonts;

/**
 * Text view that can set a custom material font
 */
public class TextMaterialFont extends AppCompatTextView {
public TextMaterialFont(Context context) {
	super(context);
}

public TextMaterialFont(Context context, AttributeSet attrs) {
	super(context, attrs);
	init(attrs);
}

private void init(AttributeSet attrs) {
	if (attrs != null) {
		TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.TextMaterialFont);

		int fontIndex = attributes.getInt(R.styleable.TextMaterialFont_materialFontFamily, -1);
		Fonts font = Fonts.fromIndex(fontIndex);
		if (font != null) {
			setTypeface(font.getTypeface());
		}

		attributes.recycle();
	}
}

public TextMaterialFont(Context context, AttributeSet attrs, int defStyleAttr) {
	super(context, attrs, defStyleAttr);
	init(attrs);
}
}
