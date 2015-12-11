package org.unicen.multiagents.fridge.controller;

enum QuerySupermarketsState {

	SUPERMARKETS_LIST_READY(1),
	NOTHING_TO_BUY(2);
	
	private final int value;

	private QuerySupermarketsState(int value) {
		this.value = value;
	}
	
	public int value(){
		return value;
	}
}
