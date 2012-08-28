package com.dicent.dice.secondEd;

import java.util.HashMap;

import com.dicent.dice.SideValues;

public class YellowDieData extends PowerDieData {
	private static HashMap<Side, SideValues> sideValues = new HashMap<Side, SideValues>();
	
	static {
		sideValues.put(Side.SIDE1, new SideValues(2, 1, 0));
		sideValues.put(Side.SIDE2, new SideValues(1, 1, 0));
		sideValues.put(Side.SIDE3, new SideValues(1, 0, 1));
		sideValues.put(Side.SIDE4, new SideValues(0, 2, 1));
		sideValues.put(Side.SIDE5, new SideValues(0, 2, 0));
		sideValues.put(Side.SIDE6, new SideValues(0, 1, 1));
	}
	
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
		return sideValues.get(side);
	}
}