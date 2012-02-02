package com.dicent.dice;

public class SilverDieData extends DieData {

	public SilverDieData() {
		super();
		
		dieType = SILVER_DIE;
		backgroundColor = 0xffaaaaaa;
		blackIcons = false;
		powerDie = true;
	}

	@Override
	public SideValues getSideValues() {
		SideValues sv = new SideValues();
		switch (side) {
		case SIDE1:
			sv.enhancement = 2;
			break;
		case SIDE2:
			sv.enhancement = 2;
			break;
		case SIDE3:
			sv.enhancement = 2;
			break;
		case SIDE4:
			sv.surges = 2;
			break;
		case SIDE5:
			sv.surges = 2;
			break;
		case SIDE6:
			
			break;
		}
		return sv;
	}
}
