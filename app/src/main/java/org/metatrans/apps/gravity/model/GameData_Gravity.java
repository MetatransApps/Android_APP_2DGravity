package org.metatrans.apps.gravity.model;


public class GameData_Gravity extends org.metatrans.commons.graphics2d.model.GameData {
	
	
	private static final long serialVersionUID = -1112210300081303255L;
	
	
	public int count_objects;
	public int total_count_objects;
	
	
	public GameData_Gravity() {

		super();

		count_lives = 15 * 60 * 1000 / 24;// / 20;
	}
}
