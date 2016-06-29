package com.jaf.framework.poi.excel.dto;

import com.jaf.framework.poi.excel.ColumnHead;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年6月29日
 * @since 1.0
 */
public class UserAnnotationDto {

	@ColumnHead(title = "姓名")
	private String name;

	@ColumnHead(title = "身份证号", width = 25)
	private String idCard;

	@ColumnHead(title = "年龄")
	private int age;
	
	public UserAnnotationDto() {
	}
	
	public UserAnnotationDto(String name, String idCard, int age) {
		this.name = name;
		this.idCard = idCard;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
