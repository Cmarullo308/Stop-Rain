package me.CJStopRain.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new MyEvents(this), this);

		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("StopRain") || command.getName().equalsIgnoreCase("SR")) {
			switch (args[0].toLowerCase()) {
			case "setenabled":
				setEnabled(sender, args);
				break;
			case "showconsolemessage":
				setShowConsoleMessage(sender, args);
				break;
			case "numberofcancels":
				sendNumberOfCancels(sender);
				break;
			default:
				break;
			}
		}

		return true;
	}

	private void sendNumberOfCancels(CommandSender sender) {
		if (!sender.hasPermission("stoprain.sendnumberofcancels")) {
			noPermission(sender);
			return;
		}

		int numOfCancels = getConfig().getInt("numberOfCancels");
		if (numOfCancels > 1) {
			sender.sendMessage(ChatColor.GREEN + "The rain was stopped " + numOfCancels + " times");
		} else {
			sender.sendMessage(ChatColor.GREEN + "The rain was stopped " + numOfCancels + " time");
		}
	}

	private void noPermission(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
	}

	private void setShowConsoleMessage(CommandSender sender, String[] args) {
		if (!sender.hasPermission("stoprain.setconsolemessage")) {
			noPermission(sender);
			return;
		}

		if (args.length != 2) {
			sender.sendMessage(ChatColor.RED + "Invalid number of arguements");
			return;
		}

		if (args[1].equalsIgnoreCase("True") || args[1].equalsIgnoreCase("False")) {
			getConfig().set("showConsoleMessage", args[1].toLowerCase());
			saveConfig();
		} else {
			sender.sendMessage(ChatColor.RED + "Invalid arguement, must be true or false");
		}
	}

	private void setEnabled(CommandSender sender, String[] args) {
		if (!sender.hasPermission("stoprain.enable")) {
			noPermission(sender);
			return;
		}

		if (args.length != 2) {
			sender.sendMessage(ChatColor.RED + "Invalid number of arguements");
			return;
		}

		if (args[1].equalsIgnoreCase("True") || args[1].equalsIgnoreCase("False")) {
			getConfig().set("enabled", args[1].toLowerCase());
			saveConfig();
			if (args[1].equalsIgnoreCase("True")) {
				if (sender instanceof Player) {
					((Player) sender).getWorld().setStorm(false);
				}
				sender.sendMessage(ChatColor.GREEN + "StopRain enabled");
			} else {
				sender.sendMessage(ChatColor.GREEN + "StopRain disabled");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Invalid arguement, must be true or false");
		}
	}

	public void onDisable() {

	}

	public void consoleMessage(String string) {
		getLogger().info(string);
	}
}
