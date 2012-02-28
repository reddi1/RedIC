package com.github.reddi1.RedIC.IC;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;

import com.github.reddi1.RedIC.RedIC;

public class RC1000 extends RedICBase {

	public static String name = "TOGGLE";
	public static String ic = "[RC1000]";
	public static int valsInLineThree = 2;
	public static int valsInLineFour = 6;

	public RC1000(RedIC plugin, SignChangeEvent event) {
		super(plugin, event);
	}

	public static void activate(Sign sign, boolean powered) {

		if (!checkSignValid(sign, valsInLineThree, valsInLineFour))
			return;

		int[] dmg = lineValues(sign.getLine(2));
		int damageValueOn = dmg[0];
		int damageValueOff = dmg[1];

		int[] locs = lineValues(sign.getLine(3));
		int xOffset = locs[0];
		int yOffset = locs[1];
		int zOffset = locs[2];
		int width = locs[3];
		int length = locs[4];
		int height = locs[5];

		World w = sign.getWorld();

		Location loc = getICPos(sign);
		Location startLoc = new Location(loc.getWorld(), loc.getX() + xOffset,
				loc.getY() + yOffset, loc.getZ() + zOffset);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < length; z++) {
					Location currentLocation = new Location(
							startLoc.getWorld(), startLoc.getX() + x,
							startLoc.getY() + y, startLoc.getZ() + z);
					Block block = w.getBlockAt(currentLocation);
					if (block.getType().equals(Material.WOOL)) {
						if (powered) {
							block.setData((byte) damageValueOn);
						} else {
							block.setData((byte) damageValueOff);
						}
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
