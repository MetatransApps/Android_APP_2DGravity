package com.gravityplay.main;


import android.view.View;

import com.apps.mobile.android.commons.ads.api.IAdsConfiguration;
import com.apps.mobile.android.commons.ads.impl.flow.IAdLoadFlow;
import com.apps.mobile.android.commons.app.Application_Base_Ads;
import com.apps.mobile.android.commons.app.IActivityInterstitial;
import com.apps.mobile.android.commons.graphics2d.main.Activity_Main_Base2D;


public class Activity_Main_Gravity extends Activity_Main_Base2D implements IActivityInterstitial {
	
	
	private IAdLoadFlow current_adLoadFlow_Interstitial;
	
	
	@Override
	protected View createMainView() {
		return new View_Main_Gravity(this);
	}
	

	@Override
	public void openInterstitial() {
		try {
			
			System.out.println("Activity_Main_StopTheBalls openInterstitial called");
			
			if (current_adLoadFlow_Interstitial != null) {
				System.out.println("Activity_Main_StopTheBalls openInterstitial RESUMED");
				current_adLoadFlow_Interstitial.resume();
			}
			
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}
	
	
	@Override
	protected void onResume() {
		
		super.onResume();

		if (getInterstitialName() != null) {

			current_adLoadFlow_Interstitial = ((Application_Base_Ads) getApplication()).getAdsManager().getCachedFlow(getInterstitialName());

			if (current_adLoadFlow_Interstitial == null) {

				System.out.println("Activity_Main create Interstitial");

				current_adLoadFlow_Interstitial = ((Application_Base_Ads) getApplication()).getAdsManager().createFlow_Interstitial_Mixed(getInterstitialName());
				((Application_Base_Ads) getApplication()).getAdsManager().putCachedFlow(getInterstitialName(), current_adLoadFlow_Interstitial);
			} else {

				System.out.println("Activity_Main Interstitial EXISTS");

				//current_adLoadFlow_Interstitial.cleanCurrent();
				current_adLoadFlow_Interstitial.pause();
			}
		}
	}
	
	
	private String getInterstitialName() {
		return IAdsConfiguration.AD_ID_INTERSTITIAL1;
	}
}
