package org.metatrans.apps.gravity.model;


import org.metatrans.apps.gravity.cfg.world.ConfigurationUtils_Level;
import org.metatrans.apps.gravity.menu.ConfigurationUtils_SpaceObjects;
import org.metatrans.commons.cfg.colours.IConfigurationColours;
import org.metatrans.commons.cfg.melody.IConfigurationMelody;
import org.metatrans.commons.graphics2d.model.UserSettings;


public class UserSettings_Gravity extends UserSettings {
	
	
	private static final long serialVersionUID 		= 3199714911195754477L;

	public static final int MODEL_VERSION_1 		= 0;
	public static final int MODEL_VERSION_2 		= 1;

	private static final int DEFAULT_CFG_ID_COLOUR = IConfigurationColours.CFG_COLOUR_BLUE_PETROL;


	public int cfg_id_space_objects;

	public int model_version = MODEL_VERSION_2;


	public UserSettings_Gravity() {
		
		super();

		cfg_id_space_objects 	= ConfigurationUtils_SpaceObjects.START_INDEX;
		uiColoursID 			= DEFAULT_CFG_ID_COLOUR;
		modeID 					= ConfigurationUtils_Level.LEVEL_ID_DEFAULT;
		melody_cfg_id 			= IConfigurationMelody.CFG_MELODY_SPACE_V2;
	}


	private void fixFields(String op) {

		if (cfg_id_space_objects == 0 || model_version == MODEL_VERSION_1) {

			cfg_id_space_objects = ConfigurationUtils_SpaceObjects.START_INDEX;
		}

		if (uiColoursID == 0 || model_version == MODEL_VERSION_1) {
	    	uiColoursID 		= DEFAULT_CFG_ID_COLOUR;
	    	System.out.println("UserSettings: " + op + " - updating colour id");
	    }
	    
		if (modeID == 0 || model_version == MODEL_VERSION_1) {
			modeID = ConfigurationUtils_Level.LEVEL_ID_DEFAULT;
		}

		model_version = MODEL_VERSION_2;
	}
}
