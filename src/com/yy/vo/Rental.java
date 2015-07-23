package com.yy.vo;

import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "Rental")
// 建议加上注解， 混淆后表名不受影响
public class Rental extends EntityBase {

	@Column(column = "ContractId")
	private int ContractId;

	@Column(column = "House")
	public String House;

	@Column(column = "ChargeDate")
	private Date ChargeDate;

	@Column(column = "RentAmount")
	private int RentAmount;

	@Column(column = "Receiver")
	private String Receiver;
	
	@Column(column = "Description")
	public String Description;

	@Column(column = "IsDeleted")
	private boolean IsDeleted;

	public int getContractId() {
		return ContractId;
	}

	public void setContractId(int contractId) {
		ContractId = contractId;
	}

	public String getHouse() {
		return House;
	}

	public void setHouse(String house) {
		House = house;
	}

	public Date getChargeDate() {
		return ChargeDate;
	}

	public void setChargeDate(Date chargeDate) {
		ChargeDate = chargeDate;
	}

	public int getRentAmount() {
		return RentAmount;
	}

	public void setRentAmount(int rentAmount) {
		RentAmount = rentAmount;
	}


	public String getReceiver() {
		return Receiver;
	}

	public void setReceiver(String receiver) {
		Receiver = receiver;
	}

	public boolean isIsDeleted() {
		return IsDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		IsDeleted = isDeleted;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

}
