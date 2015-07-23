package com.yy.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.yy.constant.Constant;
import com.yy.fragment.adapter.ContractAdapter;
import com.yy.house.R;
import com.yy.ui.TitleBarView;
import com.yy.vo.Contract;
import com.yy.vo.House;

public class RentContractActivity extends BaseActivity {

	@ViewInject(R.id.title_bar)
	protected TitleBarView mTitleBarView;
	@ViewInject(value = R.id.title_btn_right, parentId = R.id.title_bar)
	protected Button btnRight;
	@ViewInject(R.id.lv_datalist)
	private PullToRefreshListView mListView;
	private ContractAdapter mAdapter;
	private List<Contract> mDataList = new ArrayList<Contract>();
	private House itemData;
	private boolean isProcessing = true;

	@Override
	protected void setContentView() {
		setContentView(R.layout.contract_layout);
		ViewUtils.inject(this);
	}

	@Override
	protected void initialize() {
		Intent intent = getIntent();
		itemData = (House) intent.getSerializableExtra("itemData");

		mTitleBarView.setCommonTitle(View.VISIBLE, View.GONE, View.VISIBLE, View.VISIBLE);
		mTitleBarView.setBtnLeft(R.drawable.back, R.string.back);
		mTitleBarView.setCenterBtnLeftText(R.string.processing);
		mTitleBarView.setCenterBtnRightText(R.string.complete);
		
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mTitleBarView.setCenterBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if (mTitleBarView.getBtnCenterLeft().isEnabled()) {
						mTitleBarView.getBtnCenterLeft().setEnabled(false);
						mTitleBarView.getBtnCenterRight().setEnabled(true);
						mDataList = db.findAll(Selector.from(Contract.class).where("IsActivate", "=", true).and("House", "=", itemData.getArea()));
						mAdapter = new ContractAdapter(mDataList, RentContractActivity.this);
						mListView.setAdapter(mAdapter);
						mAdapter.notifyDataSetChanged();
						isProcessing = true;
					}
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		mTitleBarView.setCenterBtnRightOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if (mTitleBarView.getBtnCenterRight().isEnabled()) {
						mTitleBarView.getBtnCenterRight().setEnabled(false);
						mTitleBarView.getBtnCenterLeft().setEnabled(true);
						mDataList = db.findAll(Selector.from(Contract.class).where("IsActivate", "=", false).and("House", "=", itemData.getArea()));
						mAdapter = new ContractAdapter(mDataList, RentContractActivity.this);
						mListView.setAdapter(mAdapter);
						mAdapter.notifyDataSetChanged();
						isProcessing = false;
					}
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		mTitleBarView.getBtnCenterLeft().setEnabled(false);
		mTitleBarView.getBtnCenterRight().setEnabled(true);

		try {
			mDataList = db.findAll(Selector.from(Contract.class).where("IsActivate", "=", true).and("House", "=", itemData.getArea()));
			mAdapter = new ContractAdapter(mDataList, this);
			mAdapter.notifyDataSetChanged();
			mListView.setAdapter(mAdapter);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnItemClick(R.id.lv_datalist)
	public void onLVItemClick(AdapterView<?> parent, View view, int position, long id) {
		Contract itemData = mDataList.get(position - 1);

		final Intent intent = new Intent();
		intent.setClass(this, RentalListActivity.class);
		intent.putExtra("itemData", itemData);
		intent.putExtra("isProcessing", isProcessing);
		startActivity(intent);
	}

}
