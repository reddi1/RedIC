package com.github.reddi1.RedIC;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;


public class RedIC extends JavaPlugin {
	private static final Logger log = Logger.getLogger("Minecraft");
	private final RedICListener redICListener = new RedICListener(this);
	
	public void onDisable() {
		log.info("RedIC DISABLED");

	}

	public void onEnable() {
		getServer().getPluginManager().registerEvents(redICListener, this);
		log.info("RedlIC STARTED");

	}

}
