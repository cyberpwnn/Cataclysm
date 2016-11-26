package org.cyberpwn.disaster;

import org.bukkit.World;

public interface Disaster
{
	public boolean canRun(World world);
	
	public void run(World world);
	
	public String getName();
}
