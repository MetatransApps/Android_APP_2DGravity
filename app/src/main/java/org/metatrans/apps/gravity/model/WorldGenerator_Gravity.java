package org.metatrans.apps.gravity.model;


import java.util.ArrayList;

import org.metatrans.apps.gravity.cfg.world.IConfigurationWorld;
import org.metatrans.apps.gravity.menu.ConfigurationUtils_SpaceObjects;
import org.metatrans.apps.gravity.menu.IConfigurationSpaceObjects;
import org.metatrans.apps.gravity.model.entities.Entity2D_Challenger_Gravity;
import org.metatrans.apps.gravity.model.entities.Entity2D_Terrain_Gravity;
import org.metatrans.apps.gravity.model.entities.Entity2D_Player_Gravity;
import org.metatrans.commons.app.Application_Base;
import org.metatrans.commons.cfg.colours.ConfigurationUtils_Colours;
import org.metatrans.commons.graphics2d.model.World;
import org.metatrans.commons.graphics2d.model.entities.Entity2D_Moving;
import org.metatrans.commons.graphics2d.model.entities.IEntity2D;
import org.metatrans.commons.ui.utils.ScreenUtils;

import android.content.Context;
import android.graphics.RectF;


public class WorldGenerator_Gravity {
	
	
	public static int getObjectsCount() {
		return 137; //137;
	}
	
	
	public static World generate(Context activity, IConfigurationWorld cfg) {
		
		System.out.println("GAMEDATA GENERATION");

		IConfigurationSpaceObjects objects_config = ConfigurationUtils_SpaceObjects.getConfigByID(((UserSettings_Gravity) Application_Base.getInstance().getUserSettings()).cfg_id_space_objects);

		float spaceScaleFactor = cfg.getSpaceMultiplier();
		
		int[] screen_size = ScreenUtils.getScreenSize(activity);
		int main_width = 1 * Math.max(screen_size[0], screen_size[1]);
		int main_height = 1 * Math.min(screen_size[0], screen_size[1]);
		
		int cell_size = main_width / 17;

		World_Gravity world = new World_Gravity(activity, 1, 1);

		world.setCellSize(cell_size); //Buttons size


		float MAX_MASS = cfg.getSpaceMultiplier() * 2500;
		
		for (int i = 0; i < getObjectsCount(); i++) {

			float asteroids_size = 1f * cell_size;

			int x = (int) (spaceScaleFactor * Math.random() * main_width - asteroids_size);
			int y = (int) (spaceScaleFactor * Math.random() * main_height - asteroids_size);

			x = (int) Math.max(asteroids_size, x);
			y = (int) Math.max(asteroids_size, y);

			float mass = MAX_MASS;//Math.max(1, rnd.nextInt(MAX_MASS));
			
			float radius = (int) ((138f / 256f) * cell_size / (1.5f));

			RectF rect = new RectF(x, y, x + 2 * radius, y + 2 * radius);

			world.addEntity(

					new Entity2D_Challenger_Gravity(
							world,
							rect,
							world.getGroundEntities(),
							world.getKillersEntities_forChallengers(),
							world.getMovingEntities(),
							mass,
							cfg.getMaxSpeed(),
							cfg.getSpaceMultiplier() * 1000000,
							objects_config.getBitmapResourceID()
					)
			);
		}


		float player_scale = 10f;

		world.addEntity(

				new Entity2D_Player_Gravity(

						world,
						new RectF(
								spaceScaleFactor * main_width / 2 - player_scale * cell_size / 2,
								spaceScaleFactor * main_height / 2 - player_scale * cell_size / 2,
								spaceScaleFactor * main_width / 2 + player_scale * cell_size / 2,
								spaceScaleFactor * main_height / 2 + player_scale * cell_size / 2
						),

						new ArrayList<IEntity2D>()
				)
		);


		world.addEntity(

				new Entity2D_Terrain_Gravity(

						world,
						/*new RectF(
								spaceScaleFactor * main_width / 2 - main_width / 2,
								spaceScaleFactor * main_height / 2 - main_height / 2,
								spaceScaleFactor * main_width / 2 + main_width / 2,
								spaceScaleFactor * main_height / 2 + main_height / 2
						)*/
						world.getCamera()
				)
		);


		for (Entity2D_Moving moving: world.getMovingEntities()) {

			moving.setWorldSize(world.get_WORLD_SIZE_X(), world.get_WORLD_SIZE_Y());
		}


		return world;
	}
}

	