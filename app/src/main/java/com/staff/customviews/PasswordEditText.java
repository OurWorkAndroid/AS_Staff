package com.staff.customviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.staff.main.R;


/**
 * 自定义显示隐藏密码的EditText
 */
public class PasswordEditText extends EditText{
	/**
	 * 删除按钮的引用
	 */
	private Drawable mClearDrawable;
	private boolean mVisible;

	public PasswordEditText(Context context) {
		this(context, null);
	}

	public PasswordEditText(Context context, AttributeSet attrs) {
		// 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public PasswordEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(); 
	}

	private void init() {
		setShowPassword(mVisible);
	}

	/**
	 * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件 当我们按下的位置 在 EditText的宽度 -
	 * 图标到控件右边的间距 - 图标的宽度 和 EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向没有考虑
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (getCompoundDrawables()[2] != null) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				boolean touch = event.getX() > (getWidth()
						- getPaddingRight() - mClearDrawable
							.getIntrinsicWidth())
						&& (event.getX() < ((getWidth() - getPaddingRight())));
				if (touch) {
					mVisible = !mVisible;
					setShowPassword(mVisible);
				}
			}
		}

		return super.onTouchEvent(event);
	}

	public void setVisible(boolean visible){
		this.mVisible = visible;
		setShowPassword(mVisible);
	}

	private void setShowPassword(boolean show) {
		if (show) {
			setInputType(InputType.TYPE_CLASS_TEXT);
			mClearDrawable = getResources().getDrawable(R.drawable.login_password_hide);

		} else {
			setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			mClearDrawable = getResources().getDrawable(R.drawable.login_password_show);

		}

		mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
				mClearDrawable.getIntrinsicHeight());
		setCompoundDrawables(getCompoundDrawables()[0],
				getCompoundDrawables()[1], mClearDrawable, getCompoundDrawables()[3]);
	}

}
