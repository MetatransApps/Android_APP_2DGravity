package org.metatrans.apps.gravity.model.entities;


import java.util.List;

import org.metatrans.apps.gravity.app.Application_Gravity;
import org.metatrans.apps.gravity.lib.R;
import org.metatrans.apps.gravity.main.Activity_Result;
import org.metatrans.apps.gravity.model.GameData_Gravity;
import org.metatrans.apps.gravity.model.WorldGenerator_Gravity;
import org.metatrans.apps.gravity.model.World_Gravity;
import org.metatrans.commons.app.Application_Base;
import org.metatrans.commons.app.Application_Base_Ads;
import org.metatrans.commons.cfg.colours.ConfigurationUtils_Colours;
import org.metatrans.commons.graphics2d.app.Application_2D_Base;
import org.metatrans.commons.graphics2d.model.World;
import org.metatrans.commons.graphics2d.model.entities.Entity2D_Player;
import org.metatrans.commons.graphics2d.model.entities.IEntity2D;
import org.metatrans.commons.model.LevelResult_Base;
import org.metatrans.commons.ui.utils.BitmapUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;


public class Entity2D_Player_Gravity extends Entity2D_Player {
	
	
	private static final long serialVersionUID 				= 564766339328166322L;

	private static final int bitmap_rotation_step 			= 15;

	private static transient Bitmap bitmap_org;
	private static transient Bitmap bitmap_rotated;
	private static transient Bitmap[] bitmap_rotated_cache 	= new Bitmap[360 / bitmap_rotation_step + 1];


	private int bitmap_rotation_degrees 					= 0;


	public Entity2D_Player_Gravity(World _world, RectF _evelop,
			List<? extends IEntity2D> _killerEntities, int _colour) {
		super(_world, _evelop, _world.getGroundEntities_SolidOnly(), _killerEntities);
	}
	
	
	@Override
	protected World_Gravity getWorld() {
		return (World_Gravity) super.getWorld();
	}
	
	
	protected Class<? extends Activity> getActivityResult_Class() {
		return Activity_Result.class;
	}
	
	
	@Override
	public void nextMoment(float takts) {
		
		super.nextMoment(takts);

		if (levelCompletedCondition()) {
			
			getGameData().level_completed = true;
			
			getGameData().count_stars = 3;//Work around
			
			LevelResult_Base levelResult = Application_2D_Base.getInstance().getLevelsResults().getResult(Application_2D_Base.getInstance().getUserSettings().modeID);
			
			levelResult.setCount_stars(getGameData().count_stars);
			Application_2D_Base.getInstance().storeLevelsResults();
			
			if (getGameData().count_stars >= 3) {
				Application_2D_Base.getInstance().setNextLevel();
			}
			getGameData().last_count_stars = getGameData().count_stars;
			getGameData().count_stars = 0;
			
			getGameData().total_count_objects += getGameData().count_objects;
			getGameData().count_objects = 0;
			
			System.out.println("Entity2D_Player_Gravity.nextMoment: levelCompletedCondition total_count_objects=" + getGameData().total_count_objects);
			
			getGameData().world = Application_2D_Base.getInstance().createNewWorld();
			
			Application_Base.getInstance().storeGameData();

		} else {
			
			if (getGameData().count_lives <= 0) {
				
				killedFinal();
				
				getGameData().count_lives = 0;//Fix it to 0 instead of -1

			} else {

				bitmap_rotation_degrees += bitmap_rotation_step;
				if (bitmap_rotation_degrees >= 360) {
					bitmap_rotation_degrees = 0;
				}
			}
		}
	}
	
	
	protected boolean levelCompletedCondition() {
		return getGameData().count_objects == WorldGenerator_Gravity.getObjectsCount();
	}
	
	
	@Override
	protected void killedFinal() {
		
		getGameData().total_count_objects += getGameData().count_objects;
		getGameData().count_objects = 0;
		
		System.out.println("Entity2D_Player_Gravity.killedFinal: total_count_objects=" + getGameData().total_count_objects);
		
		super.killedFinal();
	}
	
	
	protected GameData_Gravity getGameData() {
		return (GameData_Gravity) (Application_Gravity.getInstance()).getGameData();
	}


	@Override
	public Bitmap getBitmap() {

		if (bitmap_org == null) {

			bitmap_org = BitmapUtils.fromResource(Application_2D_Base.getInstance(), R.drawable.gravity_blackhole_blue_v2);
			bitmap_org = BitmapUtils.createScaledBitmap(bitmap_org, (int) (getEnvelop().right - getEnvelop().left), (int) (getEnvelop().bottom - getEnvelop().top));
		}

		int bitmap_index = Math.abs(bitmap_rotation_degrees) / bitmap_rotation_step;

		if (bitmap_rotated_cache[bitmap_index] == null) {

			Matrix matrix = new Matrix();

			matrix.postRotate(360 - bitmap_rotation_degrees);

			bitmap_rotated_cache[bitmap_index] = Bitmap.createBitmap(bitmap_org, 0, 0, bitmap_org.getWidth(), bitmap_org.getHeight(), matrix, true);
		}

		bitmap_rotated = bitmap_rotated_cache[bitmap_index];

		return bitmap_rotated;
	}


	@Override
	public RectF getEnvelop_ForDraw() {

		RectF target = new RectF();

		//Null if set to 0, 0 - no touch of the screen by user.
		if((getWorld().getPointerX() == null || getWorld().getPointerY() == null)
				|| (getWorld().getPointerX() == 0 && getWorld().getPointerY() == 0)
			) {

			return null;
		}

		target.left = getWorld().getPointerX() + getWorld().getCamera().right / 3 - getEnvelop().width() / 2; //4 * getEnvelop().width() / 5 - 1 * getEnvelop().width() / 11;
		target.right = target.left + getEnvelop().width();
		target.top = getWorld().getPointerY() + getWorld().getCamera().bottom / 3 - getEnvelop().height() / 2; //2 * getEnvelop().height() / 3 - 1 * getEnvelop().width() / 13;
		target.bottom = target.top + getEnvelop().height();

		return target;
	}


	@Override
	public void draw(Canvas c) {

		Bitmap bitmap = getBitmap();

		if (bitmap == null) {

			/*getPaint().setColor(Color.WHITE);
				getPaint().setAlpha(255);
				c.drawCircle(getEnvelop_ForDraw().left + (getEnvelop_ForDraw().right - getEnvelop_ForDraw().left) / 2,
						getEnvelop_ForDraw().top + (getEnvelop_ForDraw().bottom - getEnvelop_ForDraw().top) / 2,
						 (getEnvelop_ForDraw().right - getEnvelop_ForDraw().left) / 2,
						 getPaint());
			*/

		} else {

			RectF envelop = getEnvelop_ForDraw();

			if (envelop != null) {

				c.drawBitmap(bitmap, null, envelop, null);
			}
		}
	}
}
