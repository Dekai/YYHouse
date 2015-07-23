package com.yy.fragment.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yy.house.R;
import com.yy.vo.House;

public class HouseAdapter extends MyBaseAdapter {
	private List<House> mList = null;
	private Context mContext;
	private LayoutInflater mInflater;

	public HouseAdapter(List<House> listMsgBean, Context context) {
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
			convertView = mInflater.inflate(R.layout.house_item_layout, null);
			holder = new ViewHolder();
			holder.tv_area = (TextView) convertView.findViewById(R.id.tv_area);
			holder.tv_buildingnum = (TextView) convertView.findViewById(R.id.tv_buildingnum);
			holder.tv_roomnum = (TextView) convertView.findViewById(R.id.tv_roomnum);
			holder.tv_buildingarea = (TextView) convertView.findViewById(R.id.tv_buildingarea);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final House dataInfo = (House) getItem(position);
		holder.tv_area.setText(dataInfo.getArea() + " - " + dataInfo.getHouseNum());
		if (dataInfo.isIsAvailable()) {
			holder.tv_buildingnum.setText(R.string.houseavailable);
		} else {
			holder.tv_buildingnum.setText(R.string.houserent);
		}
		holder.tv_roomnum.setText(String.format("%s “%sÃ¸%sŒ¿", dataInfo.getRoomNum1(), dataInfo.getRoomNum2(), dataInfo.getRoomNum3()));
		holder.tv_buildingarea.setText(String.format("√Êª˝:%s∆Ω", dataInfo.getBuildingArea()));
		return convertView;
	}

	class ViewHolder {
		TextView tv_area;
		TextView tv_buildingnum;
		TextView tv_roomnum;
		TextView tv_buildingarea;
	}

}
