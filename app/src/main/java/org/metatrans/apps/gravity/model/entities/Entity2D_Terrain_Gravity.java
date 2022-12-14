package org.metatrans.apps.gravity.model.entities;


import android.graphics.Bitmap;
import android.graphics.RectF;

import org.metatrans.apps.gravity.menu.ConfigurationUtils_SpaceObjects;
import org.metatrans.apps.gravity.menu.IConfigurationSpaceObjects;
import org.metatrans.apps.gravity.model.UserSettings_Gravity;
import org.metatrans.commons.app.Application_Base;
import org.metatrans.commons.graphics2d.model.IWorld;
import org.metatrans.commons.graphics2d.model.entities.Entity2D_Ground;
import org.metatrans.commons.graphics2d.model.entities.IEntity2D;
import org.metatrans.commons.ui.utils.BitmapUtils;


public class Entity2D_Terrain_Gravity extends Entity2D_Ground {


    private static final long serialVersionUID = -434579107317800391L;


    private transient Bitmap bitmap_background;
    private transient Bitmap bitmap_latest_backup;


    public Entity2D_Terrain_Gravity(IWorld world) {

        super(world, null, IEntity2D.SUBTYPE_GROUND_EMPTY, 0, 0);
    }


    @Override
    public RectF getEnvelop() {

        return getWorld().getCamera();
    }


    @Override
    public Bitmap getBitmap() {


        UserSettings_Gravity userSettings_gravity = (UserSettings_Gravity) Application_Base.getInstance().getUserSettings();

        IConfigurationSpaceObjects objects_config = ConfigurationUtils_SpaceObjects.getConfigByID(userSettings_gravity.cfg_id_space_objects);


        Bitmap latest = getWorld().getBitmapCache().get(objects_config.getBitmapResourceID_Background());

        if (bitmap_latest_backup != latest) {

            bitmap_latest_backup = latest;

            bitmap_background = BitmapUtils.createScaledBitmap(bitmap_latest_backup,
                    (int) (getEnvelop_ForDraw().right - getEnvelop_ForDraw().left),
                    (int) (getEnvelop_ForDraw().bottom - getEnvelop_ForDraw().top));
        }


        return bitmap_background;
    }


    @Override
    public int getBackgroundColour() {

        return Application_Base.getInstance().getColoursCfg().getColour_Square_White();
    }
}
