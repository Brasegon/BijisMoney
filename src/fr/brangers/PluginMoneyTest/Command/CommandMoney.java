package fr.brangers.PluginMoneyTest.Command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import fr.brangers.PluginMoneyTest.Main;
import fr.brangers.PluginMoneyTest.SQL.SqlConnection;

public class CommandMoney implements CommandExecutor {

	private Main main;
	private SqlConnection sql;
	
	public CommandMoney(Main main) {
		this.main = main;
		this.sql = main.sql;
		
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] arg) {
		switch (arg.length) {
			case 0:
				return printMoney(sender, arg);
			case 1:
				return printMoneyPlayer(sender, arg);
		}
		return false;
	}
	private boolean printMoneyPlayer(CommandSender sender, String[] arg) {
		Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
			try {
				Connection connection = sql.getConnection();
				final PreparedStatement preparedStatement = connection.prepareStatement("SELECT Money FROM Account WHERE PlayerName = ?");
				preparedStatement.setString(1, arg[0]);
				final ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					double money = resultSet.getDouble("Money");
					sender.sendMessage("Player " + arg[0] + " : " + money);
				} else {
						sender.sendMessage("Player " + arg[0] + " n'a pas de compte");
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		});
		return true;
	}
	private boolean printMoney(CommandSender sender, String[] arg) {
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			final String key = "player." + player.getUniqueId();
			final ConfigurationSection config = main.config.getConfigurationSection(key);
			final double money = config.getDouble("money");
			player.sendMessage("Vous avez : " + money);
			return true;
		}
		else if (sender instanceof ConsoleCommandSender) {
			Bukkit.getConsoleSender().sendMessage("Cette commande ne peut etre effectue que par un joueur");
			return true;
		}
		return false;
	}

}
