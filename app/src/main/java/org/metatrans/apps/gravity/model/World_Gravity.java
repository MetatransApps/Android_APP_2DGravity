package org.metatrans.apps.gravity.model;


import java.util.ArrayList;
import java.util.List;

import org.metatrans.apps.gravity.lib.R;
import org.metatrans.apps.gravity.menu.ConfigurationUtils_SpaceObjects;
import org.metatrans.apps.gravity.menu.IConfigurationSpaceObjects;
import org.metatrans.commons.app.Application_Base;
import org.metatrans.commons.graphics2d.app.Application_2D_Base;
import org.metatrans.commons.graphics2d.model.World;
import org.metatrans.commons.graphics2d.model.entities.Entity2D_Bullet;
import org.metatrans.commons.graphics2d.model.entities.Entity2D_Challenger;
import org.metatrans.commons.graphics2d.model.entities.Entity2D_Moving;
import org.metatrans.commons.graphics2d.model.entities.IEntity2D;
import org.metatrans.commons.model.I2DBitmapCache;
import org.metatrans.commons.ui.utils.BitmapUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;


public class World_Gravity extends World {
	
	
	private static final long serialVersionUID = 3276469433687306613L;
	
	
	private static transient Bitmap bitmap_level;
	private static transient Bitmap bitmap_lives;
	private transient Bitmap bitmap_balls;
	private transient Bitmap bitmap_background;

	private List<IEntity2D> killersEntities_forPlayer;
	private List<IEntity2D> killersEntities_forChallengers;
	
	private Float pointer_x;
	private Float pointer_y;
	
	
	public World_Gravity(Context _activity, int _maze_size_x, int _maze_size_y) {
		
		super(_activity, _maze_size_x, _maze_size_y);
		
		SPEED_MAX_CHALLENGER = 1 * SPEED_MAX_CHALLENGER / 2;
		
		killersEntities_forPlayer 		= new ArrayList<IEntity2D>();
		killersEntities_forChallengers 	= new ArrayList<IEntity2D>();
	}
	
	
	@Override
	public synchronized void update() {
		
		Application_2D_Base.getInstance().getGameData().count_lives--;
		((GameData_Gravity)Application_2D_Base.getInstance().getGameData()).count_objects = getCountEntries_InScreen();
		
		super.update();		
	}


	@Override
	protected I2DBitmapCache createBitmapCache() {

		return BitmapCache_Gravity.getStaticInstance(BitmapCache_Gravity.BITMAP_ID_COMMON);
	}

	
	public void initBitmaps() {
		
		System.out.println("!EXPENSIVE OP: RE-INIT BITMAPS OF THE WORLD");
		
		bitmap_level   			= BitmapUtils.fromResource(Application_Base.getInstance(), R.drawable.ic_level);
		bitmap_lives 			= BitmapUtils.fromResource(Application_Base.getInstance(), R.drawable.ic_heart);

		IConfigurationSpaceObjects objects_config = ConfigurationUtils_SpaceObjects.getConfigByID(((UserSettings_Gravity) Application_Base.getInstance().getUserSettings()).cfg_id_space_objects);
		bitmap_balls   		= getBitmapCache().get(objects_config.getBitmapResourceID());
		bitmap_background 	= getBitmapCache().get(objects_config.getBitmapResourceID_Background());
	}


	@Override
	public boolean hasToDrawPlayerLast() {

		return false;
	}

	
	public void setPointer(Float x, Float y) {
		pointer_x = x;
		pointer_y = y;
	}
	
	
	public Float getPointerX() {
		return pointer_x;
	}
	
	
	public Float getPointerY() {
		return pointer_y;
	}
	
	
	private int getCountEntries_InScreen() {
		int count = 0;
		for (Entity2D_Moving moving : getMovingEntities()) {
			if (RectF.intersects(moving.getEnvelop(), getCamera())) {
				count++;
			}
		}
		return count - 1;//Remove player entity
	}
	
	
	@Override
	public synchronized void addEntity(IEntity2D entity) {
		
		super.addEntity(entity);
		
		if (entity instanceof Entity2D_Challenger) {
			killersEntities_forPlayer.add(entity);
		}
		
		if (entity instanceof Entity2D_Bullet) {
			killersEntities_forChallengers.add(entity);
		}
		
		if (getSpecialEntities().size() > 1) {
			throw new IllegalStateException();
		}
	}
	
	
	@Override
	public synchronized void removeMovingEntity(Entity2D_Moving entity) {
		
		super.removeMovingEntity(entity);
		
		if (entity instanceof Entity2D_Challenger) {
			killersEntities_forPlayer.remove(entity);
		}
		
		if (entity instanceof Entity2D_Bullet) {
			killersEntities_forChallengers.remove(entity);
		}
	}
	
	
	@Override
	public synchronized void button1(float dx, float dy) {
		
		//((Entity2D_Player_Labyrints)getPlayerEntity()).shot(dx, dy);
		
	}
	
	
	protected List<IEntity2D> getKillersEntities_forPlayer() {
		return killersEntities_forPlayer;
	}
	
	
	protected List<IEntity2D> getKillersEntities_forChallengers() {
		return killersEntities_forChallengers;
	}


	public Bitmap getBitmap_level() {
		if (bitmap_level == null || bitmap_level.isRecycled()) {
			initBitmaps();
		}
		return bitmap_level;
	}


	public Bitmap getBitmap_balls() {
		if (bitmap_balls == null || bitmap_balls.isRecycled()) {
			initBitmaps();
		}
		return bitmap_balls;
	}


	public Bitmap getBitmap_background() {
		if (bitmap_background == null || bitmap_background.isRecycled()) {
			initBitmaps();
		}
		return bitmap_background;
	}

	
	public Bitmap getBitmap_lives() {
		if (bitmap_lives == null || bitmap_lives.isRecycled()) {
			initBitmaps();
		}
		return bitmap_lives;
	}
}
