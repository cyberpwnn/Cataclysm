package org.cyberpwn.disasters;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.cyberpwn.cataclysm.object.Meteorite;
import org.cyberpwn.disaster.CataclysmicDisaster;
import org.phantomapi.construct.Controllable;
import org.phantomapi.util.Chunks;
import org.phantomapi.util.M;

public class DisasterMeteorite extends CataclysmicDisaster
{
	public DisasterMeteorite(Controllable parentController)
	{
		super(parentController, "Meteorite");
	}
	
	@Override
	public boolean canRun(World world)
	{
		if(Chunks.getLoadedChunks(world).size() > 0)
		{
			if(world.getTime() > 14000)
			{
				return M.r(0.2);
			}
		}
		
		return false;
	}
	
	@Override
	public void run(World world)
	{
		Chunk c = Chunks.getLoadedChunks(world).pickRandom();
		Location l = c.getBlock((int) (Math.random() * 15), 255, (int) (Math.random() * 15)).getLocation();
		Vector d = new Vector((Math.random() * 2) - 1, -0.6, (Math.random() * 2) - 1);
		new Meteorite(l, d);
	}
	
	@Override
	public void onStart()
	{
		
	}
	
	@Override
	public void onStop()
	{
		
	}
}
