package com.yy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yy.activity.LoginActivity;
import com.yy.activity.MainActivity;
import com.yy.activity.RegisterActivity;
import com.yy.constant.Constant;
import com.yy.house.R;
import com.yy.ui.TitleBarView;
import com.yy.util.UpdateManager;

public class SettingFragment extends BaseFragment {

	private static final String TAG = "SettingFragment";
	private MainActivity mMainActivity;

	@ViewInject(R.id.title_bar)
	protected TitleBarView mTitleBarView;

	@ViewInject(R.id.rl_mine_info)
	protected RelativeLayout rl_mine_info;

	@ViewInject(R.id.rl_update)
	protected RelativeLayout rl_update;

	@ViewInject(R.id.btn_logout)
	protected Button btn_logout;

	@ViewInject(R.id.tv_user)
	protected TextView tv_user;

	@ViewInject(R.id.tv_phone)
	protected TextView tv_phone;

	private String svrAddress = "http://yydaddy.xicp.net/";
	private String xmlFile = "houseversion.xml";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		fragmentLayout = inflater.inflate(R.layout.setting_layout, container, false);

		mMainActivity = (MainActivity) getActivity();
		mFragmentManager = getActivity().getFragmentManager();
		ViewUtils.inject(this, fragmentLayout);
		initialize();
		return fragmentLayout;
	}

	protected void initialize() {
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.VISIBLE);
		mTitleBarView.setTitleText(R.string.setting);
		mTitleBarView.setBtnRightText(R.string.headlogout);
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mMainActivity, LoginActivity.class);
				startActivity(intent);
				loginUser = null;
				mMainActivity.finish();
			}
		});

		tv_user.setText(String.format("%s ¡ª¡ª %s", mMainActivity.loginUser.getUserName(), mMainActivity.loginUser.getName()));
		tv_phone.setText(mMainActivity.loginUser.getPhone());
	}

	@OnClick(R.id.rl_mine_info)
	private void clickMineInfo(View view) {
		Intent intent = new Intent();
		intent.setClass(mMainActivity, RegisterActivity.class);
		intent.putExtra("userId", String.valueOf(mMainActivity.loginUser.getId()));
		startActivity(intent);
		// mMainActivity.finish();
	}

	@OnClick(R.id.rl_update)
	private void clickUpdate(View view) {

		UpdateManager updateManager = new UpdateManager(mMainActivity);
		updateManager.checkUpdate(svrAddress, xmlFile);
	}

	@OnClick(R.id.btn_logout)
	private void clickLogout(View view) {
		Intent intent = new Intent();
		intent.setClass(mMainActivity, LoginActivity.class);
		startActivity(intent);
		loginUser = null;
		mMainActivity.finish();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_SETTING;

	}

}
