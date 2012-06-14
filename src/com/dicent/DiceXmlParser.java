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

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.dicent.dice.DieData;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

public class DiceXmlParser {
	public static DiceList parse(Resources res, int resId) throws XmlPullParserException, IOException {
		XmlResourceParser xrp = res.getXml(resId);
		
		DiceList data = new DiceList();
		
		int eventType = xrp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_TAG:
				if (xrp.getName().equals("die")) {
					data.add(DieData.create(getDieType(xrp.getAttributeValue(null, "type"))));
				}
				break;
			}
			
			eventType = xrp.next();
		}
		return data;
	}
	
	private static int getDieType(String name) {
		if (name.equals("black")) return DieData.BLACK_DIE;
		else if (name.equals("blue")) return DieData.BLUE_DIE;
		else if (name.equals("gold")) return DieData.GOLD_DIE;
		else if (name.equals("green")) return DieData.GREEN_DIE;
		else if (name.equals("red")) return DieData.RED_DIE;
		else if (name.equals("silver")) return DieData.SILVER_DIE;
		else if (name.equals("transparent")) return DieData.TRANSPARENT_DIE;
		else if (name.equals("white")) return DieData.WHITE_DIE;
		else if (name.equals("yellow")) return DieData.YELLOW_DIE;
		
		return 0;
	}
}
