package com.zhengjin.designmode.demo;

public class StateOnReady implements State {

	private CandyMachine2 mCandyMachine;

	public StateOnReady(CandyMachine2 machine) {
		this.mCandyMachine = machine;
	}

	@Override
	public void insertCoin() {
		System.out.println("you have inserted a coin, next, please turn crank!");
		this.mCandyMachine.setState(mCandyMachine.mHasCoinState);
	}

	@Override
	public void returnCoin() {
		System.out.println("you haven't inserted a coin yet!");
	}

	@Override
	public void turnCrank() {
		System.out.println("you turned, but you haven't inserted a coin!");
	}

	@Override
	public void dispense() {
	}

	@Override
	public void printstate() {
		System.out.println("***OnReadyState***");
	}

}
