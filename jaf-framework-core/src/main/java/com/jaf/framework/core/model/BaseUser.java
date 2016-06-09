package com.jaf.framework.core.model;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年9月5日
 * @since 1.0
 */
public abstract class BaseUser extends CommonEntity {
	
	private static final long serialVersionUID = 8940403010876413545L;
	
	// 用户名
	private String userName;
	
	// 密码
	private String passWord;
	
	
	public String getUserName() {
		return userName;
	}
	
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public String getPassWord() {
		return passWord;
	}
	
	
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
}
