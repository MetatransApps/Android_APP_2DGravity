package org.metatrans.apps.gravity.model;


import org.metatrans.apps.gravity.lib.R;
import org.metatrans.commons.app.Application_Base;
import org.metatrans.commons.graphics2d.model.BitmapCache_Base;
import org.metatrans.commons.ui.utils.BitmapUtils;


public class BitmapCache_Gravity extends BitmapCache_Base {


	public static final int BITMAP_ID_LEVEL 					= 11;

	public static final int BITMAP_ID_BLACKHOLE					= 30;

	public static final int BITMAP_ID_ASTEROID_BLUE				= 50;
	public static final int BITMAP_ID_ASTEROID_GREEN			= 70;
	public static final int BITMAP_ID_ASTEROID_GRAY				= 90;
	public static final int BITMAP_ID_BACKGROUND_PURPLE			= 110;
	public static final int BITMAP_ID_BACKGROUND_PING			= 130;
	public static final int BITMAP_ID_BACKGROUND_GREEN			= 150;
	public static final int BITMAP_ID_BACKGROUND_RED			= 170;


	public BitmapCache_Gravity(Integer cache_id) {

		super(cache_id);
	}


	@Override
	public void initBitmaps() {


		System.out.println("!EXPENSIVE OP: RE-INIT BITMAPS OF THE WORLD");


		STATIC.getInstance_Impl(BITMAP_ID_COMMON).add(
				BITMAP_ID_BLACKHOLE,
				BitmapUtils.fromResource(Application_Base.getInstance(), R.drawable.gravity_blackhole_blue_v2));


		STATIC.getInstance_Impl(BITMAP_ID_COMMON).add(
				BITMAP_ID_ASTEROID_BLUE,
				BitmapUtils.fromResource(Application_Base.getInstance(), R.drawable.gravity_asteroid_blue_v2));
		STATIC.getInstance_Impl(BITMAP_ID_COMMON).add(
				BITMAP_ID_ASTEROID_GREEN,
				BitmapUtils.fromResource(Application_Base.getInstance(), R.drawable.gravity_asteroid_green_v2));
		STATIC.getInstance_Impl(BITMAP_ID_COMMON).add(
				BITMAP_ID_ASTEROID_GRAY,
				BitmapUtils.fromResource(Application_Base.getInstance(), R.drawable.gravity_asteroid_gray_v2));


		STATIC.getInstance_Impl(BITMAP_ID_COMMON).add(
				BITMAP_ID_BACKGROUND_PURPLE,
				BitmapUtils.fromResource(Application_Base.getInstance(), R.drawable.gravity_background_purple),
				false);
		STATIC.getInstance_Impl(BITMAP_ID_COMMON).add(
				BITMAP_ID_BACKGROUND_PING,
				BitmapUtils.fromResource(Application_Base.getInstance(), R.drawable.gravity_background_ping),
				false);
		STATIC.getInstance_Impl(BITMAP_ID_COMMON).add(
				BITMAP_ID_BACKGROUND_GREEN,
				BitmapUtils.fromResource(Application_Base.getInstance(), R.drawable.gravity_background_green),
				false);
		STATIC.getInstance_Impl(BITMAP_ID_COMMON).add(
				BITMAP_ID_BACKGROUND_RED,
				BitmapUtils.fromResource(Application_Base.getInstance(), R.drawable.gravity_background_red),
				false);
	}
}
