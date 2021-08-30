package org.metatrans.apps.gravity.loading;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import org.metatrans.apps.gravity.lib.R;
import org.metatrans.commons.app.Application_Base;
import org.metatrans.commons.cfg.colours.ConfigurationUtils_Colours;
import org.metatrans.commons.loading.View_Loading_Base;
import org.metatrans.commons.model.UserSettings_Base;
import org.metatrans.commons.ui.utils.BitmapUtils;


public class View_Loading extends View_Loading_Base {
	
	
	private Bitmap[] bitmap_commons;
	private Bitmap background_image;
	
	
	public View_Loading(Context context, UserSettings_Base _settings) {
		
		super(context);
		
		//background_image = BitmapUtils.fromResource(getContext(), R.drawable.ic_logo_balls);
	}
	
	
	@Override
	protected Bitmap getBitmapBackground() {
		return background_image;
	}
	
	
	@Override
	protected Bitmap[] getCommonBitmaps() {
		return bitmap_commons;
	}
	
	
	@Override
	protected void initPiecesBitmaps() {
		
		bitmap_commons = new Bitmap[] {
				getImageBitmap(R.drawable.ic_logo_gravity_transparent),
				getImageBitmap(R.drawable.ic_logo_gravity_transparent),
		};
		
		
		Bitmap[] bitmap_others = new Bitmap[137];
		
		
		for (int i=0; i< bitmap_others.length; i++) {
			
			bitmap_others[i] = createOval((int) getSquareSize() / 4, (int) getSquareSize() / 4);
			createEntry(bitmap_others[i]);
			
		}
	}
	
	
	private static Bitmap createOval(int w, int h) {
		
	    Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
	    Canvas c = new Canvas(bm);
	    Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

	    p.setColor(ConfigurationUtils_Colours.getConfigByID(Application_Base.getInstance().getUserSettings().uiColoursID).getColour_Square_White());
	    c.drawOval(new RectF(0, 0, w * 3 / 4, h * 3 / 4), p);
	    
	    return bm;
	}
	
	
	protected Bitmap getImageBitmap(int imageResID) {
		return BitmapUtils.fromResource(getContext(), imageResID, (int) getSquareSize());
	}
}
