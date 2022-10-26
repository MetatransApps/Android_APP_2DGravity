package org.metatrans.apps.gravity.menu;


import org.metatrans.apps.gravity.lib.R;

import java.util.HashMap;
import java.util.Map;


public class ConfigurationUtils_SpaceObjects {


	public static final int START_INDEX = 1;


	private static org.metatrans.apps.gravity.menu.IConfigurationSpaceObjects[] ALL_CFGs;


	static {

		ALL_CFGs = new org.metatrans.apps.gravity.menu.IConfigurationSpaceObjects[4];

		ALL_CFGs[0] = new Config_SpaceObjects(START_INDEX + 0, R.string.asteroid_blue_1, R.drawable.gravity_asteroid_blue_v2, R.drawable.gravity_background_purple, R.drawable.gravity_icon_purple);
		ALL_CFGs[1] = new Config_SpaceObjects(START_INDEX + 1, R.string.asteroid_blue_2, R.drawable.gravity_asteroid_blue_v2, R.drawable.gravity_background_ping, R.drawable.gravity_icon_ping);
		ALL_CFGs[2] = new Config_SpaceObjects(START_INDEX + 2, R.string.asteroid_green, R.drawable.gravity_asteroid_green_v2, R.drawable.gravity_background_green, R.drawable.gravity_icon_green);
		ALL_CFGs[3] = new Config_SpaceObjects(START_INDEX + 3, R.string.asteroid_gray, R.drawable.gravity_asteroid_gray_v2, R.drawable.gravity_background_red, R.drawable.gravity_icon_red);
	}

	private static final Map<Integer, org.metatrans.apps.gravity.menu.IConfigurationSpaceObjects> mapping = new HashMap<Integer, IConfigurationSpaceObjects>();
	
	
	static {

		for (int i=0; i<ALL_CFGs.length; i++) {

			org.metatrans.apps.gravity.menu.IConfigurationSpaceObjects cfg = ALL_CFGs[i];

			if (cfg!= null) {

				Integer id = cfg.getID();

				if (mapping.containsKey(id)) {
					throw new IllegalStateException("Duplicated cfg id: " + id);
				}

				mapping.put(id, cfg);
			}
		}
	}
	
	
	public static org.metatrans.apps.gravity.menu.IConfigurationSpaceObjects[] getAll() {
		return ALL_CFGs;
	}
	
	
	public static int getID(int orderNumber) {
		return ALL_CFGs[orderNumber].getID();
	}
	
	
	public static org.metatrans.apps.gravity.menu.IConfigurationSpaceObjects getConfigByID(int id) {

		org.metatrans.apps.gravity.menu.IConfigurationSpaceObjects result = mapping.get(id);
		
		if (result == null) {
			throw new IllegalStateException("Config with id = " + id + " not found.");
		}
		
		return result;
	}
	
	
	public static int getOrderNumber(int cfgID) {
		for (int i=0; i<ALL_CFGs.length; i++) {
			if (ALL_CFGs[i] != null) {
				Integer id = ALL_CFGs[i].getID();
				if (id == cfgID) {
					return i;
				}
			}
		}

		throw new IllegalStateException("CFG identifier " + cfgID + " not found.");
	}
}
