package com.gravityplay.main;


import com.apps.mobile.android.commons.ads.api.IAdsConfiguration;
import com.apps.mobile.android.commons.graphics2d.app.Application_2D_Base;
import com.apps.mobile.android.commons.graphics2d.main.Activity_Result_Base2D;
import com.apps.mobile.android.commons.graphics2d.model.UserSettings;
import com.apps.mobile.android.commons.main.View_Result;
import com.gravityplay.lib.R;
import com.gravityplay.app.Application_Gravity;
import com.gravityplay.model.GameData_Gravity;


public class Activity_Result extends Activity_Result_Base2D {
	
	
	@Override
	public View_Result createView() {
		
		GameData_Gravity gameGata = (GameData_Gravity) ((Application_2D_Base) getApplication()).getGameData();
		UserSettings settings = (UserSettings) ((Application_Gravity)getApplication()).getUserSettings();
		
		View_Result view = new View_Result(this,
				gameGata.total_count_objects >= settings.best_scores, //new record
				false, //show mode
				null,//"All Levels", //mode name
				new String[] {getString(R.string.scores)},//, "l2", "l3"},
				new String[] {"" + gameGata.total_count_objects},//, "dy2", "dy3"},
				new String[] {"" + settings.best_scores}//, "db2", "db3"}
				);
		
		return view;
	}
	
	
	@Override
	protected String getBannerName() {
		return IAdsConfiguration.AD_ID_BANNER3;
	}
}
