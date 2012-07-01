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

import com.dicent.dice.Die;
import com.dicent.dice.DieData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectDiceActivity extends DicentActivity {
	public static final int BASE_DICE_COUNT = 12;
	public static final int RTL_DICE_COUNT = 10;
	public static final String SAVED_BASE_DICE_SHARED_PREFERENCE = "savedBaseDice";
	public static final String SAVED_RTL_DICE_SHARED_PREFERENCE = "savedRTLDice";
	public static final String SAVED_TRANSPARENT_DIE_SHARED_PREFERENCE = "savedTransparentDie";

	private DieAdapter dieAdapter = new DieAdapter();

	private int playerIndex;
	private boolean isOverlord;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_dice);

		//collect objects created in XML
		GridView diceGrid = (GridView)findViewById(R.id.selectDiceGrid);
		Button rollButton = (Button)findViewById(R.id.selectDiceRollButton);

		float density = getResources().getDisplayMetrics().density;
		diceGrid.setColumnWidth((int)(density * Die.scale));

		//create stuff
		playerIndex = getIntent().getIntExtra("playerIndex", 0);
		dieAdapter.setDice(state.getPlayerDieDatas(playerIndex));
		state.registerPreferencesChangedNotifier(dieAdapter);
		isOverlord = getIntent().getBooleanExtra("isOverlord", false);

		//set listeners
		rollButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				state.rollEffects();

				DiceList resultDice = state.getResultDice();
				resultDice.clear();
				for (DieData data : state.getPlayerDieDatas(playerIndex)) {
					if (!data.isSelected || !data.isVisible()) continue;
					DieData newData = data.copy();
					newData.isSelected = false;
					newData.roll();
					resultDice.add(newData);
				}

				Intent resultsIntent = new Intent(getBaseContext(), ResultsActivity.class);
				startActivity(resultsIntent);
			}
		});
		
		diceGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				DiceList dice = state.getPlayerDieDatas(playerIndex);
				DieData die = dice.get(position);
				if (die.isPowerDie() &&	dice.selectedPowerDiceCount() >= 5 && !die.isSelected)
					return;
				
				die.isSelected = !die.isSelected;
				dieAdapter.notifyDataSetChanged();
			}
		});
		
		if (savedInstanceState == null && isOverlord) {
			for (DieData data : state.getPlayerDieDatas(playerIndex))
				data.isSelected = false;
		}

		diceGrid.setAdapter(dieAdapter);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		state.unregisterPreferencesChangedNotifier(dieAdapter);
	}
}
