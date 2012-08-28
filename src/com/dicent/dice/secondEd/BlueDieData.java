package com.dicent.dice.secondEd;

import java.util.HashMap;

import com.dicent.dice.SideValues;

public class BlueDieData extends AttackDieData {
	private static HashMap<Side, SideValues> sideValues = new HashMap<Side, SideValues>();
	
	static {
		sideValues.put(Side.SIDE1, new SideValues(6, 1, 1));
		sideValues.put(Side.SIDE2, new SideValues(5, 1, 0));
		sideValues.put(Side.SIDE3, new SideValues(4, 2, 0));
		sideValues.put(Side.SIDE4, new SideValues(3, 2, 0));
		sideValues.put(Side.SIDE5, new SideValues(2, 2, 1));
		sideValues.put(Side.SIDE6, new SideValues(true));
	}
	
	@Override
	public int getDieType() {
		return BLUE_DIE;
	}

	@Override
	public int getDieColor() {
		return 0xff0000aa;
	}

	@Override
	public boolean usesBlackIcons() {
		return false;
	}

	@Override
	public SideValues getSideValues() {
		return sideValues.get(side);
	}

}
