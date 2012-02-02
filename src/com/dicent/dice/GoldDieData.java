package com.dicent.dice;

public class GoldDieData extends DieData {

	public GoldDieData() {
		super();
		
		dieType = GOLD_DIE;
		backgroundColor = 0xff666600;
		blackIcons = false;
		powerDie = true;
	}

	@Override
	public SideValues getSideValues() {
		SideValues sv = new SideValues();
		switch (side) {
		case SIDE1:
			sv.enhancement = 3;
			break;
		case SIDE2:
			sv.enhancement = 3;
			break;
		case SIDE3:
			sv.enhancement = 3;
			break;
		case SIDE4:
			sv.surges = 3;
			break;
		case SIDE5:
			sv.surges = 3;
			break;
		case SIDE6:
			
			break;
		}
		return sv;
	}
}
