package com.yy.activity;

import java.util.List;

import android.content.Intent;
import android.text.InputType;
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
import com.yy.vo.Contract;
import com.yy.vo.House;

public class HouseActivity extends BaseActivity {

	@ViewInject(R.id.title_bar)
	protected TitleBarView mTitleBarView;
	@ViewInject(value = R.id.title_btn_right, parentId = R.id.title_bar)
	protected Button btnRight;

	@ViewInject(R.id.sp_area)
	private Spinner sp_area;

	@ViewInject(R.id.et_building_num)
	private EditText et_building_num;

	@ViewInject(R.id.et_room_num1)
	private EditText et_room_num1;

	@ViewInject(R.id.et_room_num2)
	private EditText et_room_num2;

	@ViewInject(R.id.et_room_num3)
	private EditText et_room_num3;

	@ViewInject(R.id.et_area)
	private EditText et_area;

	@ViewInject(R.id.et_house_des)
	private EditText et_house_des;

	@ViewInject(R.id.btn_add)
	private Button btn_add;

	@ViewInject(R.id.btn_delete)
	private Button btn_delete;

	public boolean isAdd = true, isDeleted = false;
	private int dataID;
	private House dataItem = new House();

	@Override
	protected void setContentView() {
		setContentView(R.layout.house);
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
		House itemData = (House) intent.getSerializableExtra("itemData");
		if (itemData != null) {
			dataID = itemData.getId();

			String area = itemData.getArea();
			for (int i = 0; i < areas.length; i++) {
				if (area.equals(adapter.getItem(i))) {
					sp_area.setSelection(i, true);// 默认选中项
					break;
				}
			}

			et_building_num.setText(itemData.getHouseNum());
			et_room_num1.setText(String.valueOf(itemData.getRoomNum1()));
			et_room_num2.setText(String.valueOf(itemData.getRoomNum2()));
			et_room_num3.setText(String.valueOf(itemData.getRoomNum3()));
			et_area.setText(String.valueOf(itemData.getBuildingArea()));

			et_house_des.setText(itemData.getDescription());
			mTitleBarView.setTitleText(R.string.updatehouse);
			btn_add.setText(R.string.updatehouse);
			btn_delete.setText(R.string.deletehouse);
			isAdd = false;
		} else {
			mTitleBarView.setTitleText(R.string.addhouse);
			btn_add.setText(R.string.addhouse);
			btn_delete.setText(R.string.backlist);
		}

		et_area.setInputType(InputType.TYPE_CLASS_NUMBER);
		et_room_num1.setInputType(InputType.TYPE_CLASS_NUMBER);
		et_room_num2.setInputType(InputType.TYPE_CLASS_NUMBER);
		et_room_num3.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	@OnClick(R.id.btn_add)
	public void addHouse(View view) {
		GatherData();
		try {
			// db.saveOrUpdate(house);
			if (isAdd) {
				dataItem.setIsAvailable(true);
				db.saveBindingId(dataItem);
				Toast.makeText(this, "房屋信息添加成功", Toast.LENGTH_LONG).show();
			} else {
				dataItem.setId(dataID);
				db.update(dataItem);
				Toast.makeText(this, "房屋信息修改成功", Toast.LENGTH_LONG).show();
			}
			BackActivity(Constant.FRAGMENT_FLAG_HOUSE);
			finish();
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnClick(R.id.btn_delete)
	public void deleteHouse(View view) {
		if (isAdd) {
			finish();
		} else {
			try {
				GatherData();
				List<Contract> contractList = db.findAll(Selector.from(Contract.class).where("House", "=", dataItem.getArea())
						.and("IsDeleted", "=", false));
				if (contractList.size() > 0) {
					Toast.makeText(this, "此房屋存在进行汇总合同，不能删除！", Toast.LENGTH_LONG).show();
					return;
				} else {
					dataItem.setIsDeleted(true);
					dataItem.setId(dataID);
					db.update(dataItem);
					Toast.makeText(this, "房屋信息删除成功", Toast.LENGTH_LONG).show();
					BackActivity(Constant.FRAGMENT_FLAG_HOUSE);
				}
			} catch (DbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void GatherData() {
		dataItem.setArea(sp_area.getSelectedItem().toString());
		dataItem.setHouseNum(et_building_num.getText().toString());
		if (ValidateInteger(et_room_num1)) {
			dataItem.setRoomNum1(Integer.valueOf(et_room_num1.getText().toString()));
		} else {
			return;
		}

		if (ValidateInteger(et_room_num2)) {
			dataItem.setRoomNum2(Integer.valueOf(et_room_num2.getText().toString()));
		} else {
			return;
		}

		if (ValidateInteger(et_room_num3)) {
			dataItem.setRoomNum3(Integer.valueOf(et_room_num3.getText().toString()));
		} else {
			return;
		}

		if (ValidateFloat(et_area)) {
			dataItem.setBuildingArea(Float.valueOf(et_area.getText().toString()));
		} else {
			return;
		}

		dataItem.setDescription(et_house_des.getText().toString());
	}

}
