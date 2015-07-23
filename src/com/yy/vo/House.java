package com.yy.vo;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "House")
// 建议加上注解， 混淆后表名不受影响
public class House extends EntityBase {

	@Column(column = "Area")
	private String Area;

	@Column(column = "HouseNum")
	private String HouseNum;

	@Column(column = "RoomNum1")
	private int RoomNum1;

	@Column(column = "RoomNum2")
	private int RoomNum2;

	@Column(column = "RoomNum3")
	private int RoomNum3;

	@Column(column = "BuildingArea")
	private float BuildingArea;

	@Column(column = "RentAmount")
	private int RentAmount;

	@Column(column = "IsAvailable")
	private boolean IsAvailable;
	
	@Column(column = "IsDeleted")
	private boolean IsDeleted;

	
	public boolean isIsAvailable() {
		return IsAvailable;
	}

	public void setIsAvailable(boolean isAvailable) {
		IsAvailable = isAvailable;
	}

	public boolean isIsDeleted() {
		return IsDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		IsDeleted = isDeleted;
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public String getHouseNum() {
		return HouseNum;
	}

	public void setHouseNum(String houseNum) {
		HouseNum = houseNum;
	}

	public int getRoomNum1() {
		return RoomNum1;
	}

	public void setRoomNum1(int roomNum1) {
		this.RoomNum1 = roomNum1;
	}

	public int getRoomNum2() {
		return RoomNum2;
	}

	public void setRoomNum2(int roomNum2) {
		this.RoomNum2 = roomNum2;
	}

	public int getRoomNum3() {
		return RoomNum3;
	}

	public void setRoomNum3(int roomNum3) {
		this.RoomNum3 = roomNum3;
	}

	public float getBuildingArea() {
		return BuildingArea;
	}

	public void setBuildingArea(float buildingArea) {
		BuildingArea = buildingArea;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	@Column(column = "Description")
	private String Description;

	public int getRentAmount() {
		return RentAmount;
	}

	public void setRentAmount(int rentAmount) {
		RentAmount = rentAmount;
	}

}
