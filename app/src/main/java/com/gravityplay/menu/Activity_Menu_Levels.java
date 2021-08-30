package com.gravityplay.menu;


import java.util.ArrayList;
import java.util.List;

import org.metatransapps.commons.Activity_Base;
import org.metatransapps.commons.Alerts_Base;
import org.metatransapps.commons.app.Application_Base;
import org.metatransapps.commons.cfg.ConfigurationUtils_Base;
import org.metatransapps.commons.cfg.IConfigurationEntry;
import org.metatransapps.commons.events.EventsData_Base;
import org.metatransapps.commons.graphics2d.app.Application_2D_Base;
import org.metatransapps.commons.ui.Toast_Base;
import org.metatransapps.commons.ui.list.ListViewFactory;
import org.metatransapps.commons.ui.list.RowItem_CIdTD;
import org.metatransapps.commons.ui.utils.BitmapUtils;

import com.gravityplay.lib.R;
import com.gravityplay.cfg.world.ConfigurationUtils_Level;
import com.gravityplay.cfg.world.IConfigurationWorld;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


public class Activity_Menu_Levels extends Activity_Base {
	
	
	private Bitmap bitmap_stars_0;
	private Bitmap bitmap_stars_1;
	private Bitmap bitmap_stars_2;
	private Bitmap bitmap_stars_3;
	private Bitmap bitmap_locked;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		System.out.println("Activity_Menu_Levels: onCreate()");
		
		super.onCreate(savedInstanceState);
		
		bitmap_stars_0 = BitmapUtils.fromResource(this, R.drawable.ic_stars_0, getIconSize());
		bitmap_stars_1 = BitmapUtils.fromResource(this, R.drawable.ic_stars_1, getIconSize());
		bitmap_stars_2 = BitmapUtils.fromResource(this, R.drawable.ic_stars_2, getIconSize());
		bitmap_stars_3 = BitmapUtils.fromResource(this, R.drawable.ic_stars_3, getIconSize());
		bitmap_locked  = BitmapUtils.fromResource(this, R.drawable.ic_gift_locked, getIconSize());
		Bitmap old = bitmap_locked;
		bitmap_locked = BitmapUtils.toGrayscale(bitmap_locked);
		BitmapUtils.recycle(bitmap_locked, old);
		
		
		int currOrderNumber = getConfigurationLevels().getOrderNumber(((Application_Base)getApplication()).getUserSettings().modeID);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		ViewGroup frame = ListViewFactory.create_CITD_ByXML(this, inflater, buildRows(currOrderNumber), currOrderNumber, new OnItemClickListener_Menu());
		
		setContentView(frame);
		
