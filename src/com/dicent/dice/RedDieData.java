package com.dicent.dice;

public class RedDieData extends DieData {
	@Override
	public int getDieType() {
		return RED_DIE;
	}

	@Override
	public int getDieColor() {
		return 0xffcc0000;
	}

	@Override
	public SideValues getSideValues() {
		SideValues sv = new SideValues();
		switch (side) {
		case SIDE1:
			sv.wounds = 4;
			break;
		case SIDE2:
			sv.range = 1;
			sv.wounds = 3;
			sv.surges = 1;
			break;
		case SIDE3:
			sv.range = 1;
			sv.wounds = 3;
			break;
		case SIDE4:
			sv.range = 2;
			sv.wounds = 1;
			sv.surges = 1;
			break;
		case SIDE5:
			sv.range = 2;
			sv.wounds = 2;
			break;
		case SIDE6:
			sv.fail = true;
			break;
		}
		return sv;
	}
}
