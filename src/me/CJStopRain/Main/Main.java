package me.CJStopRain.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	public String helpMessage;

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new MyEvents(this), this);

		createHelpMessage();

		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	private void createHelpMessage() {
		helpMessage = ChatColor.BOLD + "Commands\n";

		helpMessage += ChatColor.WHITE + "  StopRain setEnabled <true | false>\n";
		helpMessage += ChatColor.GRAY + "  StopRain showconsolemessage <true | false>\n";
		helpMessage += ChatColor.WHITE + "  StopRain numberofcancels";
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
			case "help":
				sender.sendMessage(helpMessage);
				break;
			default:
				sender.sendMessage(ChatColor.RED + "Invalid arguements");
				break;
			}
		}

		return true;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("stoprain")) {
			ArrayList<String> choices = new ArrayList<String>();

			if (args.length == 1) {
				if ("setenabled".startsWith(args[0].toLowerCase())) {
					choices.add("setenabled");
				}

				if ("showconsolemessage".startsWith(args[0].toLowerCase())) {
					choices.add("showconsolemessage");
				}

				if ("numberofcancels".startsWith(args[0].toLowerCase())) {
					choices.add("numberofcancels");
				}

				if ("help".startsWith(args[0].toLowerCase())) {
					choices.add("help");
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("setenabled") || args[0].equalsIgnoreCase("showconsolemessage")) {
					if ("true".startsWith(args[1].toLowerCase())) {
						choices.add("true");
					}

					if ("false".startsWith(args[1].toLowerCase())) {
						choices.add("false");
					}
				}
			}

			Collections.sort(choices);
			return choices;
		}

		return null;
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
