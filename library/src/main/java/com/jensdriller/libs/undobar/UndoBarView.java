package com.jensdriller.libs.undobar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class UndoBarView extends MaxWidthLinearLayout {

	public UndoBarView(Context context) {
		super(context);
	}

	public UndoBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public UndoBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private TextView mMessage;
	private View mButton;

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		mMessage = (TextView) findViewById(R.id.message);
		mButton = findViewById(R.id.button);
	}

	void setMessage(CharSequence message) {
		mMessage.setText(message);
	}

	void setOnUndoClickListener(OnClickListener onClickListener) {
		mButton.setOnClickListener(onClickListener);
	}

	@Override
	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);
		ss.message = mMessage.getText().toString();
		return ss;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		if (!(state instanceof SavedState)) {
			super.onRestoreInstanceState(state);
			return;
		}

		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());
		setMessage(ss.message);
	}

	private static class SavedState extends BaseSavedState {

		private String message;

		SavedState(Parcelable superState) {
			super(superState);
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeString(message);
		}

		private SavedState(Parcel in) {
			super(in);
			message = in.readString();
		}

		@SuppressWarnings("unused")
		private static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {

			@Override
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}
}
