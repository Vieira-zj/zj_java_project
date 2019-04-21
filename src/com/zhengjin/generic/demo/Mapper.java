package com.zhengjin.generic.demo;

public interface Mapper<I, O> {

	O map(I item);
}
