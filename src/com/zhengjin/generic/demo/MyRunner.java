package com.zhengjin.generic.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Update list by map function.
 * 
 * @param <I> input parameter type
 * @param <O> output parameter type
 * 
 */
public class MyRunner<I, O> {

	private List<I> inList;
	private List<O> outList;

	public MyRunner<I, O> init(List<I> list) {
		this.inList = list;
		this.outList = new ArrayList<O>(list.size() * 2);
		return this;
	}

	public MyRunner<I, O> map(Mapper<I, O> mapper) {
		for (I item : this.inList) {
			this.outList.add(mapper.map(item));
		}
		return this;
	}

	public void forEach(Handler<O> handler) {
		for (O item : this.outList) {
			handler.handle(item);
		}
	}

}
