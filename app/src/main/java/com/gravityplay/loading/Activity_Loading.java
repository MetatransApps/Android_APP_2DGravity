package com.gravityplay.loading;


import com.apps.mobile.android.commons.Activity_Base;
import com.apps.mobile.android.commons.ads.api.IAdsConfiguration;
import com.apps.mobile.android.commons.app.Application_Base_Ads;
import com.apps.mobile.android.commons.cfg.colours.ConfigurationUtils_Colours;
import com.apps.mobile.android.commons.cfg.colours.IConfigurationColours;
import com.apps.mobile.android.commons.loading.View_Loading_Base;
import com.gravityplay.lib.R;
import com.gravityplay.app.Application_Gravity;
import com.gravityplay.main.Activity_Main_Gravity;
import com.gravityplay.menu.Activity_Menu_Levels;


public class Activity_Loading extends com.apps.mobile.android.commons.loading.Activity_Loading_Base_Ads {
	
	
	@Override
	protected int getBackgroundImageID() {
		return R.drawable.ic_logo_balls;
	}
	
	
	@Override
	protected void load() {
		
		try {
			Application_Gravity.getInstance().getGameData();
		} catch(Exception e) {
			e.printStackTrace();
			Application_Gravity.getInstance().recreateGameDataObject();
		}
	}
	
	
	@Override
	protected Class<? extends Activity_Base> getNextActivityClass() {
		return Activity_Main_Gravity.class;
	}
	
	
	@Override
	protected Class<? extends Activity_Base> getActivityClass_Menu2() {
		return Activity_Menu_Levels.class;
	}
	
	
	@Override
	protected int getText_Menu2() {
		return R.string.levels;
	}
	
	
	@Override
	protected String getBannerName() {
		return IAdsConfiguration.AD_ID_BANNER1;
	}
	
	
	@Override
	protected String getInterstitialName() {
		return IAdsConfiguration.AD_ID_INTERSTITIAL1;
	}
	
	
	@Override
	protected View_Loading_Base getLoadingView() {
		View_Loading view = new View_Loading(this, ((Application_Base_Ads)getApplication()).getUserSettings());
		return view;
	}
	
	
	@Override
	protected IConfigurationColours getColoursCfg() {
		IConfigurationColours coloursCfg = ConfigurationUtils_Colours.getConfigByID(((Application_Base_Ads)getApplication()).getUserSettings().uiColoursID);
		return coloursCfg;
	}
}
