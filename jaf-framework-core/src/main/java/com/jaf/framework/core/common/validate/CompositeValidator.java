package com.jaf.framework.core.common.validate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * 组合模式
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public abstract class CompositeValidator<T> implements Validator<T> {

    // 多个校验规则器之间采用并行方式校验
    private boolean concurrentVerify = false;

    private final List<Validator<T>> validators = new ArrayList<Validator<T>>();

    public CompositeValidator() {
        this.initValidators();
    }

    @Override
    public final Result verify(final T target) {
        Stream<Validator<T>> stream = concurrentVerify ? validators.parallelStream() : validators.stream();
        return stream.map(v -> v.verify(target)).reduce(Result.success(), Result::and);
    }

    /**
     * 任何一个校验规则失败则直接返回
     * @param target
     * @return
     */
    public Result anyFailed(final T target) {
        for(Validator<T> validator : validators) {
            Result r;
            if((r = validator.verify(target)).isFailed())
                return r;
        }
        return Result.success();
    }

    protected void addValidator(Validator<T> aValidator) {
        validators.add(aValidator);
    }

    protected void addValidators(Collection<Validator<T>> aValidators) {
        validators.addAll(aValidators);
    }

    public Validator<T> parallel() {
        this.concurrentVerify = true;
        return this;
    }

    protected abstract void initValidators();

}
