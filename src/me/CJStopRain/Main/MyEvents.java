package me.CJStopRain.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class MyEvents implements Listener {
	Main main;

	public MyEvents(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		if (event.toWeatherState() && main.getConfig().getString("enabled").equals("true")) {
			event.setCancelled(true);
			event.getWorld().setStorm(false);
			if (main.getConfig().getString("showConsoleMessage").equalsIgnoreCase("true")) {
				main.getLogger().info("Stoped rain");
			}
			main.getConfig().set("numberOfCancels", main.getConfig().getInt("numberOfCancels") + 1);
			main.saveConfig();
		}
	}
}
