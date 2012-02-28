package com.github.reddi1.RedIC.IC;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;

import com.github.reddi1.RedIC.RedIC;

public class RC1001 extends RedICBase {

	public static String name = "COPY";
	public static String ic = "[RC1001]";
	public static int valsInLineThree = 6;
	public static int valsInLineFour = 3;

	public RC1001(RedIC plugin, SignChangeEvent event) {
		super(plugin, event);
	}

	public static void activate(Sign sign, Boolean powered) {

		if (!checkSignValid(sign, valsInLineThree, valsInLineFour))
			return;

		int[] locs = lineValues(sign.getLine(2));
		int xOffset = locs[0];
		int yOffset = locs[1];
		int zOffset = locs[2];
		int width = locs[3];
		int length = locs[4];
		int height = locs[5];

		int[] to = lineValues(sign.getLine(3));
		int toX = to[0];
		int toY = to[1];
		int toZ = to[2];

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
					if (powered && fromBlock.getType().equals(Material.WOOL)) {
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

	@Override
	public int getValsInLineThree() {
		return valsInLineThree;
	}

	@Override
	public int getValsInLineFour() {
		return valsInLineFour;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getIC() {
		return ic;
	}
}
