package com.dicent.dice.secondEd;

public class BrownDieData extends DefenseDie {

	@Override
	public int getDieType() {
		return BROWN_DIE;
	}

	@Override
	public int getDieColor() {
		return 0xffa76936;
	}

	@Override
	public boolean usesBlackIcons() {
		return false;
	}
}
