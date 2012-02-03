package com.dicent.dice;

import android.graphics.Color;

public class BlackDieData extends DieData {
	@Override
	public int getDieType() {
		return BLACK_DIE;
	}
	
	@Override
	public int getDieColor() {
		return Color.BLACK;
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
			sv.enhancement = 1;
			break;
		case SIDE2:
			sv.enhancement = 1;
			break;
		case SIDE3:
			sv.enhancement = 1;
			break;
		case SIDE4:
			sv.surges = 1;
			break;
		case SIDE5:
			sv.surges = 1;
			break;
		case SIDE6:
			
			break;
		}
		return sv;
	}
}
