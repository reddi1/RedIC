package com.github.reddi1.RedIC.IC;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.Event;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

import com.github.reddi1.RedIC.RedIC;


public class RedICBase {

	public static RedIC plugin;

	public RedICBase(RedIC redICPlugin, Event event) {
		plugin = redICPlugin;
	}

	public static void cancel(SignChangeEvent event, String msg) {
		event.setCancelled(true);
		event.getBlock().setTypeId(0);
		ItemStack item = new ItemStack(Material.SIGN, 1);
		event.getPlayer().getInventory().addItem(item);
		event.getPlayer().sendMessage(ChatColor.RED + msg);

	}

	public static Location getICPos(Sign sign) {
		Location loc = sign.getBlock().getLocation();
		switch (sign.getRawData()) {
		case 0x2:
			loc.setZ(loc.getZ() + 1);
			break;
		case 0x3:
			loc.setZ(loc.getZ() - 1);
			break;
		case 0x4:
			loc.setX(loc.getX() + 1);
			break;
		case 0x5:
			loc.setX(loc.getX() - 1);
			break;
		}
		return loc;
	}
}
