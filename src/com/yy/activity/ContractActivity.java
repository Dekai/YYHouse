package com.yy.activity;

import java.util.Date;

import android.content.Intent;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yy.constant.Constant;
import com.yy.house.R;
import com.yy.ui.TitleBarView;
import com.yy.util.DateUtil;
import com.yy.vo.Contract;
import com.yy.vo.House;

public class ContractActivity extends BaseActivity {

	@ViewInject(R.id.title_bar)
	protected TitleBarView mTitleBarView;
	@ViewInject(value = R.id.title_btn_right, parentId = R.id.title_bar)
	protected Button btnRight;

	@ViewInject(R.id.sp_area)
	private Spinner sp_area;

	@ViewInject(R.id.btn_start_date)
	private Button btn_start_date;

	@ViewInject(R.id.btn_end_date)
	private Button btn_end_date;

	@ViewInject(R.id.et_rentamount)
	private EditText et_rentamount;

	@ViewInject(R.id.et_intervel)
	private EditText et_intervel;

	@ViewInject(R.id.et_desposit)
	private EditText et_desposit;

	@ViewInject(R.id.et_renter)
	private EditText et_renter;

	@ViewInject(R.id.et_phone)
	private EditText et_phone;

	@ViewInject(R.id.et_renterId)
	private EditText et_renterId;

	@ViewInject(R.id.et_contract_des)
	private EditText et_contract_des;

	@ViewInject(R.id.btn_add)
	private Button btn_add;

	@ViewInject(R.id.btn_delete)
	private Button btn_delete;

	public boolean isAdd = true, isDeleted = false;
	private int dataID;
	private Contract dataItem = new Contract();

	@Override
	protected void setContentView() {
		setContentView(R.layout.contract);
		ViewUtils.inject(this);
	}

	@Override
	protected void initialize() {
		mTitleBarView.setBtnLeft(R.drawable.back, R.string.back);
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 建立数据源
		String[] areas = getResources().getStringArray(R.array.area);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, areas);
		adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		sp_area.setAdapter(adapter);

		Intent intent = getIntent();
		Contract itemData = (Contract) intent.getSerializableExtra("itemData");
		if (itemData != null) {
			dataID = itemData.getId();

			String area = itemData.getHouse();
			for (int i = 0; i < areas.length; i++) {
				if (area.equals(adapter.getItem(i))) {
					sp_area.setSelection(i, true);// 默认选中项
					break;
				}
			}

			btn_start_date.setText(dateFormat.format(itemData.getStartDate()));
			btn_end_date.setText(dateFormat.format(itemData.getEndDate()));
			et_rentamount.setText(String.valueOf(itemData.getRentAmount()));
			et_intervel.setText(String.valueOf(itemData.getIntervel()));
			et_desposit.setText(String.valueOf(itemData.getDeposit()));
			et_renter.setText(itemData.getRenter());
			et_phone.setText(itemData.getPhone());
			et_renterId.setText(itemData.getRenterID());
			et_contract_des.setText(itemData.getDescription());

			mTitleBarView.setTitleText(R.string.updatecontract);
			btn_add.setText(R.string.updatecontract);
			btn_delete.setText(R.string.deletecontract);
			isAdd = false;
		} else {
			btn_start_date.setText(DateUtil.getCurrentDateString());
			btn_end_date.setText(DateUtil.getFormatCurrentAdd(365, dateFormatString));
			mTitleBarView.setTitleText(R.string.addcontract);
			btn_add.setText(R.string.addcontract);
			btn_delete.setText(R.string.backlist);
		}

		et_rentamount.setInputType(InputType.TYPE_CLASS_NUMBER);
		et_intervel.setInputType(InputType.TYPE_CLASS_NUMBER);
		et_desposit.setInputType(InputType.TYPE_CLASS_NUMBER);
		et_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
		et_renterId.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	@OnClick(R.id.btn_add)
	public void addHouse(View view) {
		GatherData();
		try {
			if (isAdd) {
				House mHouse = db.findFirst(Selector.from(House.class).where("Area", "=", dataItem.getHouse()).and("IsDeleted", "=", false));
				if (mHouse == null || mHouse.isIsAvailable()) {
					mHouse.setIsAvailable(false);
					db.update(mHouse);

					dataItem.setIsActivate(true);
					db.saveBindingId(dataItem);
					Toast.makeText(this, "合同信息添加成功", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(this, "此房屋在租状态，请核实！", Toast.LENGTH_LONG).show();
					return;
				}
			} else {
				dataItem.setId(dataID);
				db.update(dataItem);
				Toast.makeText(this, "合同信息修改成功", Toast.LENGTH_LONG).show();
			}
			BackActivity(Constant.FRAGMENT_FLAG_CONTRACT);
			finish();
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnClick(R.id.btn_delete)
	public void deleteContract(View view) {
		if (isAdd) {
			finish();
		} else {
			try {
				GatherData();
				House mHouse = db.findFirst(Selector.from(House.class).where("Area", "=", dataItem.getHouse()).and("IsDeleted", "=", false));
				if (mHouse != null) {
					mHouse.setIsAvailable(true);
					db.update(mHouse);
				}
				dataItem.setId(dataID);
				dataItem.setIsActivate(false);
				db.update(dataItem);
				Toast.makeText(this, "合同信息结束成功", Toast.LENGTH_LONG).show();
				BackActivity(Constant.FRAGMENT_FLAG_CONTRACT);
			} catch (DbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@OnClick(R.id.btn_start_date)
	public void showDate(View view) {
		ShowTimePicker((Button) view, btn_end_date);
	}

	@OnClick(R.id.btn_end_date)
	public void showEndDate(View view) {
		ShowTimePicker((Button) view);
	}

	private void GatherData() {
		dataItem = new Contract();
		dataItem.setHouse(sp_area.getSelectedItem().toString());
		Date startDate = GetBtnDate(btn_start_date);
		dataItem.setStartDate(startDate);
		dataItem.setNextChargeDate(DateUtil.getDateAdd(startDate, 90));
		dataItem.setEndDate(GetBtnDate(btn_end_date));
		if (ValidateInteger(et_rentamount)) {
			dataItem.setRentAmount(Integer.valueOf(et_rentamount.getText().toString()));
		} else {
			return;
		}

		if (ValidateInteger(et_intervel)) {
			dataItem.setIntervel(Integer.valueOf(et_intervel.getText().toString()));
		} else {
			return;
		}

		if (ValidateInteger(et_desposit)) {
			dataItem.setDeposit(Integer.valueOf(et_desposit.getText().toString()));
		} else {
			return;
		}

		dataItem.setRenter(et_renter.getText().toString());
		dataItem.setRenterID(et_renterId.getText().toString());
		dataItem.setPhone(et_phone.getText().toString());
		dataItem.setDescription(et_contract_des.getText().toString());
	}
}
