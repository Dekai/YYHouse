package com.yy.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.yy.constant.Constant;
import com.yy.fragment.adapter.RentalAdapter;
import com.yy.house.R;
import com.yy.ui.TitleBarView;
import com.yy.vo.Contract;
import com.yy.vo.Rental;

public class RentalListActivity extends BaseActivity {

	@ViewInject(R.id.title_bar)
	protected TitleBarView mTitleBarView;
	@ViewInject(value = R.id.title_btn_right, parentId = R.id.title_bar)
	protected Button btnRight;
	@ViewInject(R.id.lv_datalist)
	private PullToRefreshListView mListView;
	private RentalAdapter mAdapter;
	private List<Rental> mDataList = new ArrayList<Rental>();
	private Contract itemData;
	private boolean isProcessing;

	@Override
	protected void setContentView() {
		setContentView(R.layout.contract_layout);
		ViewUtils.inject(this);
	}

	@Override
	protected void initialize() {
		Intent intent = getIntent();
		itemData = (Contract) intent.getSerializableExtra("itemData");
		isProcessing = intent.getBooleanExtra("isProcessing", true);

		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.VISIBLE);
		mTitleBarView.setBtnLeft(R.drawable.back, R.string.back);
		mTitleBarView.setTitleText(R.string.rentrecords);

		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				BackActivity(Constant.FRAGMENT_FLAG_RENTAL);
				finish();
			}
		});
		if (isProcessing) {
			mTitleBarView.setTvRightVisibility(View.GONE);
			mTitleBarView.setBtnRightText(R.string.add);

			mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(RentalListActivity.this, RentalActivity.class);
					intent.putExtra("contractId", itemData.getId());
					intent.putExtra("house", itemData.getHouse());
					intent.putExtra("nextChargeDate", format(itemData.getNextChargeDate()));
					intent.putExtra("intervel", itemData.getIntervel());
					intent.putExtra("rentAmount", itemData.getRentAmount());
					intent.putExtra("isProcessing", isProcessing);
					startActivity(intent);
					finish();
				}
			});
		}

		try {
			mDataList = db.findAll(Selector.from(Rental.class).where("IsDeleted", "=", false));
			if (mDataList != null) {
				mAdapter = new RentalAdapter(mDataList, RentalListActivity.this);
				mAdapter.notifyDataSetChanged();
				mListView.setAdapter(mAdapter);
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnItemClick(R.id.lv_datalist)
	public void onLVItemClick(AdapterView<?> parent, View view, int position, long id) {
		Rental itemData = mDataList.get(position - 1);

		final Intent intent = new Intent();
		intent.setClass(this, RentalActivity.class);
		intent.putExtra("itemData", itemData);
		startActivity(intent);
	}

}
