package com.yy.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yy.house.R;
import com.yy.ui.TitleBarView;
import com.yy.vo.User;

public class RegisterActivity extends BaseActivity {

	@ViewInject(R.id.title_bar)
	protected TitleBarView mTitleBarView;
	@ViewInject(value = R.id.title_btn_right, parentId = R.id.title_bar)
	protected Button btnRight;

	@ViewInject(R.id.et_username)
	private EditText et_username;

	@ViewInject(R.id.et_password)
	private EditText et_password;

	@ViewInject(R.id.et_password2)
	private EditText et_password2;

	@ViewInject(R.id.et_name)
	private EditText et_name;

	@ViewInject(R.id.et_phone)
	private EditText et_phone;

	@ViewInject(R.id.btn_add)
	private Button btn_add;

	@ViewInject(R.id.btn_delete)
	private Button btn_delete;
	private User dataItem = new User();
	private String dataID;
	private boolean isAdd = true;

	@Override
	protected void setContentView() {
		setContentView(R.layout.register);
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

		Intent intent = getIntent();
		dataID = intent.getStringExtra("userId");
		if (dataID != null) {
			mTitleBarView.setTitleText(R.string.updateuser);
			et_username.setText(loginUser.getUserName());
			et_name.setText(loginUser.getName());
			et_phone.setText(loginUser.getPhone());
			et_username.setEnabled(false);
			btn_add.setText(R.string.update);
			isAdd = false;
		} else {
			mTitleBarView.setTitleText(R.string.adduser);
			btn_add.setText(R.string.registerbtn);
		}
	}

	@OnClick(R.id.btn_add)
	public void addUser(View view) {
		GatherData();
		Intent intent = new Intent();
		try {
			if (isAdd) {
				if (dataItem.getPassword().equals(et_password2.getText().toString())) {
					db.saveBindingId(dataItem);
					Toast.makeText(this, "用户注册成功", Toast.LENGTH_LONG).show();
					intent.setClass(RegisterActivity.this, LoginActivity.class);
					finish();
				} else {
					Toast.makeText(this, "密码不一致，请重新输入", Toast.LENGTH_LONG).show();
					return;
				}
			} else {
				dataItem.setId(Integer.valueOf(dataID));
				db.update(dataItem);
				loginUser = dataItem;
				Toast.makeText(this, "用户修改成功", Toast.LENGTH_LONG).show();
				finish();
			}

		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnClick(R.id.btn_delete)
	public void deleteUser(View view) {
		finish();
	}

	private void GatherData() {
		dataItem.setUserName(et_username.getText().toString());
		dataItem.setPassword(et_password.getText().toString());
		dataItem.setName(et_name.getText().toString());
		dataItem.setPhone(et_phone.getText().toString());
	}

}
