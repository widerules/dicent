/** This file is part of Dicent.
 *
 *  Dicent is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  Dicent is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with Dicent.  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.dicent.dice;

public class SideValues {
	private int range = 0;
	private int wounds = 0;
	private int surges = 0;
	private int enhancement = 0;
	private int shields = 0;
	private boolean fail = false;
	
	public SideValues(int _range, int _wounds, int _surges) {
		range = _range;
		wounds = _wounds;
		surges = _surges;
	}
	
	public SideValues(int _enhancement) {
		enhancement = _enhancement;
	}
	
	public SideValues(int _shields, int placeholder) {
		shields = _shields;
	}
	
	public SideValues(boolean _fail) {
		fail = _fail;
	}
	
	public int getRange() {return range;}
	
	public int getWounds() {return wounds;}
	
	public int getSurges() {return surges;}
	
	public int getEnhancement() {return enhancement;}
	
	public int getShields() {return shields;}
	
	public boolean isFail() {return fail;}
}
