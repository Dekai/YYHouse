package com.yy.activity;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yy.house.R;
import com.yy.vo.User;

public class LoginActivity extends BaseActivity {

	@ViewInject(R.id.rl_user)
	private RelativeLayout rl_user;
	@ViewInject(R.id.login)
	private Button mLogin;
	@ViewInject(R.id.register)
	private Button register;
	@ViewInject(R.id.account)
	private EditText account;
	@ViewInject(R.id.password)
	private EditText password;

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_login);
		ViewUtils.inject(this);
	}

	@Override
	protected void initialize() {
		Animation anim = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.login_anim);
		anim.setFillAfter(true);
		rl_user.startAnimation(anim);
	}

	@OnClick(R.id.login)
	public void onLogin(View v) {
		String userAccount = account.getText().toString();
		String userPassword = password.getText().toString();
		User user = null;
		try {
			user = db.findFirst(Selector.from(User.class).where("UserName", "=", userAccount).and("Password", "=", userPassword));
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user != null) {
			final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			intent.putExtra("loginUser", user);
			startActivity(intent);
			finish();
		} else {
			Toast.makeText(this, "’À∫≈√‹¬Î≤ª∂‘£¨«Î÷ÿ ‘", Toast.LENGTH_LONG).show();
		}

	}

	@OnClick(R.id.register)
	public void onRegister(View v) {
		Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);
	}

}
