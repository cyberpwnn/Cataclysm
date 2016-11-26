package org.cyberpwn.cataclysm;

import org.phantomapi.construct.Ghost;

public class Cataclysm extends Ghost
{
	private DisasterController disasterController;
	
	@Override
	public void preStart()
	{
		disasterController = new DisasterController(this);
		
		register(disasterController);
	}
	
	@Override
	public void onStart()
	{
		
	}
	
	@Override
	public void onStop()
	{
		
	}
	
	@Override
	public void postStop()
	{
		
	}
}
