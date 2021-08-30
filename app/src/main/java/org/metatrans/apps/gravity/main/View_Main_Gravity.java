package org.metatrans.apps.gravity.main;


import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import org.metatrans.apps.gravity.menu.Activity_Menu_Main;
import org.metatrans.apps.gravity.model.GameData_Gravity;
import org.metatrans.apps.gravity.model.World_Gravity;
import org.metatrans.commons.app.Application_Base;
import org.metatrans.commons.graphics2d.app.Application_2D_Base;
import org.metatrans.commons.graphics2d.main.Activity_Main_Base2D;
import org.metatrans.commons.graphics2d.ui.View_Main_Base;
import org.metatrans.commons.ui.TextArea;
import org.metatrans.commons.ui.utils.ScreenUtils;


public class View_Main_Gravity extends View_Main_Base {
	
	
	private Paint default_paint;
	
	private RectF rect_level;
	private RectF rect_level_icon;
	private RectF rect_level_text;
	
	private RectF rect_stars;
	private RectF rect_stars_icon;
	private RectF rect_stars_text;
	
	private RectF rect_lives;
	private RectF rect_lives_icon;
	private RectF rect_lives_text;
	
	private TextArea textarea_level;
	private TextArea textarea_lives;
	private TextArea textarea_stars;
	
	
	public View_Main_Gravity(Activity_Main_Base2D activity) {
		
		super(activity);
		
		default_paint = new Paint();
		
		int cell_size = (int) getWorld().getCellSize();
		
		int height = (int) ((8.0f * cell_size) / 10f);
		int start_y = (int) ((1.0f * cell_size) / 10f);
		
		int width = (int) (1.7f * height);
		int width_icon = height;
		int interval_x = cell_size / 3;
		
		float extend_factor_bullets = 1.1f;
		float extend_factor_steps 	= 1.35f;
		
		int[] screen_size = ScreenUtils.getScreenSize((Activity) getContext());
		int screen_width = Math.max(screen_size[0], screen_size[1]);
		int start_x = (int) ((screen_width - 5 * interval_x - 4 * width - extend_factor_bullets * width - extend_factor_steps * width) / 2f);
		
		int border_icon = start_y;
		
		rect_level			= new RectF(start_x, 										start_y, start_x + width, 															start_y + height);
		rect_level_icon		= new RectF(start_x + border_icon, 							start_y + border_icon, start_x + width_icon - border_icon, 							start_y + height - border_icon);
		rect_level_text		= new RectF(rect_level_icon.right, 							start_y, rect_level.right, 															start_y + height);
		
		rect_lives 			= new RectF(rect_level.right + interval_x, 					start_y, rect_level.right + 4 * interval_x + width, 									start_y + height);
		rect_lives_icon 	= new RectF(rect_level.right + interval_x + border_icon, 	start_y + border_icon, rect_level.right + interval_x + width_icon - border_icon, 	start_y + height - border_icon);
		rect_lives_text		= new RectF(rect_lives_icon.right, 							start_y, rect_lives.right, 															start_y + height);
		
		rect_stars 			= new RectF(rect_lives.right + interval_x, 				start_y, rect_lives.right + 3 * interval_x + extend_factor_steps * width,				start_y + height);
		rect_stars_icon		= new RectF(rect_lives.right + interval_x + border_icon,	start_y + border_icon, rect_lives.right + interval_x + width_icon - border_icon, 	start_y + height - border_icon);
		rect_stars_text		= new RectF(rect_stars_icon.right,						 	start_y, rect_stars.right,															start_y + height);
		
		textarea_level 		= new TextArea(rect_level_text, 	"00 ", 		Color.GREEN);
		textarea_lives 		= new TextArea(rect_lives_text, 	"x 000000 ", 	Color.GREEN);
		textarea_stars 		= new TextArea(rect_stars_text, 	"x 000000 ", 	Color.GREEN);
	}
	
	
	@Override
	public Class getMainMenuClass() {
		return Activity_Menu_Main.class;
	}
	
	
	@Override
	public void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		
		default_paint.setColor(Color.BLACK);
		
		canvas.drawRect(rect_level, default_paint);
		canvas.drawRect(rect_lives, default_paint);
		canvas.drawRect(rect_stars, default_paint);
		
		int level = Application_Base.getInstance().getUserSettings().modeID;
		canvas.drawBitmap(World_Gravity.getBitmap_level(), null, rect_level_icon, default_paint);
		textarea_level.setColour_Text(Color.GREEN);
		textarea_level.setText("" + level + " ");
		textarea_level.draw(canvas);
		
		int stars = ((GameData_Gravity)Application_Base.getInstance().getGameData()).count_objects;
		canvas.drawBitmap(World_Gravity.getBitmap_balls(), null, rect_stars_icon, default_paint);
		textarea_stars.setColour_Text(Color.GREEN);
		textarea_stars.setText("x " + stars + " ");
		textarea_stars.draw(canvas);
		
		int lives = Application_2D_Base.getInstance().getGameData().count_lives;
		canvas.drawBitmap(World_Gravity.getBitmap_lives(), null, rect_lives_icon, default_paint);
		textarea_lives.setColour_Text(Color.GREEN);
		textarea_lives.setText("x " + lives + " ");
		textarea_lives.draw(canvas);
	}
	
	
	protected World_Gravity getWorld() {
		return (World_Gravity) super.getWorld();
	}
	
	
	@Override
	protected void processEvent_DOWN(MotionEvent event) {
		
		super.processEvent_DOWN(event);
		
		if (!Application_2D_Base.getInstance().isCurrentlyGameActiveIntoTheMainScreen()) {
			return;
		}
		
		float x = event.getX(event.getActionIndex());
		float y = event.getY(event.getActionIndex());
		
		getWorld().setPointer(x, y);
	}
	
	
	@Override
	protected void processEvent_MOVE(MotionEvent event) {
		
		super.processEvent_MOVE(event);
		
		if (!Application_2D_Base.getInstance().isCurrentlyGameActiveIntoTheMainScreen()) {
			return;
		}
		
		float x = event.getX();
		float y = event.getY();
		
		getWorld().setPointer(x, y);
	}
	
	
	@Override
	protected void processEvent_UP(MotionEvent event) {
		
		super.processEvent_UP(event);
		
		if (!Application_2D_Base.getInstance().isCurrentlyGameActiveIntoTheMainScreen()) {
			return;
		}
		
		getWorld().setPointer(null, null);
	}
}
