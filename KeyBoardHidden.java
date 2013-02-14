package com.chikuwastudio.photospotsearch.util;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class KeyBoardHidden implements TextView.OnEditorActionListener {

	private Context context;

	/**
	 * コンストラクタ
	 * @param context
	 */
	public KeyBoardHidden(Context context){
		this.context = context;
	}

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        if(event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if(event.getAction() == KeyEvent.ACTION_UP) {

                // ソフトキーボードを隠す
                ((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            return true;
        }
        return false;
    }
}