		setBackgroundPoster(R.id.commons_listview_frame, 55);
	}
	
	
	protected ConfigurationUtils_Base getConfigurationLevels() { 
		return ConfigurationUtils_Level.getInstance();
	}
	
	
	public List<RowItem_CIdTD> buildRows(int initialSelection) {
		
		List<RowItem_CIdTD> rowItems = new ArrayList<RowItem_CIdTD>();
		
		IConfigurationEntry[] levelCfgs = getConfigurationLevels().getAll();
		
		for (int i = 0; i < levelCfgs.length; i++) {
			
			IConfigurationWorld colourWorld = (IConfigurationWorld) levelCfgs[i];
			
			boolean available = isAvailable(colourWorld);
			
			Bitmap bitmap = null;
			if (available) {
				int count_stars_cur = Application_2D_Base.getInstance().getLevelsResults().getResult(colourWorld.getID()).getCount_Stars();
				bitmap = getStarIcon(count_stars_cur);
			} else {
				bitmap = bitmap_locked;
			}
			
			Drawable drawable = BitmapUtils.createDrawable(this, bitmap);
			String description = colourWorld.getDescription_String();
			
			RowItem_CIdTD item = new RowItem_CIdTD(i == initialSelection, drawable,
					colourWorld.getName_String(),
					description
					);
			
			rowItems.add(item);
		}
		
		return rowItems;
	}
	
	
	private boolean isAvailable(IConfigurationWorld colourWorld) {
		
		int level_id = colourWorld.getID();
		
		if (level_id <= 2) {
			return true;
		}
		
		int count_stars_prev = (level_id <= 1) ? 3 : Application_2D_Base.getInstance().getLevelsResults().getResult(colourWorld.getID() - 1).getCount_Stars();
		int left_hours = getWaitingTime_Hours(24 * level_id);
		
		return count_stars_prev == 3 || left_hours <= 0;
	}
	
	
	private int getWaitingTime_Hours(int necessary_hours) {
		
		
		//First 2 levels are unlocked from the very begining
		necessary_hours -= 2 * 24;
		
		
		EventsData_Base eventsData = Application_2D_Base.getInstance().getEventsManager().getEventsData(this);
		
		int passed_hours = 0;
		if (eventsData != null) {
			long time_since_install_ms = System.currentTimeMillis() - eventsData.installation_time;
			passed_hours = (int) (time_since_install_ms / AlarmManager.INTERVAL_HOUR);
		}
		
		int left_hours = necessary_hours - passed_hours;
		
		return left_hours;
	}
	
	
	private Bitmap getStarIcon(int count) {
		
		if (count == 0) {
			
			return bitmap_stars_0;
			
		} else if (count == 1) {
			
			return bitmap_stars_1;
			
		} else if (count == 2) {
			
			return bitmap_stars_2;
			
		} else if (count == 3) {
			
			return bitmap_stars_3;
			
		} else {

			throw new IllegalStateException("Stars count = " + count);
		}
		
	}
	
	
	private class OnItemClickListener_Menu implements
			AdapterView.OnItemClickListener {
		
		
		private OnItemClickListener_Menu() {
		}
		
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
			
			//System.out.println("ColoursSelection POS=" + position + ", id=" + id);
			
			int currOrderNumber = getConfigurationLevels().getOrderNumber(((Application_Base)getApplication()).getUserSettings().modeID);
			
			if (position != currOrderNumber) {
				
				IConfigurationWorld colourWorld = (IConfigurationWorld) getConfigurationLevels().getAll()[position];
				boolean available = isAvailable(colourWorld);
				
				if (available) {
				
					AlertDialog.Builder adb = Alerts_Base.createAlertDialog_LoseGame(Activity_Menu_Levels.this,
							
							new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int which) {
									
									int newCfgID = getConfigurationLevels().getID(position);
									changeMode(newCfgID);
									
									finish();
								}
							}
					);
		
					adb.show();
					
				} else {
					
					int left_hours = getWaitingTime_Hours(24 * colourWorld.getID());
					String wait_time = "";
					if ((left_hours / 24) > 0) {
						wait_time += (left_hours / 24) + "d" + " ";
					}
					if ((left_hours % 24) > 0) {
						wait_time += (left_hours % 24) + "h";
					}
					
					
//					DateUtils.formatElapsedTime(elapsedSeconds)
					
					// Format the broken-down time in a locale-appropriate way.
			        // TODO: use icu4c when http://unicode.org/cldr/trac/ticket/3407 is fixed.
					/*StringBuffer buff = new StringBuffer();
					
			        Formatter f = new Formatter(buff, Locale.getDefault());
			        //initFormatStrings();
			        //if (hours > 0) {
			        String label = "";
			        if (left_hours / 24 > 0) {
				        label = f.format("dd HH", left_hours / 24, left_hours % 24).toString();	
			        } else {
				        label = f.format("HH", left_hours % 24).toString();
			        }
			        //} else {
			            //return f.format(sElapsedFormatMMSS, minutes, seconds).toString();
			        //}
			        
			        //Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
					 */
					
					Toast_Base.showToast_InCenter_Long(Activity_Menu_Levels.this,
							//"\r\n"
							//"\r\n"
							Application_Base.getInstance().getString(R.string.unlock_level_1) + " ?"
							+ "\r\n"
							+ "\r\n"
							+ " > " + Application_Base.getInstance().getString(R.string.unlock_level_2) + " " + position
							+ "\r\n"
							+ "\r\n"
							+ "             "
							+ Application_Base.getInstance().getString(R.string.or)
							+ "\r\n"
							+ "\r\n"
							+ " > " + Application_Base.getInstance().getString(R.string.unlock_level_3) + " " + wait_time
							//+ "\r\n"
							//+ "\r\n"
							);
					
				}
			}
		}
	}
	
	
	public void changeMode(int modeID) {
		
		((Application_Base)getApplication()).getUserSettings().modeID = modeID;
		
		((Application_Base)getApplication()).storeUserSettings();
		
		((Application_Base)getApplication()).recreateGameDataObject();
		
		//IEventsManager eventsManager = Application_Base.getInstance().getEventsManager();
		//eventsManager.register(this, eventsManager.create(IEvent_Base.MENU_OPERATION, IEvent_Base.MENU_OPERATION_CHANGE_MODE, modeID,
		//		"MENU_OPERATION", "CHANGE_MODE_L", "" + modeID));
	}
	
	
	/*@Override
	protected int getBackgroundImageID() {
		boolean left_handed = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
		return left_handed ? R.drawable.ic_bell_landscape : R.drawable.ic_bell_portrait;
	}*/
	
	
	@Override
	protected int getBackgroundImageID() {
		return 0;//R.drawable.ic_logo_balls;
	}
}
