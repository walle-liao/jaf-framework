package com.jaf.framework.core.web.select;

/**
 * 对应 HTML 页面上的 select option 对象
 * 
 * @author liaozhicheng
 * @date 2015-1-26
 * @since 1.0
 */
public class SelectOption {
	
	private Object text;
	
	private Object value;
	
	
	public SelectOption(Object text, Object value) {
		this.text = text;
		this.value = value;
	}
	
	
	public boolean isFull() {
		return this.text != null && this.value != null;
	}
	
	
	public Object getText() {
		return text;
	}
	
	
	public void setText(Object text) {
		this.text = text;
	}
	
	
	public Object getValue() {
		return value;
	}
	
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	
	@Override
	public String toString() {
		return "[ " + text.toString() + " : " + value.toString() + " ]";
	}
	
	
	public String toOption() {
		StringBuilder option = new StringBuilder();
		option.append("<option value='").append(this.value.toString()).append("'>")
				.append(this.text.toString()).append("</option>");
		return option.toString();
	}
	
}
