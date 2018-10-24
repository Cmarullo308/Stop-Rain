package me.CJStopRain.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;

import Tools.Print;

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

	@EventHandler
	public void onWorldLoad(WorldLoadEvent e) {
		main.getLogger().info("World Load Event");
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Material material = block.getType();
		Player player = event.getPlayer();

		player.sendMessage(ChatColor.GREEN + "You have broke " + material.toString());

		Print.pl("Working!");
	}

	@EventHandler
	public void mobKilled(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();

		if (entity.getType() == EntityType.ZOMBIE && entity.getKiller() instanceof Player) {
			event.getDrops().add(new ItemStack(Material.YELLOW_WOOL));
		} else {

		}
	}
}
