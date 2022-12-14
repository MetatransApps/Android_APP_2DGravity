package org.metatrans.apps.gravity.model.entities;


import java.util.List;

import org.metatrans.apps.gravity.app.Application_Gravity;
import org.metatrans.apps.gravity.main.Activity_Result;
import org.metatrans.apps.gravity.model.BitmapCache_Gravity;
import org.metatrans.apps.gravity.model.GameData_Gravity;
import org.metatrans.apps.gravity.model.WorldGenerator_Gravity;
import org.metatrans.apps.gravity.model.World_Gravity;
import org.metatrans.commons.app.Application_Base;
import org.metatrans.commons.graphics2d.app.Application_2D_Base;
import org.metatrans.commons.graphics2d.model.World;
import org.metatrans.commons.graphics2d.model.entities.Entity2D_Player;
import org.metatrans.commons.graphics2d.model.entities.IEntity2D;
import org.metatrans.commons.model.LevelResult_Base;

import android.app.Activity;
import android.graphics.RectF;


public class Entity2D_Player_Gravity extends Entity2D_Player {
	
	
	private static final long serialVersionUID 				= 564766339328166322L;


	public Entity2D_Player_Gravity(World _world, RectF _evelop, List<? extends IEntity2D> _killerEntities) {

		super(_world, _evelop, _world.getGroundEntities_SolidOnly(), _killerEntities,

				BitmapCache_Gravity.BITMAP_ID_BLACKHOLE, 0);
	}


	@Override
	public RectF getEnvelop_ForDraw() {


		//Null if set to 0, 0 - no touch of the screen by user.
		if((getWorld().getPointerX() == null || getWorld().getPointerY() == null)
				|| (getWorld().getPointerX() == 0 && getWorld().getPointerY() == 0)
		) {

			return null;
		}


		RectF target = new RectF();

		target.left = getWorld().getPointerX() + getWorld().getCamera().left - getEnvelop().width() / 2;
		target.right = target.left + getEnvelop().width();
		target.top = getWorld().getPointerY() + getWorld().getCamera().top - getEnvelop().height() / 2;
		target.bottom = target.top + getEnvelop().height();


		return target;
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
			
			getGameData().count_stars = 3; //Work around
			
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
				
				getGameData().count_lives = 0; //Fix it to 0 instead of -1
			}
		}
	}


	protected boolean levelCompletedCondition() {

		return getGameData().count_objects >= WorldGenerator_Gravity.getObjectsCount();
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
}
