package org.metatrans.apps.gravity.main;


import android.view.View;

import org.metatrans.commons.IActivityInterstitial;
import org.metatrans.commons.ads.api.IAdsConfiguration;
import org.metatrans.commons.graphics2d.main.Activity_Main_Base2D;


public class Activity_Main_Gravity extends Activity_Main_Base2D implements IActivityInterstitial {
	
	
	@Override
	protected View createMainView() {
		return new View_Main_Gravity(this);
	}

	
	public String getInterstitialName() {
		return IAdsConfiguration.AD_ID_INTERSTITIAL1;
	}
}
