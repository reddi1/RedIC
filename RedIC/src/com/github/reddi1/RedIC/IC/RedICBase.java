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

	public static void cancel(SignChangeEvent event, int line, int num) {
		event.setCancelled(true);
		event.getBlock().setTypeId(0);
		ItemStack item = new ItemStack(Material.SIGN, 1);
		event.getPlayer().getInventory().addItem(item);
		String l = "";
		String n = "";

		switch (line) {
		case 2:
			l = "Third";
			break;
		case 3:
			l = "Fourth";
			break;

		}
		switch (num) {
		case 1:
			n = "0";
			break;
		case 2:
			n = "0:0";
			break;
		case 3:
			n = "0:0:0";
			break;
		case 4:
			n = "0:0:0:0";
			break;
		case 5:
			n = "0:0:0:0:0";
			break;
		case 6:
			n = "0:0:0:0:0:0";
			break;
		}
		event.getPlayer().sendMessage(
				ChatColor.RED + l + " line must be in the following Format -> "
						+ n);

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

	public Boolean matchLine(String line, int num) {
		switch (num) {
		case 1:
			return line.matches("\\d+");
		case 2:
			return line.matches("\\d+:\\d+");
		case 3:
			return line.matches("\\d+:\\d+:\\d+");
		case 4:
			return line.matches("\\d+:\\d+:\\d+:\\d+");
		case 5:
			return line.matches("\\d+:\\d+:\\d+:\\d+:\\d+");
		case 6:
			return line.matches("\\d+:\\d+:\\d+:\\d+:\\d+:\\d+");
		}
		return false;
	}

	public int[] lineValues(String line) {
		String[] vals = line.split(":");
		if (!matchLine(line, vals.length))
			return new int[0];

		int[] result = new int[vals.length];
		for (int i = 0; i < vals.length; i++) {
			result[i] = Integer.valueOf(vals[i]);
		}
		return result;
	}
}
