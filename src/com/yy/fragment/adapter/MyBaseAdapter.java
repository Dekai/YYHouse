package com.yy.fragment.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yy.house.R;
import com.yy.vo.MessageBean;

public class MyBaseAdapter extends BaseAdapter {

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	protected String formatDate(Date date) {

		String str = "";
		if (date != null) {
			SimpleDateFormat ymd = null;
			ymd = new SimpleDateFormat("yyyy-MM-dd");
			str = ymd.format(date);
		}
		return str;
	}

}
