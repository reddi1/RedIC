package com.github.reddi1.RedIC;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.SignChangeEvent;

import com.github.reddi1.RedIC.IC.*;

public class RedICListener implements Listener {

	public static RedIC plugin;

	private static Pattern rcPattern = Pattern.compile("^\\[RC(\\d+)\\]$",
			Pattern.CASE_INSENSITIVE);

	// Locations to check after a redstone event
	private int[][] redLocs = { { 1, 0, 0 }, { -1, 0, 0 }, { 0, 0, 1 },
			{ 0, 0, -1 }, { 0, 1, 0 } };

	public RedICListener(RedIC instance) {
		plugin = instance;
	}

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		Matcher matcher = rcPattern.matcher(event.getLine(1));
		if (matcher.find()) {
			int rc = Integer.parseInt(matcher.group(1));

			switch (rc) {
			case 1000:
				new RC1000(plugin, event);
				break;
			case 1001:
				new RC1001(plugin, event);
				break;
			case 1002:
				new RC1002(plugin, event);
				break;
			}
		}

	}

	@EventHandler
	public void onBlockRedstoneChange(BlockRedstoneEvent event) {
		// TODO: perhaps check for redstone not facing the IC?

		int oldCurrent = event.getOldCurrent();
		int newCurrent = event.getNewCurrent();

		// no change in on/off state
		if ((oldCurrent == 0 && newCurrent == 0)
				|| (oldCurrent > 0 && newCurrent > 0))
			return;

		Block block = event.getBlock();
		Location loc = block.getLocation();
		loc.setY(loc.getY() + 1);

		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();

		for (int[] rLoc : redLocs) {
			if (block.getWorld()
					.getBlockAt(x + rLoc[0], y + rLoc[1], z + rLoc[2])
					.getType().equals(Material.WALL_SIGN)) {
				redICRedstoneEvent(block.getWorld(), x + rLoc[0], y + rLoc[1],
						z + rLoc[2], event);
			}
		}
	}

	public void redICRedstoneEvent(World world, int x, int y, int z,
			BlockRedstoneEvent event) {
		Sign sign = (Sign) world.getBlockAt(x, y, z).getState();
		int newCurrent = event.getNewCurrent();
		Matcher matcher = rcPattern.matcher(sign.getLine(1));
		if (matcher.find()) {
			int rc = Integer.parseInt(matcher.group(1));
			switch (rc) {
			case 1000:
				RC1000.activate(sign, newCurrent, event);
				break;
			case 1001:
				RC1001.activate(sign, newCurrent, event);
				break;
			case 1002:
				RC1002.activate(sign, newCurrent, event);
				break;
			}
		}

	}

}
