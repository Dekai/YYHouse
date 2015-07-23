package com.yy.activity;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.SqlInfo;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yy.house.R;
import com.yy.ui.TitleBarView;
import com.yy.vo.Rental;

public class RentalActivity extends BaseActivity {

	@ViewInject(R.id.title_bar)
	protected TitleBarView mTitleBarView;
	@ViewInject(value = R.id.title_btn_right, parentId = R.id.title_bar)
	protected Button btnRight;

	@ViewInject(R.id.tv_house)
	private TextView tv_house;

	@ViewInject(R.id.et_rent_amount)
	private EditText et_rent_amount;

	@ViewInject(R.id.tv_rent_promot)
	private TextView tv_rent_promot;

	@ViewInject(R.id.btn_date)
	private Button btn_date;

	@ViewInject(R.id.et_operator)
	private EditText et_operator;

	@ViewInject(R.id.et_des)
	private EditText et_des;

	@ViewInject(R.id.btn_add)
	private Button btn_add;

	@ViewInject(R.id.btn_delete)
	private Button btn_delete;

	@ViewInject(R.id.ll_btns)
	private LinearLayout ll_btns;

	public boolean isAdd = true, isDeleted = false;
	private int dataID, contractId;
	private Rental dataItem = new Rental();
	private boolean isProcessing;
	private String nextChargeDate;

	@Override
	protected void setContentView() {
		setContentView(R.layout.rental);
		ViewUtils.inject(this);
	}

	@Override
	protected void initialize() {
		mTitleBarView.setBtnLeft(R.drawable.back, R.string.back);
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(RentalActivity.this, RentalListActivity.class);
				startActivity(intent);
				finish();
			}
		});

		Intent intent = getIntent();
		Rental itemData = (Rental) intent.getSerializableExtra("itemData");
		isProcessing = intent.getBooleanExtra("isProcessing", true);

		if (isProcessing)
			ll_btns.setVisibility(View.VISIBLE);

		String house;
		contractId = intent.getIntExtra("contractId", 1);
		nextChargeDate = intent.getStringExtra("nextChargeDate");
		int intervel;
		int rentAmount;
		if (itemData != null) {
			dataID = itemData.getId();

			tv_house.setText(itemData.getHouse());
			et_rent_amount.setText(String.valueOf(itemData.getRentAmount()));
			btn_date.setText(format(itemData.getChargeDate()));
			et_operator.setText(itemData.getReceiver());
			et_des.setText(itemData.getDescription());

			mTitleBarView.setTitleText(R.string.updaterental);
			btn_add.setText(R.string.updaterental);
			btn_delete.setText(R.string.deleterental);
			isAdd = false;
		} else {
			mTitleBarView.setTitleText(R.string.addrental);
			btn_date.setText(nextChargeDate);
			house = intent.getStringExtra("house");
			intervel = intent.getIntExtra("intervel", 1);
			rentAmount = intent.getIntExtra("rentAmount", 1);
			et_rent_amount.setText(String.valueOf(intervel * rentAmount));
			tv_rent_promot.setText(String.format("(%s元,%s个月)", rentAmount, intervel));
			tv_house.setText(house);
			et_operator.setText(loginUser.getName());
			btn_add.setText(R.string.addrental);
			btn_delete.setText(R.string.backlist);
		}

		et_rent_amount.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	@OnClick(R.id.btn_add)
	public void addRental(View view) {
		GatherData();
		try {
			// db.saveOrUpdate(house);
			if (isAdd) {
				SqlInfo updateHouse = new SqlInfo("update house set rentamount = rentamount + ? where area = ?", dataItem.getRentAmount(),
						dataItem.getHouse());
				db.execNonQuery(updateHouse);
				
				db.saveBindingId(dataItem);
				Toast.makeText(this, "房租添加成功", Toast.LENGTH_LONG).show();
			} else {
				dataItem.setId(dataID);
				db.update(dataItem);
				Toast.makeText(this, "房租修改成功", Toast.LENGTH_LONG).show();
			}
			Intent intent = new Intent();
			intent.setClass(RentalActivity.this, RentalListActivity.class);
			startActivity(intent);
			finish();
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnClick(R.id.btn_delete)
	public void deleteRental(View view) {
		if (isAdd) {
			finish();
		} else {
			try {
				GatherData();
				dataItem.setId(dataID);
				dataItem.setIsDeleted(true);
				db.update(dataItem);
				Toast.makeText(this, "房租删除成功", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.setClass(RentalActivity.this, RentalListActivity.class);
				startActivity(intent);
				finish();
			} catch (DbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@OnClick(R.id.btn_date)
	public void showDate(View view) {
		ShowTimePicker((Button) view);
	}

	private void GatherData() {
		dataItem = new Rental();
		dataItem.setContractId(contractId);
		dataItem.setHouse(tv_house.getText().toString());
		if (ValidateInteger(et_rent_amount)) {
			dataItem.setRentAmount(Integer.valueOf(et_rent_amount.getText().toString()));
		} else {
			return;
		}

		dataItem.setChargeDate(GetBtnDate(btn_date));
		dataItem.setDescription(et_des.getText().toString());

	}

}
