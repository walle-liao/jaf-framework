package com.jaf.framework.core.web.controller;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年10月5日
 * @since 1.0
 */
public class DateConverter extends PropertyEditorSupport {

	private static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat TIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	
	public static final DateConverter DATE_CONVERTER_INSTANCE = new DateConverter(DATEFORMAT);
	public static final DateConverter TIME_CONVERTER_INSTANCE = new DateConverter(TIMEFORMAT);

	
    private DateFormat dateFormat;
    private boolean allowEmpty = true;
	
    
    public DateConverter() {
    }
  
    public DateConverter(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }
  
    public DateConverter(DateFormat dateFormat, boolean allowEmpty) {
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
    }
    
    
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && StringUtils.isEmpty(text)) {
            setValue(null);
        } else {
            try {
                if(this.dateFormat != null)
                    setValue(this.dateFormat.parse(text));
                else {
                    if(text.contains(":"))
                        setValue(TIMEFORMAT.parse(text));
                    else
                        setValue(DATEFORMAT.parse(text));
                }
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
            }
        }
	}

	
    @Override  
    public String getAsText() {
        Date value = (Date) getValue();
        DateFormat dateFormat = this.dateFormat;
        if(dateFormat == null)
            dateFormat = TIMEFORMAT;
        return (value != null ? dateFormat.format(value) : "");
    }
	
}
