package org.metatrans.apps.gravity.model;


import java.util.ArrayList;

import org.metatrans.apps.gravity.cfg.world.IConfigurationWorld;
import org.metatrans.apps.gravity.model.entities.Entity2D_Challenger_Gravity;
import org.metatrans.apps.gravity.model.entities.Entity2D_Player_Gravity;
import org.metatrans.commons.app.Application_Base;
import org.metatrans.commons.cfg.colours.ConfigurationUtils_Colours;
import org.metatrans.commons.graphics2d.model.World;
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
		
		float spaceScaleFactor = cfg.getSpaceMultiplier();
		
		int[] screen_size = ScreenUtils.getScreenSize(activity);
		int main_width = (int) (1 * Math.max(screen_size[0], screen_size[1]));
		int main_height = (int) (1 * Math.min(screen_size[0], screen_size[1]));
		
		int cell_size = main_width / 17;

		World_Gravity world = new World_Gravity(activity, (int) (spaceScaleFactor * main_width), (int) (spaceScaleFactor * main_height));

		world.setCellSize(cell_size);//Buttons size

		float CELL_MULT = 5f;

		world.addEntity(new Entity2D_Player_Gravity(world,
				new RectF(main_width - CELL_MULT * cell_size,
						main_height - CELL_MULT * cell_size,
						main_width + CELL_MULT * cell_size,
						main_height + CELL_MULT * cell_size
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
			
			float radius = (int) (cell_size / 1.5f);
			RectF rect = new RectF(x, y, x + 2 * radius, y + 2 * radius);

			world.addEntity(new Entity2D_Challenger_Gravity(world,
							rect,
							world.getGroundEntities(), world.getKillersEntities_forChallengers(),
							world.getPlayerEntity().getEnvelop().left, world.getPlayerEntity().getEnvelop().top,
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

	