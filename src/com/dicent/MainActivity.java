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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class MainActivity extends DicentActivity {
	private static final String FRAGMENT_ATTACK_DICE = "attackDice";
	private static final String FRAGMENT_DEFENCE_DICE = "defenseDice";
	
	private DiceFragment attackDiceFragment;
	private DiceFragment defenseDiceFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		state.saveState(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		attackDiceFragment = (DiceFragment)fm.findFragmentByTag(FRAGMENT_ATTACK_DICE);
		
		if (state.getDescentVersion().equals(DicentPreferencesActivity.DESCENT_SECOND_EDITION)) {
			attackDiceFragment.setType(DiceFragment.ATTACK);
			attackDiceFragment.setShowCheckbox(true);
			attackDiceFragment.setDice(state.getSecondEdAttackDice());
			attackDiceFragment.setCheckboxText(getResources().getString(R.string.attack));
			attackDiceFragment.setEnabled(state.isAttackEnabled());
			
			defenseDiceFragment = (DiceFragment)fm.findFragmentByTag(FRAGMENT_DEFENCE_DICE);
			if (defenseDiceFragment == null) {
				defenseDiceFragment = new DiceFragment();
				ft.add(R.id.diceLayout, defenseDiceFragment, FRAGMENT_DEFENCE_DICE);
			}
			defenseDiceFragment.setType(DiceFragment.DEFENSE);
			defenseDiceFragment.setShowCheckbox(true);
			defenseDiceFragment.setDice(state.getSecondEdDefenseDice());
			defenseDiceFragment.setCheckboxText(getResources().getString(R.string.defense));
			defenseDiceFragment.setEnabled(state.isDefenseEnabled());
		} else {
			attackDiceFragment.setShowCheckbox(false);
			attackDiceFragment.setDice(state.getFirstEdDice());
			defenseDiceFragment = (DiceFragment)fm.findFragmentByTag(FRAGMENT_DEFENCE_DICE);
			if (defenseDiceFragment != null) ft.remove(defenseDiceFragment);
			defenseDiceFragment = null;
		}
		ft.commit();
	}
	
	public void roll(View v) {
		state.rollEffects();
		
		DiceList resultDice = state.getResultDice();
		resultDice.clear();
		ArrayList<DiceList> diceLists = new ArrayList<DiceList>(2);
		if (attackDiceFragment.isEnabled())
			diceLists.add(attackDiceFragment.getDice());
		if (defenseDiceFragment != null && defenseDiceFragment.isEnabled())
			diceLists.add(defenseDiceFragment.getDice());
		
		for (DiceList dice : diceLists) {
			for (DieData data : dice) {
				if (!data.isSelected || !data.isVisible()) continue;
				DieData newData = data.copy();
				newData.isSelected = false;
				newData.roll();
				resultDice.add(newData);
			}
		}
		
		Intent resultsIntent = new Intent(getBaseContext(), ResultsActivity.class);
		if (state.getDescentVersion().equals(DicentPreferencesActivity.DESCENT_SECOND_EDITION))
			resultsIntent.putExtra(INTENTKEY_MODE, MODE_SECONDED);
		else resultsIntent.putExtra(INTENTKEY_MODE, MODE_FIRSTED);
		startActivity(resultsIntent);
	}
}
