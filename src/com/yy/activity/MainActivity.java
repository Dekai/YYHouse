package com.yy.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;

import com.yy.constant.Constant;
import com.yy.fragment.BaseFragment;
import com.yy.house.R;
import com.yy.ui.BottomControlPanel;
import com.yy.ui.BottomControlPanel.BottomPanelCallback;
import com.yy.ui.HeadControlPanel;
import com.yy.vo.User;

public class MainActivity extends BaseActivity implements BottomPanelCallback {
	BottomControlPanel bottomPanel = null;
	HeadControlPanel headPanel = null;

	private FragmentManager fragmentManager = null;
	private FragmentTransaction fragmentTransaction = null;
	public static String currFragTag = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
		fragmentManager = getFragmentManager();
		String fragFlag = getIntent().getStringExtra("fragFlag");
		if (fragFlag != null) {
			switchFragment(fragFlag);
			bottomPanel.setBtnChecked(fragFlag);
		} else {
			setDefaultFirstFragment(Constant.FRAGMENT_FLAG_CONTRACT);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initUI() {
		bottomPanel = (BottomControlPanel) findViewById(R.id.bottom_layout);
		if (bottomPanel != null) {
			bottomPanel.initBottomPanel();
			bottomPanel.setBottomCallback(this);
		}
		loginUser = (User) getIntent().getSerializableExtra("loginUser");
	}

	/*
	 * 处理BottomControlPanel的回调
	 * 
	 * @see
	 * org.yanzi.ui.BottomControlPanel.BottomPanelCallback#onBottomPanelClick
	 * (int)
	 */
	@Override
	public void onBottomPanelClick(int itemId) {
		// TODO Auto-generated method stub
		String tag = "";
		if ((itemId & Constant.BTN_FLAG_CONTRACT) != 0) {
			tag = Constant.FRAGMENT_FLAG_CONTRACT;
		} else if ((itemId & Constant.BTN_FLAG_HOUSE) != 0) {
			tag = Constant.FRAGMENT_FLAG_HOUSE;
		} else if ((itemId & Constant.BTN_FLAG_RENTAL) != 0) {
			tag = Constant.FRAGMENT_FLAG_RENTAL;
		} else if ((itemId & Constant.BTN_FLAG_SETTING) != 0) {
			tag = Constant.FRAGMENT_FLAG_SETTING;
		}
		switchFragment(tag); // 切换Fragment
		// headPanel.setMiddleTitle(tag);//切换标题
	}

	private void setDefaultFirstFragment(String tag) {
		Log.i("yan", "setDefaultFirstFragment enter... currFragTag = " + currFragTag);
		switchFragment(tag);
		bottomPanel.defaultBtnChecked();
		Log.i("yan", "setDefaultFirstFragment exit...");
	}

	private void commitTransactions(String tag) {
		if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
			fragmentTransaction.commit();
			currFragTag = tag;
			fragmentTransaction = null;
		}
	}

	private FragmentTransaction ensureTransaction() {
		if (fragmentTransaction == null) {
			fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

		}
		return fragmentTransaction;

	}

	private void attachFragment(int layout, Fragment f, String tag) {
		if (f != null) {
			if (f.isDetached()) {
				ensureTransaction();
				fragmentTransaction.attach(f);

			} else if (!f.isAdded()) {
				ensureTransaction();
				fragmentTransaction.add(layout, f, tag);
			}
		}
	}

	private Fragment getFragment(String tag) {

		Fragment f = fragmentManager.findFragmentByTag(tag);

		if (f == null) {
			f = BaseFragment.newInstance(getApplicationContext(), tag);
		}
		return f;

	}

	private void detachFragment(Fragment f) {

		if (f != null && !f.isDetached()) {
			ensureTransaction();
			fragmentTransaction.detach(f);
		}
	}

	/**
	 * 切换fragment
	 * 
	 * @param tag
	 */
	private void switchFragment(String tag) {
		fragmentTransaction = fragmentManager.beginTransaction();
		if (TextUtils.equals(tag, currFragTag)) {
			return;
		}
		// 把上一个fragment detach掉
		if (currFragTag != null && !currFragTag.equals("")) {
			detachFragment(getFragment(currFragTag));
		}
		attachFragment(R.id.fragment_content, getFragment(tag), tag);
		commitTransactions(tag);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		currFragTag = "";
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void setContentView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

}
