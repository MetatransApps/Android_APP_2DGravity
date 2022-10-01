package org.metatrans.apps.gravity.model;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.metatrans.apps.gravity.cfg.world.ConfigurationUtils_Level;
import org.metatrans.apps.gravity.menu.ConfigurationUtils_SpaceObjects;
import org.metatrans.commons.cfg.colours.IConfigurationColours;
import org.metatrans.commons.graphics2d.model.UserSettings;


public class UserSettings_Gravity extends UserSettings {
	
	
	private static final long serialVersionUID = 3199714911195754477L;
	

	public int cfg_id_space_objects;


	public UserSettings_Gravity() {
		
		super();
		
		uiColoursID 		= IConfigurationColours.CFG_COLOUR_GRAY;
		modeID 				= ConfigurationUtils_Level.LEVEL_ID_DEFAULT;
		
		//fixFields("constructor");
	}
	
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		
	    fixFields("writeObject");
	    
	    // default serialization 
	    oos.defaultWriteObject();
	}
	
	
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
	    
	    // default deserialization
	    ois.defaultReadObject();
	    
	    fixFields("readObject");
	}
	
	
	private void fixFields(String op) {

		if (cfg_id_space_objects == 0) {

			cfg_id_space_objects = ConfigurationUtils_SpaceObjects.START_INDEX;
		}

		if (uiColoursID == 0) {
	    	uiColoursID 		= IConfigurationColours.CFG_COLOUR_GRAY;
	    	System.out.println("UserSettings: " + op + " - updating colour id");
	    }
	    
		if (modeID == 0) {
			modeID = ConfigurationUtils_Level.LEVEL_ID_DEFAULT;
		}
	}
}
