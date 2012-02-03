package com.dicent.dice;

public class GoldDieData extends DieData {
	@Override
	public int getDieType() {
		return GOLD_DIE;
	}

	@Override
	public int getDieColor() {
		return 0xff666600;
	}
	
	@Override
	public boolean isPowerDie() {
		return true;
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
