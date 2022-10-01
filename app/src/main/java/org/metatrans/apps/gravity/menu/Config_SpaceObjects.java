package org.metatrans.apps.gravity.menu;


import org.metatrans.apps.gravity.lib.R;
import org.metatrans.commons.cfg.ConfigurationEntry_Base;


public class Config_SpaceObjects extends ConfigurationEntry_Base implements org.metatrans.apps.gravity.menu.IConfigurationSpaceObjects {


	private int id;
	private int name_resource_id;
	private int bitmap_resource_id;

	public Config_SpaceObjects(int id, int name_Resource_ID, int bitmap_ResourceID) {

		this.id = id;
		this.name_resource_id = name_Resource_ID;
		this.bitmap_resource_id = bitmap_ResourceID;
	}

	
	@Override
	public int getID() {
		return id;
	}


	@Override
	public int getName() {
		return name_resource_id;
	}


	@Override
	public int getIconResID() {
		throw new UnsupportedOperationException();
	}


	@Override
	public int getBitmapResourceID() {
		return bitmap_resource_id;
	}
}
