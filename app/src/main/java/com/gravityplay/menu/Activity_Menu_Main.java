package com.gravityplay.menu;


import java.util.List;

import org.metatransapps.commons.app.Application_Base;
import org.metatransapps.commons.cfg.menu.Config_MenuMain_Base;
import org.metatransapps.commons.cfg.menu.IConfigurationMenu_Main;
import org.metatransapps.commons.menu.Activity_Menu_Main_Base;

import android.app.Activity;
import android.content.Intent;

import com.gravityplay.lib.R;
import com.gravityplay.cfg.world.ConfigurationUtils_Level;


public class Activity_Menu_Main extends Activity_Menu_Main_Base {
	
	
	public static int CFG_MENU_LEVELS			 		= 15;
	
	
	@Override
	protected int getBackgroundImageID() {
		return 0;//R.drawable.ic_logo_balls;
	}
	
	
	@Override
	protected List<IConfigurationMenu_Main> getEntries() {
		
		
		List<IConfigurationMenu_Main> entries = super.getEntries();
		
		//Add on 4 or 3 position in order to leave 'Invite Friends' on position 0 and other options on positions 1, 2, 3.
		int addIndex = 3;
		if (Application_Base.getInstance().getApp_Me().getPaidVersion() != null) {
			addIndex = 4;
		}
		
		entries.remove(entries.size() - 1);
		entries.remove(entries.size() - 1);
		entries.remove(entries.size() - 1);
		
		entries.add(addIndex, new Config_MenuMain_Base() {
			
			@Override
			public int getName() {
				return R.string.levels;
			}
			
			@Override
			public int getIconResID() {
				return R.drawable.ic_rainbow;
			}
			
			@Override
			public int getID() {
				return CFG_MENU_LEVELS;
			}
			
			@Override
			public String getDescription_String() {
				return /*getString(R.string.label_current) + ": " +*/ ConfigurationUtils_Level.getInstance().getConfigByID(Application_Base.getInstance().getUserSettings().modeID).getName_String();
			}
			
			@Override
			public Runnable getAction() {
				
				return new Runnable() {
					
					@Override
					public void run() {
						Activity currentActivity = Application_Base.getInstance().getCurrentActivity();
						if (currentActivity != null) {
							
							currentActivity.finish();
							
							Intent i = new Intent(currentActivity, Activity_Menu_Levels.class);
							currentActivity.startActivity(i);
						}
					}
				};
			}
		});
		
		
		return entries;
	}
}
