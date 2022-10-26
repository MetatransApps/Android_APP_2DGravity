package org.metatrans.apps.gravity.menu;


import org.metatrans.commons.cfg.ConfigurationEntry_Base;


public class Config_SpaceObjects extends ConfigurationEntry_Base implements org.metatrans.apps.gravity.menu.IConfigurationSpaceObjects {


	private int id;
	private int name_resource_id;
	private int bitmap_resource_id;
	private int bitmap_resource_id_background;
	private int bitmap_resource_id_icon;


	public Config_SpaceObjects(int id, int name_Resource_ID, int bitmap_ResourceID, int bitmap_ResourceID_background, int bitmap_ResourceID_icon) {

		this.id = id;
		this.name_resource_id = name_Resource_ID;
		this.bitmap_resource_id = bitmap_ResourceID;
		this.bitmap_resource_id_background = bitmap_ResourceID_background;
		this.bitmap_resource_id_icon = bitmap_ResourceID_icon;
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


	@Override
	public int getBitmapResourceID_Background() {
		return bitmap_resource_id_background;
	}


	@Override
	public int getBitmapResourceID_Icon() {
		return bitmap_resource_id_icon;
	}
}
