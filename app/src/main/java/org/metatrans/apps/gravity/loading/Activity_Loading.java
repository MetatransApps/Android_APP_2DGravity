package org.metatrans.apps.gravity.loading;


import org.metatrans.apps.gravity.app.Application_Gravity;
import org.metatrans.apps.gravity.lib.R;
import org.metatrans.apps.gravity.main.Activity_Main_Gravity;
import org.metatrans.apps.gravity.menu.Activity_Menu_Levels;
import org.metatrans.commons.Activity_Base;
import org.metatrans.commons.ads.api.IAdsConfiguration;
import org.metatrans.commons.app.Application_Base_Ads;
import org.metatrans.commons.cfg.colours.ConfigurationUtils_Colours;
import org.metatrans.commons.cfg.colours.IConfigurationColours;
import org.metatrans.commons.loading.View_Loading_Base;


public class Activity_Loading extends org.metatrans.commons.loading.Activity_Loading_Base_Ads {
	
	
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
