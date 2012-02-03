package com.dicent.dice;

public class GreenDieData extends DieData {
	@Override
	public int getDieType() {
		return GREEN_DIE;
	}

	@Override
	public int getDieColor() {
		return 0xFF00AA00;
	}

	@Override
	public SideValues getSideValues() {
		SideValues sv = new SideValues();
		switch (side) {
		case SIDE1:
			sv.wounds = 3;
			break;
		case SIDE2:
			sv.wounds = 3;
			break;
		case SIDE3:
			sv.wounds = 2;
			sv.surges = 1;
			break;
		case SIDE4:
			sv.wounds = 2;
			sv.surges = 1;
			break;
		case SIDE5:
			sv.range = 1;
			sv.wounds = 2;
			break;
		case SIDE6:
			sv.range = 1;
			sv.wounds = 1;
			break;
		}
		return sv;
	}
}
