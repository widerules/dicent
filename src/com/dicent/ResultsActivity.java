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

import com.dicent.dice.DieData;
import com.dicent.dice.SideValues;
import com.dicent.dice.firstEd.FirstEdDie;
import com.dicent.dice.firstEd.FirstEdDieData;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultsActivity extends DicentActivity {
	private Button addSilverButton;
	private Button addGoldButton;
	
	private int mode;
	
	private DiceFragment diceFragment;
	private StatsFragment statsFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
		
		mode = getIntent().getIntExtra(INTENTKEY_MODE, 0);

		//collect objects created in XML
		Button rerollButton = (Button)findViewById(R.id.resultsRerollButton);
		Button addBlackButton = (Button)findViewById(R.id.resultsAddBlackButton);
		addSilverButton = (Button)findViewById(R.id.resultsAddSilverButton);
		addGoldButton = (Button)findViewById(R.id.resultsAddGoldButton);
		
		statsFragment = (StatsFragment)getSupportFragmentManager().findFragmentByTag("stats");
		diceFragment = (DiceFragment)getSupportFragmentManager().findFragmentByTag("diceGrid");
		
		

		//set listeners
		rerollButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (state.getResultDice().selectedDiceCount() > 0) {
					state.rollEffects();
				}
				else Toast.makeText(ResultsActivity.this, getResources().getString(R.string.rerollNotification),
						Toast.LENGTH_SHORT).show();
				for (DieData data : state.getResultDice()) {
					if (data.isSelected) {
						data.isSelected = false;
						data.roll();
					}
				}
				diceFragment.notifyDataSetChanged();
				statsFragment.updateStats();
			}
		});
		
		if (mode == MODE_FIRSTED) {
			addBlackButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (state.getResultDice().firstEdPowerDiceCount() >= 5) return;
					addRolledDie(FirstEdDieData.BLACK_DIE);
	
					state.rollEffects();
					statsFragment.updateStats();
				}
			});
	
			addSilverButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (state.getResultDice().firstEdPowerDiceCount() >= 5) return;
					addRolledDie(FirstEdDieData.SILVER_DIE);
	
					state.rollEffects();
					statsFragment.updateStats();
				}
			});
	
			addGoldButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (state.getResultDice().firstEdPowerDiceCount() >= 5) return;
					addRolledDie(FirstEdDieData.GOLD_DIE);
	
					state.rollEffects();
					statsFragment.updateStats();
				}
			});
		} else {
			addBlackButton.setVisibility(View.GONE);
			addSilverButton.setVisibility(View.GONE);
			addGoldButton.setVisibility(View.GONE);
		}

		diceFragment.setDice(state.getResultDice());
		statsFragment.setMode(mode);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		if (mode == MODE_FIRSTED) {
			if (state.isRtlEnabled()) {
				addSilverButton.setVisibility(View.VISIBLE);
				addGoldButton.setVisibility(View.VISIBLE);
			} else {
				addSilverButton.setVisibility(View.GONE);
				addGoldButton.setVisibility(View.GONE);
			}
		}
	}
	
	private void addRolledDie(int dieType) {
		FirstEdDieData newDie = FirstEdDieData.create(dieType);
		newDie.roll();
		state.getResultDice().add(newDie);
		diceFragment.diceChanged();
	}
}
