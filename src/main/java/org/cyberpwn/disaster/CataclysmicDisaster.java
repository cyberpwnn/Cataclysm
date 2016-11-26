package org.cyberpwn.disaster;

import org.bukkit.World;
import org.phantomapi.construct.Controllable;
import org.phantomapi.construct.Controller;

public abstract class CataclysmicDisaster extends Controller implements Disaster
{
	private String name;
	
	public CataclysmicDisaster(Controllable parentController, String name)
	{
		super(parentController);
		
		this.name = name;
	}
	
	@Override
	public abstract boolean canRun(World world);
	
	@Override
	public abstract void run(World world);
	
	@Override
	public abstract void onStart();
	
	@Override
	public abstract void onStop();
	
	@Override
	public String getName()
	{
		return name;
	}
}
