package com.yy.vo;

import java.io.Serializable;

public abstract class EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;
	// @Id // �������û��������Ϊid��_id��ʱ����ҪΪ������Ӵ�ע��
	// @NoAutoIncrement // int,long���͵�idĬ������������ʹ������ʱ��Ӵ�ע��
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
