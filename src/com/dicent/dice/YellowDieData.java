package com.dicent.dice;

public class YellowDieData extends DieData {
	@Override
	public int getDieType() {
		return YELLOW_DIE;
	}

	@Override
	public int getDieColor() {
		return 0xffdddd00;
	}
	
	@Override
	public boolean usesBlackIcons() {
		return true;
	}

	@Override
	public SideValues getSideValues() {
		SideValues sv = new SideValues();
		switch (side) {
		case SIDE1:
			sv.range = 3;
			break;
		case SIDE2:
			sv.range = 3;
			break;
		case SIDE3:
			sv.range = 2;
			sv.surges = 1;
			break;
		case SIDE4:
			sv.range = 2;
			sv.surges = 1;
			break;
		case SIDE5:
			sv.range = 2;
			sv.wounds = 1;
			break;
		case SIDE6:
			sv.range = 1;
			sv.wounds = 1;
			break;
		}
		return sv;
	}
}
