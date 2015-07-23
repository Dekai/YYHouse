package com.yy.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yy.house.R;
import com.yy.util.SystemMethod;

public class TitleBarView extends RelativeLayout {

	private static final String TAG = "TitleBarView";
	private Context mContext;
	private Button btnLeft;
	private Button btnRight;
	private TextView tv_center;
	private TextView tv_right;
	private LinearLayout common_constact;
	private Button btn_centerLeft;
	private Button btn_centerRight;

	public TitleBarView(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	private void initView() {
		LayoutInflater.from(mContext).inflate(R.layout.common_title_bar, this);
		btnLeft = (Button) findViewById(R.id.title_btn_left);
		btnRight = (Button) findViewById(R.id.title_btn_right);
		tv_center = (TextView) findViewById(R.id.title_txt);
		tv_right = (TextView) findViewById(R.id.title_right_txt);
		common_constact = (LinearLayout) findViewById(R.id.common_constact);
		btn_centerLeft = (Button) findViewById(R.id.btn_centerLeft);
		btn_centerRight = (Button) findViewById(R.id.btn_centerRight);
	}

	public void setCommonTitle(int LeftVisibility, int LeftText, int rightVisibility) {
		setCommonTitle(LeftVisibility, LeftText, View.GONE, rightVisibility);

	}

	public void setCommonTitle(int LeftVisibility, int centerVisibility, int center1Visibilter, int rightVisibility) {
		btnLeft.setVisibility(LeftVisibility);
		tv_center.setVisibility(centerVisibility);
		tv_right.setVisibility(rightVisibility);
		common_constact.setVisibility(center1Visibilter);

	}

	public void setTvRightVisibility(int rightVisibility) {
		tv_right.setVisibility(rightVisibility);
	}

	public void setBtnLeft(int icon, int txtRes) {
		Drawable img = mContext.getResources().getDrawable(icon);
		int height = SystemMethod.dip2px(mContext, 20);
		int width = img.getIntrinsicWidth() * height / img.getIntrinsicHeight();
		img.setBounds(0, 0, width, height);
		btnLeft.setText(txtRes);
		btnLeft.setCompoundDrawables(img, null, null, null);
	}

	public void setBtnLeft(int txtRes) {
		btnLeft.setText(txtRes);
	}

	public void setBtnRightText(int txtRes) {
		btnRight.setText(txtRes);
	}

	public void setCenterBtnLeftText(int txtRes) {
		btn_centerLeft.setText(txtRes);
	}

	public void setCenterBtnRightText(int txtRes) {
		btn_centerRight.setText(txtRes);
	}

	public void setBtnRight(int icon) {
		Drawable img = mContext.getResources().getDrawable(icon);
		int height = SystemMethod.dip2px(mContext, 30);
		int width = img.getIntrinsicWidth() * height / img.getIntrinsicHeight();
		img.setBounds(0, 0, width, height);
		btnRight.setCompoundDrawables(img, null, null, null);
	}

	@SuppressLint("NewApi")
	public void setPopWindow(PopupWindow mPopWindow, TitleBarView titleBaarView) {
		mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E9E9E9")));
		mPopWindow.showAsDropDown(titleBaarView, 0, -15);
		mPopWindow.setAnimationStyle(R.style.popwin_anim_style);
		mPopWindow.setFocusable(true);
		mPopWindow.setOutsideTouchable(true);
		mPopWindow.update();

		setBtnRight(R.drawable.skin_conversation_title_right_btn_selected);
	}

	public void setTitleText(int txtRes) {
		tv_center.setText(txtRes);
	}

	public void setBtnLeftOnclickListener(OnClickListener listener) {
		btnLeft.setOnClickListener(listener);
	}

	public void setBtnRightOnclickListener(OnClickListener listener) {
		btnRight.setOnClickListener(listener);
	}

	public void setCenterBtnLeftOnclickListener(OnClickListener listener) {
		btn_centerLeft.setOnClickListener(listener);
	}

	public void setCenterBtnRightOnclickListener(OnClickListener listener) {
		btn_centerRight.setOnClickListener(listener);
	}

	public Button getBtnCenterLeft() {
		return btn_centerLeft;
	}

	public Button getBtnCenterRight() {
		return btn_centerRight;
	}

	public void destoryView() {
		btnLeft.setText(null);
		btnRight.setText(null);
		tv_center.setText(null);
	}

}
