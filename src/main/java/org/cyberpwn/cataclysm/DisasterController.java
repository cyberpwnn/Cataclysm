package org.cyberpwn.cataclysm;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.cyberpwn.disaster.Disaster;
import org.cyberpwn.disasters.DisasterMeteorite;
import org.phantomapi.construct.Controllable;
import org.phantomapi.construct.Controller;
import org.phantomapi.construct.Ticked;
import org.phantomapi.lang.GList;

@Ticked(0)
public class DisasterController extends Controller
{
	private GList<Disaster> disasters;
	
	public DisasterController(Controllable parentController)
	{
		super(parentController);
		
		disasters = new GList<Disaster>();
		
		disasters.add(new DisasterMeteorite(this));
		
		for(Disaster i : disasters)
		{
			register((Controller) i);
		}
	}
	
	@Override
	public void onStart()
	{
		
	}
	
	@Override
	public void onTick()
	{
		for(World i : Bukkit.getWorlds())
		{
			for(Disaster j : disasters)
			{
				if(j.canRun(i))
				{
					j.run(i);
				}
			}
		}
	}
	
	@Override
	public void onStop()
	{
		
	}
}
