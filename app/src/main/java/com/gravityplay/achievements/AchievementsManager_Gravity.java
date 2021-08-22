package com.gravityplay.achievements;


import android.content.Context;

import com.apps.mobile.android.commons.achievements.AchievementsManager_Base;
import com.apps.mobile.android.commons.app.Application_Base;
import com.apps.mobile.android.commons.cfg.achievements.IConfigurationAchievements;
import com.apps.mobile.android.commons.ui.Toast_Base;
import com.gravityplay.cfg.achievements.Config_Achievement_Invite3Friends;


public class AchievementsManager_Gravity extends AchievementsManager_Base {
	
	
	public static int IDS_MAKE_STARS_250 		= 1000;
	public static int IDS_MAKE_STARS_500 		= 1100;
	public static int IDS_MAKE_STARS_750 		= 1200;
	public static int IDS_MAKE_STARS_1000 		= 1300;
	public static int IDS_MAKE_STARS_2500 		= 1400;
	public static int IDS_MAKE_STARS_5000 		= 1500;
	public static int IDS_MAKE_STARS_7500 		= 1600;
	public static int IDS_MAKE_STARS_10000 		= 1700;
	public static int IDS_MAKE_STARS_25000		= 1800;
	public static int IDS_MAKE_STARS_50000		= 1900;
	public static int IDS_MAKE_STARS_75000		= 2000;
	public static int IDS_MAKE_STARS_100000		= 2100;
	
	
	private IConfigurationAchievements[] ALL_CFGs;
	
	
	public AchievementsManager_Gravity(Application_Base app) {
		
		super(app);
		
		ALL_CFGs = new IConfigurationAchievements[] {
		 	new Config_Achievement_Invite3Friends(),
		 	//new Config_Achievement_StopPiecesOnLoading(),
			//new Config_Achievement_ChangeColours(),
		 	
			/*new Config_Achievement_MakeSteps_A01_250(IDS_MAKE_STARS_250),
			new Config_Achievement_MakeSteps_A02_500(IDS_MAKE_STARS_500),
			new Config_Achievement_MakeSteps_A03_750(IDS_MAKE_STARS_750),
			new Config_Achievement_MakeSteps_A04_1000(IDS_MAKE_STARS_1000),
			new Config_Achievement_MakeSteps_A05_2500(IDS_MAKE_STARS_2500),
			new Config_Achievement_MakeSteps_A06_5000(IDS_MAKE_STARS_5000),
			new Config_Achievement_MakeSteps_A07_7500(IDS_MAKE_STARS_7500),
			new Config_Achievement_MakeSteps_A08_10000(IDS_MAKE_STARS_10000),
			new Config_Achievement_MakeSteps_A09_25000(IDS_MAKE_STARS_25000),
			new Config_Achievement_MakeSteps_A10_50000(IDS_MAKE_STARS_50000),
			new Config_Achievement_MakeSteps_A11_75000(IDS_MAKE_STARS_75000),
			new Config_Achievement_MakeSteps_A12_100000(IDS_MAKE_STARS_100000),
			*/
		};
	}
	
	
	@Override
	public IConfigurationAchievements[] getAll() {
		return ALL_CFGs;
	}
	
	
	@Override
	public void sentNotification(Context context, Integer achievementID) {
		//Do Nothing
		Toast_Base.showToast_InCenter(context, "Notification: New Achievement " + getConfigByID(achievementID).getName());
	}
}
