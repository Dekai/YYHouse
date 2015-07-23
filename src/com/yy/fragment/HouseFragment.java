package com.yy.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.yy.activity.HouseActivity;
import com.yy.activity.MainActivity;
import com.yy.constant.Constant;
import com.yy.fragment.adapter.HouseAdapter;
import com.yy.fragment.adapter.MessageAdapter;
import com.yy.house.R;
import com.yy.ui.TitleBarView;
import com.yy.vo.House;
import com.yy.vo.MessageBean;

public class HouseFragment extends BaseFragment {

	private static final String TAG = "HouseFragment";
	private MainActivity mMainActivity;
	private HouseAdapter mAdapter;
	private List<House> mDataList = new ArrayList<House>();

	@ViewInject(R.id.lv_house)
	private PullToRefreshListView mListView;
	@ViewInject(R.id.title_bar)
	protected TitleBarView mTitleBarView;
	@ViewInject(value = R.id.title_btn_right, parentId = R.id.title_bar)
	protected Button btnRight;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		fragmentLayout = inflater.inflate(R.layout.house_layout, container, false);

		mMainActivity = (MainActivity) getActivity();
		mFragmentManager = getActivity().getFragmentManager();
		ViewUtils.inject(this, fragmentLayout);
		initialize();
		return fragmentLayout;
	}

	protected void initialize() {
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.VISIBLE);
		// mTitleBarView.setBtnLeft(R.drawable.back, R.string.back);
		mTitleBarView.setTitleText(R.string.house);
		mTitleBarView.setBtnRightText(R.string.add);
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mMainActivity, HouseActivity.class);
				startActivity(intent);
			}
		});

		if (mDataList != null) {
			mAdapter = new HouseAdapter(mDataList, mMainActivity);
			mListView.setAdapter(mAdapter);
		}
	}

	@OnItemClick(R.id.lv_house)
	public void onLVItemClick(AdapterView<?> parent, View view, int position, long id) {
		House itemData = mDataList.get(position - 1);

		final Intent intent = new Intent();
		intent.setClass(mMainActivity, HouseActivity.class);
		intent.putExtra("itemData", itemData);
		startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate------");

		try {
			mDataList = db.findAll(Selector.from(House.class).where("IsDeleted", "=", false));
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_HOUSE;
	}

}
