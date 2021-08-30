package org.metatransapps.apps.gravity.model;


import java.util.ArrayList;

import org.metatransapps.apps.gravity.cfg.world.IConfigurationWorld;
import org.metatransapps.apps.gravity.model.entities.Entity2D_Challenger_Gravity;
import org.metatransapps.apps.gravity.model.entities.Entity2D_Player_Gravity;
import org.metatransapps.commons.app.Application_Base;
import org.metatransapps.commons.cfg.colours.ConfigurationUtils_Colours;
import org.metatransapps.commons.graphics2d.model.World;
import org.metatransapps.commons.graphics2d.model.entities.IEntity2D;
import org.metatransapps.commons.ui.utils.ScreenUtils;

import android.content.Context;
import android.graphics.RectF;


public class WorldGenerator_Gravity {
	
	
	public static int getObjectsCount() {
		return 137;
	}
	
	
	public static World generate(Context activity, IConfigurationWorld cfg) {
		
		System.out.println("GAMEDATA GENERATION");
		
		float spaceScaleFactor = cfg.getSpaceMultiplier();
		
		World_Gravity world = new World_Gravity(activity);
		
		int[] screen_size = ScreenUtils.getScreenSize(activity);
		int main_width = (int) (1 * Math.max(screen_size[0], screen_size[1]));
		int main_height = (int) (1 * Math.min(screen_size[0], screen_size[1]));
		
		int cell_size = main_width / 17;
		
		world.setCellSize(cell_size);//Buttons size
		
		world.addEntity(new Entity2D_Player_Gravity(world,
				new RectF(spaceScaleFactor / 2 * main_width - cell_size / 2,
							spaceScaleFactor / 2 * main_height - cell_size / 2,
							spaceScaleFactor / 2 * main_width + cell_size / 2,
							spaceScaleFactor / 2 * main_height + cell_size / 2
						),
						new ArrayList<IEntity2D>(),
						ConfigurationUtils_Colours.getConfigByID(Application_Base.getInstance().getUserSettings().uiColoursID).getColour_Square_ValidSelection()));
		
		/*RectF rect_screen = new RectF(scaleFactor / 2 * main_width - main_width / 2,
				scaleFactor / 2 * main_height - main_height / 2,
				scaleFactor / 2 * main_width + main_width / 2,
				scaleFactor / 2 * main_height + main_height / 2);
		*/
		
		//Random rnd = new Random();
		float MAX_MASS = cfg.getSpaceMultiplier() * 2500;
		
		for (int i = 0; i < getObjectsCount(); i++) {
			
			int x = (int) (Math.random() * spaceScaleFactor * main_width);
			int y = (int) (Math.random() * spaceScaleFactor * main_height); 
			
			float mass = MAX_MASS;//Math.max(1, rnd.nextInt(MAX_MASS));
			
			float radius = Math.max(cell_size / 7, mass / 10);
			radius = Math.min(cell_size / 6, radius);
			RectF rect = new RectF(x, y, x + 2 * radius, y + 2 * radius);
			
			//int color = ConfigurationUtils_Colours.getConfigByID(Application_Base.getInstance().getUserSettings().uiColoursID).getColour_Square_White(); 
			//Color.WHITE;//argb(255, 256 - mass * 256 / MAX_MASS, 256 - mass * 256 / MAX_MASS, 256 - mass * 256 / MAX_MASS);
			
			world.addEntity(new Entity2D_Challenger_Gravity(world,
							rect,
							world.getGroundEntities(), world.getKillersEntities_forChallengers(),
							world.getPlayerEntity().getEvelop().left, world.getPlayerEntity().getEvelop().top,
							world.getMovingEntities(),
							mass,
							cfg.getMaxSpeed(),
							cfg.getSpaceMultiplier() * 1000000
					)
			);
		}
		
		return world;
	}
}

	