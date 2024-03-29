package org.metatrans.apps.gravity.model.entities;


import java.util.List;

import org.metatrans.apps.gravity.menu.ConfigurationUtils_SpaceObjects;
import org.metatrans.apps.gravity.menu.IConfigurationSpaceObjects;
import org.metatrans.apps.gravity.model.UserSettings_Gravity;
import org.metatrans.apps.gravity.model.World_Gravity;
import org.metatrans.commons.app.Application_Base;
import org.metatrans.commons.graphics2d.model.World;
import org.metatrans.commons.graphics2d.model.entities.Entity2D_Challenger;
import org.metatrans.commons.graphics2d.model.entities.Entity2D_Ground;
import org.metatrans.commons.graphics2d.model.entities.Entity2D_Moving;
import org.metatrans.commons.graphics2d.model.entities.IEntity2D;

import android.graphics.Bitmap;
import android.graphics.RectF;


public class Entity2D_Challenger_Gravity extends Entity2D_Challenger {
	
	
	private static final long serialVersionUID = 5416967203188382917L;
	
	private static final float MIN_DISTANCE 			= 0.01f;
	private static final float MIN_DISTANCE_POINTER		= 0.01f;
	
	private List<? extends Entity2D_Moving> massObjects;
	private float mass;
	private float max_speed;
	private float massOfPointer;


	public Entity2D_Challenger_Gravity(World _world,
			RectF _evelop,
			List<Entity2D_Ground> _blockerEntities,
			List<? extends IEntity2D> _killerEntities,
			List<? extends Entity2D_Moving> _massObjects,
			float _mass, float _max_speed, float _massOfPointer,
									   int bitmap_id) {

		super(_world, _evelop, _blockerEntities, (List<? extends IEntity2D>) _killerEntities,
				bitmap_id, 0);

		int rotation_step = (int) (15 - (30 * Math.random()));

		setRotationStep(rotation_step);

		massObjects = _massObjects;
		mass = _mass;
		max_speed = _max_speed;
		massOfPointer = _massOfPointer;
		
		setSpeed((Math.random() < 0.5 ? 1f : -1f) *_world.getMaxSpeed_CHALLENGER(), (Math.random() < 0.5 ? 1f : -1f) * _world.getMaxSpeed_CHALLENGER());
	}


	@Override
	public Bitmap getBitmap() {

		IConfigurationSpaceObjects objects_config = ConfigurationUtils_SpaceObjects.getConfigByID(((UserSettings_Gravity) Application_Base.getInstance().getUserSettings()).cfg_id_space_objects);

		bitmap_id = objects_config.getBitmapResourceID();

		return super.getBitmap();
	}


	@Override
	public void nextMoment(float takts) {
		
		float x_accumulated = 0;
		float y_accumulated = 0;

		for (Entity2D_Moving object: massObjects) {
			if (object instanceof Entity2D_Challenger_Gravity) {
				Entity2D_Challenger_Gravity massObject = (Entity2D_Challenger_Gravity)object;
				
				float other_x = massObject.getX() + (massObject.getEnvelop().right - massObject.getEnvelop().left) / 2;
				float other_y = massObject.getY() + (massObject.getEnvelop().bottom - massObject.getEnvelop().top) / 2;
				float my_x = getEnvelop().left + (getEnvelop().right - getEnvelop().left) / 2;
				float my_y = getEnvelop().top + (getEnvelop().bottom - getEnvelop().top) / 2;
				float dx = other_x - my_x;
				float dy = other_y - my_y;
				
				float distance = (float) Math.sqrt(dx * dx + dy * dy);
				
				if (distance > MIN_DISTANCE) {
					
					float sx = (float) (dx / Math.max(MIN_DISTANCE, distance));
					float sy = (float) (dy / Math.max(MIN_DISTANCE, distance));
					
					float acceleration = massObject.mass / Math.max(MIN_DISTANCE, distance * distance);
					x_accumulated += sx * acceleration;
					y_accumulated += sy * acceleration;
				}
			}
		}
		
		Float fx = getWorld().getPointerX();
		Float fy = getWorld().getPointerY();
		if (fx != null && fy != null) {
			float pointer_x = getWorld().getCamera().left + fx;
			float pointer_y = getWorld().getCamera().top + fy;
			float my_x = getEnvelop().left + (getEnvelop().right - getEnvelop().left) / 2;
			float my_y = getEnvelop().top + (getEnvelop().bottom - getEnvelop().top) / 2;
			float dx = pointer_x - my_x;
			float dy = pointer_y - my_y;
			float distance = (float) Math.sqrt(dx * dx + dy * dy);
			if (distance > MIN_DISTANCE_POINTER) {
				float sx = (float) (dx / Math.max(MIN_DISTANCE_POINTER, distance));
				float sy = (float) (dy / Math.max(MIN_DISTANCE_POINTER, distance));
				float acceleration = massOfPointer / Math.max(MIN_DISTANCE_POINTER, distance * distance);
				x_accumulated += sx * acceleration;
				y_accumulated += sy * acceleration;
			}
		}
		
		float speed_x = getDx() * 1f + x_accumulated / 1f;
		float speed_y = getDy() * 1f + y_accumulated / 1f;
		
		if (speed_x < 0) {
			if (speed_x < -max_speed) {
				speed_x = -max_speed;
			}
		} else {
			if (speed_x > max_speed) {
				speed_x = max_speed;
			}
		}
		
		if (speed_y < 0) {
			if (speed_y < -max_speed) {
				speed_y = -max_speed;
			}
		} else {
			if (speed_y > max_speed) {
				speed_y = max_speed;
			}
		}
		
		setSpeed(speed_x, speed_y);
		
		super.nextMoment(takts);
	}
	
	
	@Override
	protected void groundContact_X() {
		setSpeed(-getDx(), getDy());
	}
	
	
	@Override
	protected void groundContact_Y() {
		setSpeed(getDx(), -getDy());
	}
	
	
	@Override
	protected void killed(Entity2D_Moving killer) {
		
		super.killed(killer);
		
		//((GameData_StopTheBalls)Application_Base.getInstance().getGameData()).count_killed_balls++;
	}


	@Override
	protected boolean supportsFeeding() {

		return false;
	}

	
	@Override
	protected World_Gravity getWorld() {

		return (World_Gravity) super.getWorld();
	}
}
