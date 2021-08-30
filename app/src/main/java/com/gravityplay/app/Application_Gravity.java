package com.gravityplay.app;


import org.metatransapps.commons.achievements.IAchievementsManager;
import org.metatransapps.commons.app.Application_Base;
import org.metatransapps.commons.cfg.app.IAppConfig;
import org.metatransapps.commons.cfg.colours.ConfigurationUtils_Colours;
import org.metatransapps.commons.cfg.menu.ConfigurationUtils_Base_MenuMain;
import org.metatransapps.commons.engagement.ILeaderboardsProvider;
import org.metatransapps.commons.engagement.leaderboards.LeaderboardsProvider_Base;
import org.metatransapps.commons.events.api.IEventsManager;
import org.metatransapps.commons.graphics2d.app.Application_2D_Base;
import org.metatransapps.commons.graphics2d.model.IWorld;
import org.metatransapps.commons.model.GameData_Base;
import org.metatransapps.commons.model.UserSettings_Base;
import org.metatransapps.commons.ui.utils.DebugUtils;

import com.gravityplay.lib.BuildConfig;
import com.gravityplay.achievements.AchievementsManager_Gravity;
import com.gravityplay.cfg.app.AppConfig_Gravity;
import com.gravityplay.cfg.world.ConfigurationUtils_Level;
import com.gravityplay.events.EventsManager_Gravity;
import com.gravityplay.main.Activity_Result;
import com.gravityplay.model.GameData_Gravity;
import com.gravityplay.model.UserSettings_Gravity;
import com.gravityplay.model.WorldGenerator_Gravity;


public abstract class Application_Gravity extends Application_2D_Base {
	
	
	protected IAppConfig appConfig 					= new AppConfig_Gravity();

	
	@Override
	public void onCreate() {
		
		super.onCreate();
		//Called when the application is starting, before any other application objects have been created.
		
		System.out.println("Application_EC: onCreate called " + System.currentTimeMillis());
		
		ConfigurationUtils_Colours.class.getName();
		
		ConfigurationUtils_Level.createInstance();
		
		ConfigurationUtils_Base_MenuMain.createInstance();
	}
	
	
	@Override
	public IAppConfig getAppConfig() {
		return appConfig;
	}
	
	
	@Override
	public void setNextLevel() {
		getUserSettings().modeID = ConfigurationUtils_Level.getInstance().getNextConfigID(getUserSettings().modeID);
		Application_Base.getInstance().storeUserSettings();
		System.out.println("Next level: " + getUserSettings().modeID);
	}
	
	
	@Override
	public IWorld createNewWorld() {
		return WorldGenerator_Gravity.generate(this, ConfigurationUtils_Level.getInstance().getConfigByID(Application_Base.getInstance().getUserSettings().modeID));
	}
	
	
	@Override
	public GameData_Base createGameDataObject() {
		
		System.out.println("GAMEDATA CREATE");
		
		GameData_Gravity result = new GameData_Gravity();
		
		int levelID = getUserSettings().modeID;
		result.world = WorldGenerator_Gravity.generate(this, ConfigurationUtils_Level.getInstance().getConfigByID(levelID));
		
		result.timestamp_lastborn = System.currentTimeMillis();
		
		return result;
	}
	
	
	@Override
	protected UserSettings_Base createUserSettingsObject() {
		return new UserSettings_Gravity();
	}
	
	
	@Override
	protected ILeaderboardsProvider createLeaderboardsProvider() {
		return new LeaderboardsProvider_Base(this, Activity_Result.class);
	}
	
	
	@Override
	protected IEventsManager createEventsManager() {
		return new EventsManager_Gravity(getExecutor(), getAnalytics(), getAchievementsManager());
	}
	
	
	@Override
	protected IAchievementsManager createAchievementsManager() {
		return new AchievementsManager_Gravity(this);
	}

	
	@Override
	public boolean isTestMode() {
		boolean productiveMode = !BuildConfig.DEBUG || !DebugUtils.isDebuggable(this);
		return !productiveMode;
	}
}
