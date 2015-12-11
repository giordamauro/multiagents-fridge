package org.unicen.multiagents.fridge.controller;

enum BuyLowestPriceState {

	PRODUCT_BUY_AGREE(1),
	NOTHING_TO_BUY(2);
	
	private final int value;

	private BuyLowestPriceState(int value) {
		this.value = value;
	}
	
	public int value(){
		return value;
	}
}
