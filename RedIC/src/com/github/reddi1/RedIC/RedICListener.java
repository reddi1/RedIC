package com.github.reddi1.RedIC;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.SignChangeEvent;

import com.github.reddi1.RedIC.IC.RedIC1000;

public class RedICListener implements Listener{
	
	public static RedIC plugin;
	
	public RedICListener(RedIC instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		String[] lines = event.getLines();
		if (lines[1].equalsIgnoreCase("[RedIC1000]")) {
			new RedIC1000(plugin,event);
		}
		
	}
	
	@EventHandler
	public void onBlockRedstoneChange(BlockRedstoneEvent event) {
		Block block = event.getBlock();
		Location loc = block.getLocation();
		loc.setY(loc.getY() + 1);
		
		int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        
		if(block.getWorld().getBlockAt(x+1,y,z).getType().equals(Material.WALL_SIGN)) {
			redICRedstoneEvent(block.getWorld(), x+1,y,z, event.getNewCurrent(), event);

		} else if(block.getWorld().getBlockAt(x-1,y,z).getType().equals(Material.WALL_SIGN)) {
			redICRedstoneEvent(block.getWorld(), x-1,y,z, event.getNewCurrent(), event);

		} else if(block.getWorld().getBlockAt(x,y,z+1).getType().equals(Material.WALL_SIGN)) {
			redICRedstoneEvent(block.getWorld(), x,y,z+1, event.getNewCurrent(), event);

		} else if(block.getWorld().getBlockAt(x,y,z-1).getType().equals(Material.WALL_SIGN)) {
			redICRedstoneEvent(block.getWorld(), x,y,z-1, event.getNewCurrent(), event);

		} else if(block.getWorld().getBlockAt(x,y+1,z).getType().equals(Material.WALL_SIGN)) {
			redICRedstoneEvent(block.getWorld(), x,y+1,z, event.getNewCurrent(), event);

		} 
				
	}

	
	public void redICRedstoneEvent(World world, int x, int y, int z,	int newCurrent, BlockRedstoneEvent event) {
		Sign sign = (Sign) world.getBlockAt(x,y,z).getState();
		if (sign.getLine(1).equalsIgnoreCase("[RedIC1000]")) {
			RedIC1000.activate(sign, newCurrent, event);
		}
		
	}


}
