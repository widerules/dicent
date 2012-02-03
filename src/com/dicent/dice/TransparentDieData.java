package com.dicent.dice;

public class TransparentDieData extends DieData {
	@Override
	public int getDieType() {
		return TRANSPARENT_DIE;
	}

	@Override
	public int getDieColor() {
		return 0xffcccccc;
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
			sv.fail = true;
			break;
		case SIDE2:
			sv.fail = true;
			break;
		case SIDE3:
			
			break;
		case SIDE4:
			
			break;
		case SIDE5:
			
			break;
		case SIDE6:
			
			break;
		}
		return sv;
	}
}
