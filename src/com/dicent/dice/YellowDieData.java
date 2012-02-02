package com.dicent.dice;

import android.graphics.Color;

public class YellowDieData extends DieData {
	
	public YellowDieData() {
		super();
		
		dieType = YELLOW_DIE;
		backgroundColor = Color.YELLOW;
		blackIcons = true;
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
