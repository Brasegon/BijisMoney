package fr.brangers.PluginMoneyTest.Event;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import fr.brangers.PluginMoneyTest.Main;
import fr.brangers.PluginMoneyTest.SQL.SqlConnection;

public class CreateAccountJoin implements Listener {
	private Main main;
	private SqlConnection sql;
	
	public CreateAccountJoin(Main main) {
		this.main = main;
		sql = main.sql;
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		createAccount(player);
	}
	
	private void createAccount(Player player) {
		final UUID uuid = player.getUniqueId();
		Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
			try {
				Connection connection = sql.getConnection();
				final PreparedStatement preparedStatement = connection.prepareStatement("SELECT UUID, PlayerName FROM Account WHERE uuid = ?");
				preparedStatement.setString(1, uuid.toString());
				final ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					String name = resultSet.getString("PlayerName");
					if (name != player.getName()) {
						final PreparedStatement updatePlayer = connection.prepareStatement("UPDATE Account SET PlayerName = ? WHERE UUID = ?");
						updatePlayer.setString(1, player.getName());
						updatePlayer.setString(2, uuid.toString());
						updatePlayer.executeUpdate();
					}
				} else {
					final PreparedStatement createPlayer = connection.prepareStatement("INSERT into Account(UUID, PlayerName, Money) VALUES (?, ?, 20)");
					createPlayer.setString(1, uuid.toString());
					createPlayer.setString(2, player.getName());
					createPlayer.executeUpdate();
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		});
	}
}
