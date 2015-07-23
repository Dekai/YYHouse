package com.yy.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.yy.activity.ContractActivity;
import com.yy.activity.MainActivity;
import com.yy.activity.RentContractActivity;
import com.yy.constant.Constant;
import com.yy.fragment.adapter.RentHouseAdapter;
import com.yy.house.R;
import com.yy.ui.TitleBarView;
import com.yy.vo.Contract;
import com.yy.vo.House;

public class RentalFragment extends BaseFragment {

	private static final String TAG = "RentalFragment";
	private MainActivity mMainActivity;
	private RentHouseAdapter mAdapter;
	private List<House> mDataList = new ArrayList<House>();

	@ViewInject(R.id.lv_datalist)
	private PullToRefreshListView mListView;
	@ViewInject(R.id.title_bar)
	protected TitleBarView mTitleBarView;
	@ViewInject(value = R.id.title_btn_right, parentId = R.id.title_bar)
	protected Button btnRight;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		fragmentLayout = inflater.inflate(R.layout.renthouse_layout, container, false);

		mMainActivity = (MainActivity) getActivity();
		mFragmentManager = getActivity().getFragmentManager();
		ViewUtils.inject(this, fragmentLayout);
		initialize();
		return fragmentLayout;
	}

	protected void initialize() {
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.VISIBLE);
		mTitleBarView.setTitleText(R.string.renthouse);
		mTitleBarView.setCenterBtnLeftText(R.string.processing);
		mTitleBarView.setCenterBtnRightText(R.string.complete);
		
		if (mDataList != null) {
			mAdapter = new RentHouseAdapter(mDataList, mMainActivity);
			mListView.setAdapter(mAdapter);
		}
	}

	@OnItemClick(R.id.lv_datalist)
	public void onLVItemClick(AdapterView<?> parent, View view, int position, long id) {
		House itemData = mDataList.get(position - 1);

		final Intent intent = new Intent();
		intent.setClass(mMainActivity, RentContractActivity.class);
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

		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_RENTAL;
	}

}
