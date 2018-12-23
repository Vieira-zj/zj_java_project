package com.zhengjin.designmode.demo;

/**
 * 
 * @author zhengjin 
 * 状态模式以不同的状态封装不同的行为。
 * 添加新的状态很容易，不需要修改使用它们的Context对象。
 * 
 * 使用状态模式总是会增加设计中类的数目，这是为了要获得程序可扩展性，弹性的代价，
 * 如果你的代码不是一次性的，后期可能会不断加入不同的状态，那么状态模式的设计是绝对值得的。
 *
 */
public class CandyMachine2 {

	State mSoldOutState;
	State mOnReadyState;
	State mHasCoinState;
	State mSoldState;

	private State state;
	private int count = 0;

	public CandyMachine2(int count) {
		this.count = count;

		// 在状态模式中，每个状态通过持有Context的引用，来实现状态转移
		this.mOnReadyState = new StateOnReady(this);
		this.mHasCoinState = new StateHasCoin(this);
		this.mSoldState = new StateSold(this);
		this.mSoldOutState = new StateSoldOut(this);

		if (this.count > 0) {
			this.state = this.mOnReadyState;
		} else {
			this.state = this.mSoldOutState;
		}
	}

	public void setState(State state) {
		this.state = state;
	}

	public void insertCoin() {
		state.insertCoin();
	}

	public void returnCoin() {
		state.returnCoin();
	}

	public void turnCrank() {
		state.turnCrank();
		state.dispense();
	}

	public void printState() {
		this.state.printstate();
	}

	void releaseCandy() {
		if (this.count > 0) {
			--count;
		}
		System.out.println("a candy rolling out!");
	}

	public int getCount() {
		return this.count;
	}

	public static void main(String[] args) {
//		CandyMachine machine = new CandyMachine(6);
		CandyMachine2 machine = new CandyMachine2(6);

		machine.printState();

		machine.insertCoin();
		machine.printState();

		machine.turnCrank();
		machine.printState();

		machine.insertCoin();
		machine.printState();

		machine.turnCrank();
		machine.printState();
	}

}
