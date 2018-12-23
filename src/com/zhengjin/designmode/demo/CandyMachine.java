package com.zhengjin.designmode.demo;

public class CandyMachine {

	final static int SoldOutState = 0; // 初始状态
	final static int OnReadyState = 1; // 待机状态
	final static int HasCoin = 2; // 准备状态
	final static int SoldState = 3; // 售出状态

	private int curState = SoldOutState;
	private int count = 0;

	public CandyMachine(int count) {
		this.count = count;
		if (count > 0) {
			this.curState = OnReadyState;
		}
	}

	public void insertCoin() {
		switch (this.curState) {
		case SoldOutState:
			System.out.println("you can't insert coin, the machine sold out!");
			break;
		case OnReadyState: // 只有在待机状态的时候, 投入硬币行为正确, 并将状态改变为准备状态
			this.curState = HasCoin;
			System.out.println("you have inserted a coin, next, please turn crank!");
			break;
		case HasCoin:
			System.out.println("you can't insert another coin!");
			break;
		case SoldState:
			System.out.println("please wait! we are giving you a candy!");
			break;
		}
	}

	public void returnCoin() {
		switch (this.curState) {
		case SoldOutState:
			System.out.println("you can't return, you haven't inserted a coin yet!");
			break;
		case OnReadyState:
			System.out.println("you haven't inserted a coin yet!");
			break;
		case HasCoin:
			System.out.println("coin return!");
			this.curState = OnReadyState;
			break;
		case SoldState:
			System.out.println("sorry, you already have turned the crank!");
			break;
		}
	}

	public void turnCrank() {
		switch (this.curState) {
		case SoldOutState:
			System.out.println("you turned, but there are no candies!");
			break;
		case OnReadyState:
			System.out.println("you turned, but you haven't inserted a coin!");
			break;
		case HasCoin:
			System.out.println("crank turn...!");
			this.curState = SoldState;
			this.dispense();
			break;
		case SoldState:
			System.out.println("we are giving you a candy, turning another get nothing!");
			break;
		}
	}

	// 发放糖果行为
	private void dispense() {
		this.count = this.count - 1;
		System.out.println("a candy rolling out!");
		if (count > 0) {
			this.curState = OnReadyState;
		} else {
			System.out.println("Oo, out of candies");
			this.curState = SoldOutState;
		}
	}

	public void printState() {
		switch (this.curState) {
		case SoldOutState:
			System.out.println("***SoldOutState***");
			break;
		case OnReadyState:
			System.out.println("***OnReadyState***");
			break;
		case HasCoin:
			System.out.println("***HasCoin***");
			break;
		case SoldState:
			System.out.println("***SoldState***");
			break;
		}
	}
}
