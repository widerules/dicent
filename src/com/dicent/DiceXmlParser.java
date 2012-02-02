package com.dicent;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.dicent.dice.DieData;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

public class DiceXmlParser {
	public static void parse(Resources res, int resId, DieAdapter dieAdapter) throws XmlPullParserException, IOException {
		XmlResourceParser xrp = res.getXml(resId);
		
		int eventType = xrp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_TAG:
				if (xrp.getName().equals("die")) {
					dieAdapter.addDie(getDieType(xrp.getAttributeValue(null, "type")));
				}
				break;
			}
			
			eventType = xrp.next();
		}
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
