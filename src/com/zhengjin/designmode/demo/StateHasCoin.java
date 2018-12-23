package com.zhengjin.designmode.demo;

public class StateHasCoin implements State {

	private CandyMachine2 mCandyMachine;

	public StateHasCoin(CandyMachine2 machine) {
		this.mCandyMachine = machine;
	}

	@Override
	public void insertCoin() {
		System.out.println("you can't insert another coin!");
	}

	@Override
	public void returnCoin() {
		System.out.println("coin return!");
		this.mCandyMachine.setState(mCandyMachine.mOnReadyState);
	}

	@Override
	public void turnCrank() {
		System.out.println("crank turn...!");
		this.mCandyMachine.setState(mCandyMachine.mSoldState);
	}

	@Override
	public void dispense() {
	}

	@Override
	public void printstate() {
		System.out.println("***HasCoin***");
	}

}
