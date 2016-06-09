package com.jaf.framework.poi.excel.export.dto;

import com.jaf.framework.poi.excel.ColumnHead;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月11日
 * @since 1.0
 */
public class UserExportDTO {
	
	@ColumnHead(title = "姓名")
	private String name;
	
	@ColumnHead(title = "身份证号")
	private String idNum;
	
	@ColumnHead(title = "分数")
	private int score;
	
	public UserExportDTO(String name, String idNum, int score) {
		this.name = name;
		this.idNum = idNum;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdNum() {
		return idNum;
	}
	
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
}
