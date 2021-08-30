package org.metatrans.apps.gravity.main;


import org.metatrans.apps.gravity.app.Application_Gravity;
import org.metatrans.apps.gravity.lib.R;
import org.metatrans.apps.gravity.model.GameData_Gravity;
import org.metatrans.commons.ads.api.IAdsConfiguration;
import org.metatrans.commons.graphics2d.app.Application_2D_Base;
import org.metatrans.commons.graphics2d.main.Activity_Result_Base2D;
import org.metatrans.commons.graphics2d.model.UserSettings;
import org.metatrans.commons.main.View_Result;


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
