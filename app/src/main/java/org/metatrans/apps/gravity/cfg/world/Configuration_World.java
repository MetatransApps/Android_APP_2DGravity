package org.metatrans.apps.gravity.cfg.world;


import org.metatrans.apps.gravity.lib.R;
import org.metatrans.commons.app.Application_Base;


public class Configuration_World extends Configuration_World_Base {
	
	
	public Configuration_World(int id, float maxSpeed, float spaceMultiplier) {
		
		super(id, maxSpeed, spaceMultiplier);
	}
	
	
	private String getName(float spaceMultiplier) {
		
		String name = Application_Base.getInstance().getString(R.string.level) + " " + getID();
		
		//int starsCount = WorldGenerator_Gravity.getObjectsCount();
		
		//name += " (" + starsCount + " " + Application_Base.getInstance().getString(R.string.balls) + ")";
		name += " (" + Application_Base.getInstance().getString(R.string.speed) + " " + getMaxSpeed() + " km/s, "
					+ Application_Base.getInstance().getString(R.string.space) + " " + getSpaceMultiplier() + ")";
		
		return name;
	}
	
	
	@Override
	public int getName() {
		throw new UnsupportedOperationException();
	}
	
	
	@Override
	public int getIconResID() {
		return R.drawable.ic_star_gold;
	}
	
	
	@Override
	public int getDescription() {
		throw new UnsupportedOperationException();
	}


	@Override
	public String getName_String() {	
		return getName(spaceMultiplier);
	}


	@Override
	public String getDescription_String() {
		return "";
	}
}
