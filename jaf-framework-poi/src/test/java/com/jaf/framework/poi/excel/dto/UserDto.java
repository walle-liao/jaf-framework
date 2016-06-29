package com.jaf.framework.poi.excel.dto;


/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年6月29日
 * @since 1.0
 */
public class UserDto {

	private String name;

	private String idCard;

	private int age;
	
	public UserDto() {
	}
	
	public UserDto(String name, String idCard, int age) {
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
