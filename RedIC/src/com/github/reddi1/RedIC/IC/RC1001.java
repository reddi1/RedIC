package com.github.reddi1.RedIC.IC;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.SignChangeEvent;

import com.github.reddi1.RedIC.RedIC;

public class RC1001 extends RedICBase {

	private String name = "COPY";
	private String ic = "[RC1001]";

	public RC1001(RedIC plugin, SignChangeEvent event) {
		super(plugin, event);
		Player player = event.getPlayer();

		if (!matchLine(event.getLine(3), 3)) {
			cancel(event, 3, 3);
			return;
		}
		if (!matchLine(event.getLine(2), 6)) {
			cancel(event, 2, 6);
			return;
		}
		event.setLine(0, name);
		event.setLine(1, ic);

		player.sendMessage(ChatColor.GREEN + "RedIC " + name + " created");
	}

	public static void activate(Sign sign, int current, BlockRedstoneEvent event) {
		Boolean stateOn = false;
		if (current > 0)
			stateOn = true;

		String[] dmg = sign.getLine(3).split(":");
		int toX = Integer.valueOf(dmg[0]);
		int toY = Integer.valueOf(dmg[1]);
		int toZ = Integer.valueOf(dmg[2]);

		String[] locs = sign.getLine(2).split(":");
		int xOffset = Integer.valueOf(locs[0]);
		int yOffset = Integer.valueOf(locs[1]);
		int zOffset = Integer.valueOf(locs[2]);
		int width = Integer.valueOf(locs[3]);
		int length = Integer.valueOf(locs[4]);
		int height = Integer.valueOf(locs[5]);

		World w = sign.getWorld();

		Location loc = getICPos(sign);
		Location startLoc = new Location(loc.getWorld(), loc.getX() + xOffset,
				loc.getY() + yOffset, loc.getZ() + zOffset);
		Location toLoc = new Location(loc.getWorld(), loc.getX() + toX,
				loc.getY() + toY, loc.getZ() + toZ);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < length; z++) {
					Location currentFromLocation = new Location(
							startLoc.getWorld(), startLoc.getX() + x,
							startLoc.getY() + y, startLoc.getZ() + z);
					Location currentToLocation = new Location(toLoc.getWorld(),
							toLoc.getX() + x, toLoc.getY() + y, toLoc.getZ()
							+ z);
					Block fromBlock = w.getBlockAt(currentFromLocation);
					Block toBlock = w.getBlockAt(currentToLocation);
					if (stateOn && fromBlock.getType().equals(Material.WOOL)) {
						byte data = fromBlock.getData();
						toBlock.setType(Material.WOOL);
						toBlock.setData(data);

					} else {
						byte data = 0;
						toBlock.setType(Material.AIR);
						toBlock.setData(data);
					}
				}
			}
		}

	}
}
