package com.yy.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.yy.constant.Constant;
import com.yy.house.R;

public class BottomControlPanel extends RelativeLayout implements View.OnClickListener {
	private Context mContext;
	private ImageText mMsgBtn = null;
	private ImageText mContactsBtn = null;
	private ImageText mNewsBtn = null;
	private ImageText mSettingBtn = null;
	private int DEFALUT_BACKGROUND_COLOR = Color.rgb(243, 243, 243); // Color.rgb(192,
																		// 192,
																		// 192)
	private BottomPanelCallback mBottomCallback = null;
	private List<ImageText> viewList = new ArrayList<ImageText>();

	public interface BottomPanelCallback {
		public void onBottomPanelClick(int itemId);
	}

	public BottomControlPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		mMsgBtn = (ImageText) findViewById(R.id.btn_message);
		mContactsBtn = (ImageText) findViewById(R.id.btn_contacts);
		mNewsBtn = (ImageText) findViewById(R.id.btn_news);
		mSettingBtn = (ImageText) findViewById(R.id.btn_setting);
		setBackgroundColor(DEFALUT_BACKGROUND_COLOR);
		viewList.add(mMsgBtn);
		viewList.add(mContactsBtn);
		viewList.add(mNewsBtn);
		viewList.add(mSettingBtn);

	}

	public void initBottomPanel() {
		if (mMsgBtn != null) {
			mMsgBtn.setImage(R.drawable.contract_unselected);
			mMsgBtn.setText("合同");
		}
		if (mContactsBtn != null) {
			mContactsBtn.setImage(R.drawable.house_unselected);
			mContactsBtn.setText("房屋");
		}
		if (mNewsBtn != null) {
			mNewsBtn.setImage(R.drawable.rental_unselected);
			mNewsBtn.setText("租金");
		}
		if (mSettingBtn != null) {
			mSettingBtn.setImage(R.drawable.setting_unselected);
			mSettingBtn.setText("设置");
		}
		setBtnListener();

	}

	private void setBtnListener() {
		int num = this.getChildCount();
		for (int i = 0; i < num; i++) {
			View v = getChildAt(i);
			if (v != null) {
				v.setOnClickListener(this);
			}
		}
	}

	public void setBottomCallback(BottomPanelCallback bottomCallback) {
		mBottomCallback = bottomCallback;
	}

	public void setBtnChecked(String flag) {
		if (flag.equals(Constant.FRAGMENT_FLAG_CONTRACT)) {
			mMsgBtn.setChecked(Constant.BTN_FLAG_CONTRACT);
		} else if (flag.equals(Constant.FRAGMENT_FLAG_HOUSE)) {
			mContactsBtn.setChecked(Constant.BTN_FLAG_HOUSE);
		} else if (flag.equals(Constant.FRAGMENT_FLAG_RENTAL)) {
			mNewsBtn.setChecked(Constant.BTN_FLAG_RENTAL);
		} else if (flag.equals(Constant.FRAGMENT_FLAG_SETTING)) {
			mSettingBtn.setChecked(Constant.BTN_FLAG_SETTING);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		initBottomPanel();
		int index = -1;
		switch (v.getId()) {
		case R.id.btn_message:
			index = Constant.BTN_FLAG_CONTRACT;
			mMsgBtn.setChecked(Constant.BTN_FLAG_CONTRACT);
			break;
		case R.id.btn_contacts:
			index = Constant.BTN_FLAG_HOUSE;
			mContactsBtn.setChecked(Constant.BTN_FLAG_HOUSE);
			break;
		case R.id.btn_news:
			index = Constant.BTN_FLAG_RENTAL;
			mNewsBtn.setChecked(Constant.BTN_FLAG_RENTAL);
			break;
		case R.id.btn_setting:
			index = Constant.BTN_FLAG_SETTING;
			mSettingBtn.setChecked(Constant.BTN_FLAG_SETTING);
			break;
		default:
			break;
		}
		if (mBottomCallback != null) {
			mBottomCallback.onBottomPanelClick(index);
		}
	}

	public void defaultBtnChecked() {
		if (mMsgBtn != null) {
			mMsgBtn.setChecked(Constant.BTN_FLAG_CONTRACT);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		layoutItems(left, top, right, bottom);
	}

	/**
	 * 最左边和最右边的view由母布局的padding进行控制位置。这里需对第2、3个view的位置重新设置
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	private void layoutItems(int left, int top, int right, int bottom) {
		int n = getChildCount();
		if (n == 0) {
			return;
		}
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		Log.i("yy", "paddingLeft = " + paddingLeft + " paddingRight = " + paddingRight);
		int width = right - left;
		int height = bottom - top;
		Log.i("yy", "width = " + width + " height = " + height);
		int allViewWidth = 0;
		for (int i = 0; i < n; i++) {
			View v = getChildAt(i);
			Log.i("yy", "v.getWidth() = " + v.getWidth());
			allViewWidth += v.getWidth();
		}
		int blankWidth = (width - allViewWidth - paddingLeft - paddingRight) / (n - 1);
		Log.i("yy", "blankV = " + blankWidth);

		LayoutParams params1 = (LayoutParams) viewList.get(1).getLayoutParams();
		params1.leftMargin = blankWidth;
		viewList.get(1).setLayoutParams(params1);

		LayoutParams params2 = (LayoutParams) viewList.get(2).getLayoutParams();
		params2.leftMargin = blankWidth;
		viewList.get(2).setLayoutParams(params2);
	}

}
