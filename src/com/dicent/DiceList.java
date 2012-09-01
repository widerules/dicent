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

package com.dicent;

import java.util.ArrayList;

import com.dicent.dice.DieData;
import com.dicent.dice.firstEd.PowerDieData;

public class DiceList extends ArrayList<DieData> {
	private static final long serialVersionUID = 641740432738495384L;
	
	public DiceList() {
		super();
	}
	
	public DiceList(int capacity) {
		super(capacity);
	}
	
	public DiceList copy() {
		DiceList newList = new DiceList(size());
		for (DieData data : this) newList.add(data.copy());
		return newList;
	}
	
	public int firstEdPowerDiceCount() {
		int count = 0;
		for (DieData data : this)
			if (data instanceof PowerDieData) count++;
		return count;
	}
	
	public int selectedDiceCount() {
		int count = 0;
		for (DieData data : this)
			if (data.isSelected) count++;
		return count;
	}
	
	public int selectedFirstEdPowerDiceCount() {
		int count = 0;
		for (DieData data : this)
			if (data instanceof PowerDieData && data.isSelected && data.isVisible()) count++;
		return count;
	}
}
