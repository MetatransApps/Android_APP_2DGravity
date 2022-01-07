package org.metatrans.apps.gravity.cfg.world;


public abstract class Configuration_World_Base implements IConfigurationWorld {
	
	
	private int id;
	private float maxSpeed;
	protected float spaceMultiplier;
	
	
	public Configuration_World_Base(int _id, float _maxSpeed, float _spaceMultiplier) {
		id = _id;
		maxSpeed = _maxSpeed;
		spaceMultiplier = _spaceMultiplier;
	}
	
	
	@Override
	public int getID() {
		return id;
	}
	
	
	@Override
	public float getMaxSpeed() {
		return maxSpeed;
	}
	
	
	@Override
	public float getSpaceMultiplier() {
		return spaceMultiplier;
	}
}
