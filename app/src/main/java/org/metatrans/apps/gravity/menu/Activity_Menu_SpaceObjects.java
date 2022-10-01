package org.metatrans.apps.gravity.menu;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import org.metatrans.apps.gravity.model.UserSettings_Gravity;
import org.metatrans.apps.gravity.model.World_Gravity;
import org.metatrans.commons.Activity_Base;
import org.metatrans.commons.R;
import org.metatrans.commons.app.Application_Base;
import org.metatrans.commons.ui.list.ListViewFactory;
import org.metatrans.commons.ui.list.RowItem_CIdTD;
import org.metatrans.commons.ui.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;


public class Activity_Menu_SpaceObjects extends Activity_Base {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		System.out.println("Activity_Menu_Colours_Base: onCreate()");
		
		super.onCreate(savedInstanceState);
		
		int currOrderNumber = ConfigurationUtils_SpaceObjects.getOrderNumber(((UserSettings_Gravity) Application_Base.getInstance().getUserSettings()).cfg_id_space_objects);
		
		LayoutInflater inflater = LayoutInflater.from(this);

		ViewGroup frame = ListViewFactory.create_CITD_ByXML(this, inflater, buildRows(currOrderNumber), -1, currOrderNumber, new OnItemClickListener_Menu());
		
		setContentView(frame);
		
		setBackgroundPoster(R.id.commons_listview_frame, 55);
	}
	
	
	@Override
	protected int getBackgroundImageID() {
		return 0;//R.drawable.ic_rainbow;
	}
	
	
	public List<RowItem_CIdTD> buildRows(int initialSelection) {
		
		List<RowItem_CIdTD> rowItems = new ArrayList<RowItem_CIdTD>();

		IConfigurationSpaceObjects[] objects_cfg_all = ConfigurationUtils_SpaceObjects.getAll();
		
		for (int i = 0; i < objects_cfg_all.length; i++) {

			IConfigurationSpaceObjects objects_cfg = objects_cfg_all[i];

			if (objects_cfg == null) {

				continue;
			}

			int bitmap_id = objects_cfg.getBitmapResourceID();

			Bitmap old = null;

			Bitmap bitmap1 = BitmapUtils.fromResource(this, bitmap_id);
			old = bitmap1;
			bitmap1 = BitmapUtils.createScaledBitmap(bitmap1, getIconSize(), getIconSize());
			BitmapUtils.recycle(bitmap1, old);

			Bitmap bitmap = bitmap1;

			Drawable drawable = BitmapUtils.createDrawable(this, bitmap);
			
			RowItem_CIdTD item = new RowItem_CIdTD(i == initialSelection, drawable, getString(objects_cfg.getName()), "");

			rowItems.add(item);
		}
		
		return rowItems;
	}
	
	
	private class OnItemClickListener_Menu implements
			AdapterView.OnItemClickListener {
		
		
		private OnItemClickListener_Menu() {
		}
		
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			//System.out.println("Selection POS=" + position + ", id=" + id);
			
			int currOrderNumber = ConfigurationUtils_SpaceObjects.getOrderNumber(((UserSettings_Gravity) Application_Base.getInstance().getUserSettings()).cfg_id_space_objects);

			if (position != currOrderNumber) {

				int newCfgID = ConfigurationUtils_SpaceObjects.getID(position);

				changeCfg(newCfgID);
			}
			
			finish();
		}
	}
	
	
	public void changeCfg(int cfg_id_space_objects) {

		((UserSettings_Gravity) Application_Base.getInstance().getUserSettings()).cfg_id_space_objects = cfg_id_space_objects;
		
		((Application_Base)getApplication()).storeUserSettings();

		org.metatrans.commons.graphics2d.model.GameData data
				= (org.metatrans.commons.graphics2d.model.GameData) ((Application_Base) getApplication()).getGameData();

		((World_Gravity) data.world).initBitmaps();
	}
}
