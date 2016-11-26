package org.cyberpwn.cataclysm.object;

import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;
import org.phantomapi.lang.GList;
import org.phantomapi.lang.GSound;
import org.phantomapi.nms.NMSX;
import org.phantomapi.physics.VectorMath;
import org.phantomapi.sync.Task;
import org.phantomapi.sync.TaskLater;
import org.phantomapi.util.FinalDouble;
import org.phantomapi.util.FinalInteger;
import org.phantomapi.util.M;
import org.phantomapi.vfx.ParticleEffect;
import org.phantomapi.world.Area;
import org.phantomapi.world.PhantomWorldQueue;

public class Meteorite
{
	private Location last;
	
	public Meteorite(Location l, Vector d)
	{
		@SuppressWarnings("deprecation")
		Entity e = l.getWorld().spawnFallingBlock(l, Material.OBSIDIAN, (byte) 0);
		last = l;
		
		new Task(0)
		{
			@Override
			public void run()
			{
				if(e == null || e.isDead())
				{
					last.add(d);
					createNovaExplosion(last.clone().add(0, -4, 0), (8 + Math.random() * 7), 5, 0.5 * Math.random(), 0.4, 0.543);
					cancel();
					return;
				}
				
				last = e.getLocation();
				e.getLocation().getWorld().createExplosion(e.getLocation(), 0f);
				d.setY(0);
			}
		};
	}
	
	public static void createNovaExplosion(Location l, double power, int fuse, double random, double blockRatio, double vol)
	{
		GList<Entity> entities = new GList<Entity>();
		Area a = new Area(l.clone(), power);
		FinalInteger push = new FinalInteger(0);
		FinalInteger k = new FinalInteger(0);
		FinalDouble build = new FinalDouble(0);
		Iterator<Block> blocks = a.toCuboid().iterator();
		PhantomWorldQueue q = new PhantomWorldQueue();
		
		while(blocks.hasNext())
		{
			Block b = blocks.next();
			
			if(b.getType().isSolid() && b.getLocation().distance(l.clone()) <= power * ((1.0 - random) + (Math.random() * random)))
			{
				if(M.r(blockRatio))
				{
					if(M.r(0.8))
					{
						if(M.r(0.6))
						{
							b.setType(Material.COBBLESTONE);
						}
						
						else
						{
							b.setType(Material.MOSSY_COBBLESTONE);
						}
					}
					
					else if(M.r(0.3))
					{
						b.setType(Material.COAL_ORE);
					}
					
					else if(M.r(0.7))
					{
						b.setType(Material.IRON_ORE);
					}
					
					else if(M.r(0.04))
					{
						b.setType(Material.IRON_BLOCK);
					}
					
					FallingBlock f = (FallingBlock) NMSX.createFallingBlock(b.getLocation());
					f.setHurtEntities(true);
					f.setDropItem(false);
					
					entities.add(f);
				}
				
				if(!b.getRelative(BlockFace.UP).getType().isSolid())
				{
					b.setType(Material.AIR);
				}
				
				else
				{
					q.set(b.getLocation(), Material.AIR);
				}
			}
		}
		
		q.flush();
		
		for(Entity i : a.getNearbyEntities())
		{
			entities.add(i);
		}
		
		new TaskLater(fuse)
		{
			@Override
			public void run()
			{
				push.set(1);
				
				for(Entity i : entities)
				{
					i.setVelocity(VectorMath.direction(l.clone(), i.getLocation()).multiply(2.6));
				}
				
				ParticleEffect.EXPLOSION_HUGE.display(4f, 4, l, 32);
				new GSound(Sound.EXPLODE, 3.7f, 0.6f).play(l);
				new GSound(Sound.EXPLODE, 3.7f, 0.2f).play(l);
				new GSound(Sound.WITHER_DEATH, 3.7f, 0.5f + (float) build.get()).play(l);
				new GSound(Sound.WITHER_DEATH, 3.7f, 0.566f + (float) build.get()).play(l);
			}
		};
		
		new Task(0)
		{
			@Override
			public void run()
			{
				if(push.get() == 0)
				{
					k.add(1);
					build.set((double) k.get() / (double) fuse);
					
					for(Entity i : entities)
					{
						if(!i.getLocation().getBlock().getType().equals(Material.AIR))
						{
							i.getLocation().getBlock().setType(Material.AIR);
						}
						
						i.setVelocity(i.getVelocity().clone().add(VectorMath.direction(i.getLocation(), l.clone()).multiply(build.get() * vol)));
					}
					
					new GSound(Sound.BAT_TAKEOFF, 2.0f, 0.4f + (float) build.get()).play(l);
					
					ParticleEffect.ENCHANTMENT_TABLE.display((float) (10.0 * build.get()), (int) (20.0 * build.get()), l, 32);
				}
				
				else
				{
					cancel();
				}
			}
		};
	}
}
