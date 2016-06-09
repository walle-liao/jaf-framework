package com.jaf.framework.core.model;

import java.io.Serializable;
import java.util.Date;

import com.jaf.framework.core.model.enums.CommonEnum;

/**
 * 实体类基类
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年6月27日
 * @since 1.0
 */
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 8636315302594569366L;

	// 主键
	private T id;

	// 创建用户
	private BaseUser createBy;

	// 创建时间
	private Date createTime;

	// 最后一次更新该实体的用户
	private BaseUser lastUpdateBy;

	// 最后一次更新该实体的时间
	private Date lastUpdateTime;

	// 是否删除标识位（软删除字段）
	private CommonEnum isDel = CommonEnum.NOT;
	
	/**
	 * 给实体分配一个唯一的ID值
	 */
	public abstract void generateId();

	/**
	 * 返回实体的ID是否未设置，主要用在判断数据库的 insert or update
	 * 
	 * @return 未设置:true；已设置:false
	 */
	public abstract boolean idIsEmpty();

	public BaseEntity() {
	}

	public BaseEntity(T id) {
		this.id = id;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}

	public BaseUser getCreateBy() {
		return createBy;
	}

	public void setCreateBy(BaseUser createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BaseUser getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(BaseUser lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public CommonEnum getIsDel() {
		return isDel;
	}
	
	public void setIsDel(CommonEnum isDel) {
		this.isDel = isDel;
	}

}
