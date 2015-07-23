package com.yy.fragment.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yy.house.R;
import com.yy.vo.Contract;
import com.yy.vo.Rental;

public class RentalAdapter extends MyBaseAdapter {
	private List<Rental> mList = null;
	private Context mContext;
	private LayoutInflater mInflater;

	public RentalAdapter(List<Rental> listMsgBean, Context context) {
		mList = listMsgBean;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	ViewHolder holder = null;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.rental_item_layout, null);
			holder = new ViewHolder();
			holder.tv_area = (TextView) convertView.findViewById(R.id.tv_area);
			holder.tv_rentAmount = (TextView) convertView.findViewById(R.id.tv_rentAmount);
			holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
			holder.tv_receiver = (TextView) convertView.findViewById(R.id.tv_receiver);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Rental dataInfo = (Rental) getItem(position);
		holder.tv_area.setText(dataInfo.getHouse());
		holder.tv_rentAmount.setText(String.valueOf(dataInfo.getRentAmount()));
		holder.tv_date.setText(formatDate(dataInfo.getChargeDate()));
		holder.tv_receiver.setText(dataInfo.getReceiver());
		return convertView;
	}

	class ViewHolder {
		TextView tv_area;
		TextView tv_rentAmount;
		TextView tv_date;
		TextView tv_receiver;
	}

}
