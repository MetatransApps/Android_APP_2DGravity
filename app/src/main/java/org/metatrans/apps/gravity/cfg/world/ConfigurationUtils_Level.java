package org.metatrans.apps.gravity.cfg.world;


import android.content.Context;

import org.metatrans.commons.cfg.ConfigurationUtils_Base;
import org.metatrans.commons.cfg.IConfigurationEntry;
import org.metatrans.commons.graphics2d.app.Application_2D_Base;
import org.metatrans.commons.ui.utils.ScreenUtils;


public class ConfigurationUtils_Level extends ConfigurationUtils_Base {
	
	
	private static final String TAG_NAME = ConfigurationUtils_Level.class.getName();
	
	
	public static final int LEVEL_ID_DEFAULT = 1;
	
	
	public static ConfigurationUtils_Level getInstance() {
		return (ConfigurationUtils_Level) getInstance(TAG_NAME);
	}
	
	
	public static void createInstance(Context context) {
		
		IConfigurationEntry[] cfgs_levels = new IConfigurationEntry[14];
		
		int[] size_xy 	= new int[] {2047, 1080}; //ScreenUtils.getScreenSize(); NPE
		float speed 	= Math.min(size_xy[0], size_xy[1]) / 50;
		
		for (int i=0; i<cfgs_levels.length; i++) {
			
			float cur_max_speed = speed * (1 + 1 * i / 4f);
			float spaceMultiplier = 2 + 1 * i / 3f;
			
			cfgs_levels[i] = new Configuration_World(i + 1, cur_max_speed, spaceMultiplier);
		}
		
		createInstance(TAG_NAME, new ConfigurationUtils_Level(), cfgs_levels);
	}
	
	
	public IConfigurationWorld getConfigByID(int id) {
		return (IConfigurationWorld) super.getConfigByID(id);
	}
}
