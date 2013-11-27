/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dicent.dice.secondEd;

import java.util.HashMap;

import com.dicent.dice.SideValues;

public class GreenDieData extends PowerDieData {
    private static HashMap<Side, SideValues> sideValues = new HashMap<Side, SideValues>();
    
    static {
		sideValues.put(Side.SIDE1, new SideValues(0, 0, 1));
		sideValues.put(Side.SIDE2, new SideValues(0, 1, 0));
		sideValues.put(Side.SIDE3, new SideValues(1, 1, 1));
		sideValues.put(Side.SIDE4, new SideValues(0, 1, 1));
		sideValues.put(Side.SIDE5, new SideValues(1, 0, 1));
		sideValues.put(Side.SIDE6, new SideValues(1, 1, 0));
	}
    
    @Override
    public int getDieType() {
            return GREEN_DIE;
    }
    
    @Override
    public int getDieColor() {
            return 0xFF00AA00;
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
