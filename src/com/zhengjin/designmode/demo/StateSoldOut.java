package com.zhengjin.designmode.demo;

public class StateSoldOut implements State {

	private CandyMachine2 mCandyMachine;

	public StateSoldOut(CandyMachine2 machine) {
		this.mCandyMachine = machine;
	}

	@Override
	public void insertCoin() {
		System.out.println("you can't insert coin, the machine sold out!");
	}

	@Override
	public void returnCoin() {
		System.out.println("you can't return, you haven't inserted a coin yet!");
	}

	@Override
	public void turnCrank() {
		System.out.println("you turned, but there are no candies!");
	}

	@Override
	public void dispense() {
	}

	@Override
	public void printstate() {
		System.out.println("***SoldOutState***, count: " + this.mCandyMachine.getCount());
	}

}
