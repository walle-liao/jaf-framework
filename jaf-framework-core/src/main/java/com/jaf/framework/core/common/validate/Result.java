package com.jaf.framework.core.common.validate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 *
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public final class Result {

    private static final Result SUCCESS = new Result(true, "");
    private static final Result FAIL = new Result(false, "");

    private final boolean success;
    private final String errorMessage;

    private Result(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public static Result value(boolean success, String errorMessage) {
        return new Result(success, errorMessage);
    }

    public static Result value(boolean success, Supplier<String> errorMessageSupplier) {
        return success ? Result.SUCCESS : new Result(success, errorMessageSupplier.get());
    }
    
    public static Result success() {
        return SUCCESS;
    }

    public static Result fail() {
        return FAIL;
    }

    public static Result fail(String errorMessage) {
        return new Result(false, errorMessage);
    }

    public static Result anyFail(Result ... rs) {
    	for(Result r : rs) {
    		if(r.isFailed())
    			return r;
    	}
    	return Result.SUCCESS;
    }
    
    public boolean isSucceed() {
        return this.success;
    }

    public boolean isFailed() {
        return !this.success;
    }

    public String getErrorMessage() {
        return this.isSucceed() ? "" : this.errorMessage;
    }

    public Result and(Result other) {
        return this.isFailed() ? this : new Result(this.success && other.success, this.appendErrorMessage(other));
    }

    public Result or(Result other) {
        return this.isSucceed() ? this : new Result(this.success || other.success, this.appendErrorMessage(other));
    }

    private String appendErrorMessage(Result other) {
        if(this.isSucceed())
            return other.getErrorMessage();

        if(other.isSucceed())
            return this.getErrorMessage();

        return new StringBuilder().append(this.getErrorMessage()).append(";").append(other.getErrorMessage()).toString();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("success", Boolean.valueOf(this.success));
        map.put("errorMessage", this.getErrorMessage());
        return map;
    }

}