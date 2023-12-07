package org.metatrans.apps.gravity.main;


import android.view.View;

import org.metatrans.commons.IActivityInterstitial;
import org.metatrans.commons.ads.api.IAdsConfiguration;
import org.metatrans.commons.app.Application_Base;
import org.metatrans.commons.graphics2d.app.Application_2D_Base;
import org.metatrans.commons.graphics2d.main.Activity_Main_Base2D;


public class Activity_Main_Gravity extends Activity_Main_Base2D implements IActivityInterstitial {
	
	
	@Override
	protected View createMainView() {
		return new View_Main_Gravity(this);
	}

	
	public String getInterstitialName() {
		return IAdsConfiguration.AD_ID_INTERSTITIAL1;
	}


	@Override
	protected void onResume() {

		super.onResume();

		if (!Application_2D_Base.getInstance().getGameData().paused) {

			Application_Base.getInstance().getMelodiesManager().setMelody(Application_Base.getInstance().getUserSettings().melody_cfg_id);
		}
	}


	@Override
	protected void onPause() {

		super.onPause();

		Application_Base.getInstance().getMelodiesManager().stop();
	}
}
