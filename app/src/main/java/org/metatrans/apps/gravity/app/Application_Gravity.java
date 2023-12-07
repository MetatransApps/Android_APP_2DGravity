package org.metatrans.apps.gravity.app;


import org.metatrans.apps.gravity.achievements.AchievementsManager_Gravity;
import org.metatrans.apps.gravity.cfg.world.ConfigurationUtils_Level;
import org.metatrans.apps.gravity.events.EventsManager_Gravity;
import org.metatrans.apps.gravity.lib.BuildConfig;
import org.metatrans.apps.gravity.lib.R;
import org.metatrans.apps.gravity.main.Activity_Result;
import org.metatrans.apps.gravity.model.BitmapCache_Gravity;
import org.metatrans.apps.gravity.model.GameData_Gravity;
import org.metatrans.apps.gravity.model.UserSettings_Gravity;
import org.metatrans.apps.gravity.model.WorldGenerator_Gravity;
import org.metatrans.commons.achievements.IAchievementsManager;
import org.metatrans.commons.app.Application_Base;
import org.metatrans.commons.cfg.colours.ConfigurationUtils_Colours;
import org.metatrans.commons.cfg.menu.ConfigurationUtils_Base_MenuMain;
import org.metatrans.commons.engagement.ILeaderboardsProvider;
import org.metatrans.commons.engagement.leaderboards.LeaderboardsProvider_Base;
import org.metatrans.commons.events.api.IEventsManager;
import org.metatrans.commons.graphics2d.app.Application_2D_Base;
import org.metatrans.commons.graphics2d.model.IWorld;
import org.metatrans.commons.model.BitmapCache_Base;
import org.metatrans.commons.model.GameData_Base;
import org.metatrans.commons.model.I2DBitmapCache;
import org.metatrans.commons.model.UserSettings_Base;
import org.metatrans.commons.ui.utils.DebugUtils;


public abstract class Application_Gravity extends Application_2D_Base {
	

	@Override
	public void onCreate() {

		ConfigurationUtils_Level.createInstance(this);

		getSFXManager().loadSounds(this,
				new int[] {
						org.metatrans.commons.R.raw.sfx_button_pressed_1,
						org.metatrans.commons.R.raw.sfx_button_pressed_2,
						org.metatrans.commons.R.raw.sfx_button_pressed_3,
						R.raw.sfx_new_game,
						R.raw.sfx_level_completed,
						R.raw.sfx_game_over,
						R.raw.sfx_blackhole_opening,
						R.raw.sfx_blackhole_is_open,
						R.raw.sfx_blackhole_closing
				}
		);

		super.onCreate();
		//Called when the application is starting, before any other application objects have been created.
		
		System.out.println("Application_EC: onCreate called " + System.currentTimeMillis());
		
		ConfigurationUtils_Colours.getAll();
		
		ConfigurationUtils_Base_MenuMain.createInstance();

		BitmapCache_Base.STATIC = new BitmapCache_Gravity(I2DBitmapCache.BITMAP_ID_STATIC);

		BitmapCache_Base.STATIC.initBitmaps();
	}

	
	@Override
	public void setNextLevel() {
		getUserSettings().modeID = ConfigurationUtils_Level.getInstance().getNextConfigID(getUserSettings().modeID);
		Application_Base.getInstance().storeUserSettings();
		System.out.println("Next level: " + getUserSettings().modeID);
	}
	
	
	@Override
	public IWorld createNewWorld() {

		return WorldGenerator_Gravity.generate(this,
				ConfigurationUtils_Level.getInstance().getConfigByID(Application_Base.getInstance().getUserSettings().modeID)
		);
	}
	
	
	@Override
	public GameData_Base createGameDataObject() {
		
		System.out.println("GAMEDATA CREATE");
		
		GameData_Gravity result = new GameData_Gravity();
		
		int levelID = getUserSettings().modeID;
		result.world = WorldGenerator_Gravity.generate(this, ConfigurationUtils_Level.getInstance().getConfigByID(levelID));
		
		result.timestamp_lastborn = System.currentTimeMillis();

		Application_Base.getInstance().getSFXManager().playSound(R.raw.sfx_new_game);

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

		return new EventsManager_Gravity(getExecutor(), getAchievementsManager());
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
