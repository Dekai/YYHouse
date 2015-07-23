package com.yy.vo;

import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "Contract")
// 建议加上注解， 混淆后表名不受影响
public class Contract extends EntityBase {

	@Column(column = "House")
	public String House;

	@Column(column = "StartDate")
	private Date StartDate;

	@Column(column = "EndDate")
	private Date EndDate;

	@Column(column = "RentAmount")
	private int RentAmount;

	@Column(column = "Intervel")
	private int Intervel;

	@Column(column = "Deposit")
	private int Deposit;

	@Column(column = "Renter")
	public String Renter;

	@Column(column = "Phone")
	public String Phone;

	@Column(column = "RenterID")
	public String RenterID;

	@Column(column = "Description")
	public String Description;
	
	@Column(column = "NextChargeDate")
	public Date NextChargeDate;
	
	@Column(column = "IsActivate")
	private boolean IsActivate;

	@Column(column = "IsDeleted")
	private boolean IsDeleted;
	
	public Date getNextChargeDate() {
		return NextChargeDate;
	}

	public void setNextChargeDate(Date nextChargeDate) {
		NextChargeDate = nextChargeDate;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getHouse() {
		return House;
	}

	public void setHouse(String house) {
		House = house;
	}

	public Date getStartDate() {
		return StartDate;
	}

	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}

	public Date getEndDate() {
		return EndDate;
	}

	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}

	public int getRentAmount() {
		return RentAmount;
	}

	public void setRentAmount(int rentAmount) {
		RentAmount = rentAmount;
	}

	public int getIntervel() {
		return Intervel;
	}

	public void setIntervel(int intervel) {
		Intervel = intervel;
	}

	public int getDeposit() {
		return Deposit;
	}

	public void setDeposit(int deposit) {
		Deposit = deposit;
	}

	public String getRenter() {
		return Renter;
	}

	public void setRenter(String renter) {
		Renter = renter;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getRenterID() {
		return RenterID;
	}

	public void setRenterID(String renterID) {
		RenterID = renterID;
	}

	public boolean isIsActivate() {
		return IsActivate;
	}

	public void setIsActivate(boolean isActivate) {
		IsActivate = isActivate;
	}

	public boolean isIsDeleted() {
		return IsDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		IsDeleted = isDeleted;
	}

	
}
