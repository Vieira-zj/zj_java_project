package com.zhengjin.designmode.demo;

public class StateSold implements State {

	private CandyMachine2 mCandyMachine;

	public StateSold(CandyMachine2 machine) {
		this.mCandyMachine = machine;
	}

	@Override
	public void insertCoin() {
		System.out.println("please wait! we are giving you a candy!");
	}

	@Override
	public void returnCoin() {
		System.out.println("you haven't inserted a coin yet!");
	}

	@Override
	public void turnCrank() {
		System.out.println("we are giving you a candy, turning another get nothing!");
	}

	@Override
	public void dispense() {
		this.mCandyMachine.releaseCandy();
		if (this.mCandyMachine.getCount() > 0) {
			this.mCandyMachine.setState(mCandyMachine.mOnReadyState);
		} else {
			this.mCandyMachine.setState(mCandyMachine.mSoldOutState);
		}
	}

	@Override
	public void printstate() {
		System.out.println("***SoldState***");
	}

}
