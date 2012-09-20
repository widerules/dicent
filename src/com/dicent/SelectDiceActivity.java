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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectDiceActivity extends DicentActivity {
	public static final String INTENTKEY_PLAYERINDEX = "playerIndex";
	public static final String INTENTKEY_ISOVERLORD = "isOverlord";
	public static final String SAVED_BASE_DICE_SHARED_PREFERENCE = "savedBaseDice";
	public static final String SAVED_RTL_DICE_SHARED_PREFERENCE = "savedRTLDice";
	public static final String SAVED_TRANSPARENT_DIE_SHARED_PREFERENCE = "savedTransparentDie";

	private int playerIndex;
	private boolean isOverlord;
	private int mode;
	
	private DiceList diceList;
	
	private DiceFragment diceFragment;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_dice);

		//collect objects created in XML
		Button rollButton = (Button)findViewById(R.id.selectDiceRollButton);
		diceFragment = (DiceFragment)getSupportFragmentManager().findFragmentByTag("diceGrid");

		//create stuff
		playerIndex = getIntent().getIntExtra(INTENTKEY_PLAYERINDEX, 0);
		isOverlord = getIntent().getBooleanExtra(INTENTKEY_ISOVERLORD, false);
		mode = getIntent().getIntExtra(INTENTKEY_MODE, 0);
		
		if (mode == MODE_FIRSTED) diceList = state.getFirstEdDieDatas(playerIndex);
		else if (mode == MODE_SECONDED_ATTACK) diceList = state.getSecondEdAttackDieDatas(playerIndex);
		else if (mode == MODE_SECONDED_DEFENSE) diceList = state.getSecondEdDefenseDieDatas(playerIndex);
		
		if (isOverlord)
			for (DieData data : diceList) data.isSelected = false;
		
		diceFragment.setDice(diceList);

		//set listeners
		rollButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				state.rollEffects();

				DiceList resultDice = state.getResultDice();
				resultDice.clear();
				for (DieData data : diceList) {
					if (!data.isSelected || !data.isVisible()) continue;
					DieData newData = data.copy();
					newData.isSelected = false;
					newData.roll();
					resultDice.add(newData);
				}

				Intent resultsIntent = new Intent(getBaseContext(), ResultsActivity.class);
				resultsIntent.putExtra(INTENTKEY_MODE, mode);
				startActivity(resultsIntent);
			}
		});
	}
}
