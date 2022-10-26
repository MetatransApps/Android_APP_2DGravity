package org.metatrans.apps.gravity.model.entities;


import android.graphics.Bitmap;
import android.graphics.RectF;

import org.metatrans.apps.gravity.lib.R;
import org.metatrans.apps.gravity.model.World_Gravity;
import org.metatrans.commons.app.Application_Base;
import org.metatrans.commons.graphics2d.model.IWorld;
import org.metatrans.commons.graphics2d.model.entities.Entity2D_Ground;
import org.metatrans.commons.graphics2d.model.entities.IEntity2D;
import org.metatrans.commons.ui.utils.BitmapUtils;


public class Entity2D_Terrain_Gravity extends Entity2D_Ground {


    private static final long serialVersionUID = -434579107317800391L;


    private transient Bitmap bitmap_background_org = null;
    private transient Bitmap bitmap_background = null;


    public Entity2D_Terrain_Gravity(IWorld world, RectF _evelop) {
        super(world, _evelop, IEntity2D.SUBTYPE_GROUND_EMPTY, 0, 0);
    }


    @Override
    public Bitmap getBitmap() {

        Bitmap latest = ((World_Gravity)world).getBitmap_background();

        if (bitmap_background_org != latest) {

            bitmap_background_org = latest;

            bitmap_background = BitmapUtils.createScaledBitmap(bitmap_background_org,
                    (int) (getEnvelop_ForDraw().right - getEnvelop_ForDraw().left),
                    (int) (getEnvelop_ForDraw().bottom - getEnvelop_ForDraw().top));
        }

        return bitmap_background;
    }




    /*protected boolean getFlag1() {

        return true;
    }*/


    @Override
    public int getBackgroundColour() {

        return Application_Base.getInstance().getColoursCfg().getColour_Square_White();
    }


    @Override
    public int getBitmapTransparency() {

        //return 255;
        return (int) 199.111110912;
        //return (int) 215.333;
    }
}
