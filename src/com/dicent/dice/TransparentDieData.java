package com.dicent.dice;

public class TransparentDieData extends DieData {

	public TransparentDieData() {
		super();
		
		dieType = TRANSPARENT_DIE;
		backgroundColor = 0xffcccccc;
		blackIcons = true;
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
