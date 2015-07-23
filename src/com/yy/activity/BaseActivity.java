package com.yy.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.lidroid.xutils.DbUtils;
import com.yy.house.R;
import com.yy.util.DateUtil;
import com.yy.util.timerpicker.JudgeDate;
import com.yy.util.timerpicker.ScreenInfo;
import com.yy.util.timerpicker.WheelMain;
import com.yy.vo.User;

public abstract class BaseActivity extends Activity {
	protected DbUtils db;
	protected String dateFormatString = "yyyy-MM-dd";
	protected DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
	private WheelMain wheelMain;
	public static User loginUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		db = DbUtils.create(this);
		db.configAllowTransaction(true);
		db.configDebug(true);
		setContentView();
		initialize();
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

	}

	protected abstract void setContentView();

	protected abstract void initialize();

	public void BackActivity(String fragFlag) {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		intent.putExtra("fragFlag", fragFlag);
		startActivity(intent);
	}

	public boolean ValidateInteger(EditText inputTxt) {
		boolean inputValidate = true;
		if (inputTxt.getText().toString().length() == 0) {
			inputTxt.setError("不能为空");
			inputValidate = false;
		} else {
			try {
				Integer.parseInt(inputTxt.getText().toString());

			} catch (NumberFormatException e) {
				inputValidate = false;
				inputTxt.setError("请以正确数字形式输入");
			}
		}
		return inputValidate;
	}

	public boolean ValidateFloat(EditText inputTxt) {
		boolean inputValidate = true;
		if (inputTxt.getText().toString().length() == 0) {
			inputTxt.setError("不能为空");
			inputValidate = false;
		} else {
			try {
				Float.parseFloat(inputTxt.getText().toString());

			} catch (NumberFormatException e) {
				inputValidate = false;
				inputTxt.setError("请以正确数字形式输入");
			}
		}
		return inputValidate;
	}

	protected Date GetBtnDate(Button btn_date) {
		String time = btn_date.getText().toString();
		return DateUtil.getDate(time, dateFormatString);
	}

	protected String format(Date date) {
		String str = "";
		str = dateFormat.format(date);
		return str;
	}

	public void ShowTimePicker(final Button btn_date) {
		ShowTimePicker(btn_date, null);
	}

	public void ShowTimePicker(final Button btn_date, final Button btn_2date) {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View timepickerview = inflater.inflate(R.layout.timepicker, null);
		ScreenInfo screenInfo = new ScreenInfo(this);
		wheelMain = new WheelMain(timepickerview);
		wheelMain.screenheight = screenInfo.getHeight();
		String time = btn_date.getText().toString();
		Calendar calendar = Calendar.getInstance();
		if (JudgeDate.isDate(time, "yyyy-MM-dd")) {
			try {
				calendar.setTime(dateFormat.parse(time));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		wheelMain.initDateTimePicker(year, month, day);
		new AlertDialog.Builder(this).setTitle("选择时间").setView(timepickerview).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					Date selectDate = dateFormat.parse(wheelMain.getTime());
					btn_date.setText(format(dateFormat.parse(wheelMain.getTime())));
					if (btn_2date != null) {
						btn_2date.setText(format(DateUtil.getDateAdd(selectDate, 365)));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		}).show();
	}
}
