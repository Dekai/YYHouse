package com.yy.fragment.adapter;

import java.util.List;
import java.util.zip.DataFormatException;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yy.house.R;
import com.yy.vo.Contract;
import com.yy.vo.House;

public class ContractAdapter extends MyBaseAdapter {
	private List<Contract> mList = null;
	private Context mContext;
	private LayoutInflater mInflater;

	public ContractAdapter(List<Contract> listMsgBean, Context context) {
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
			convertView = mInflater.inflate(R.layout.contract_item_layout, null);
			holder = new ViewHolder();
			holder.tv_area = (TextView) convertView.findViewById(R.id.tv_area);
			holder.tv_rentAmount = (TextView) convertView.findViewById(R.id.tv_rentAmount);
			holder.tv_intervel = (TextView) convertView.findViewById(R.id.tv_intervel);
			holder.tv_period = (TextView) convertView.findViewById(R.id.tv_period);
			holder.tv_renter = (TextView) convertView.findViewById(R.id.tv_renter);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Contract dataInfo = (Contract) getItem(position);
		holder.tv_area.setText(dataInfo.getHouse());
		holder.tv_rentAmount.setText("×â½ð:" + String.valueOf(dataInfo.getRentAmount()));
		holder.tv_intervel.setText(String.format("½»%sÑº%s -- %s", dataInfo.getIntervel(), dataInfo.getDeposit(), formatDate(dataInfo.getNextChargeDate())));
		holder.tv_period.setText(String.format("%s - %s", formatDate(dataInfo.getStartDate()), formatDate(dataInfo.getEndDate())));
		holder.tv_renter.setText(dataInfo.getRenter());
		return convertView;
	}

	class ViewHolder {
		TextView tv_area;
		TextView tv_rentAmount;
		TextView tv_intervel;
		TextView tv_period;
		TextView tv_renter;
	}

}
