package fr.brangers.PluginMoneyTest;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import fr.brangers.PluginMoneyTest.Init.InitCommand;
import fr.brangers.PluginMoneyTest.Init.InitEvent;
import fr.brangers.PluginMoneyTest.SQL.SqlConnection;

public class Main extends JavaPlugin {
	
	public YamlConfiguration config;
	public File file;
	InitCommand command = new InitCommand(this);
	InitEvent event = new InitEvent(this);
	public SqlConnection sql;
	@Override
	public void onEnable() {
		sql = new SqlConnection("jdbc:mysql://", "localhost", "minecraft", "brangers", "pokemon");
		sql.connection();
		Bukkit.getConsoleSender().sendMessage("Initialisation du Plugin Money");
		command.CommandList();
		event.EventList();
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					Entity test = (Entity) player.getLocation();
			    }
				
			}
			
		}, 0, 20);
	}
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("Fermeture du Plugin Money");
		sql.disconnect();
	}
	@Override
	public void onLoad() {
		final File file = new File(this.getDataFolder(), "Money/account.yml");
		final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		this.file = file;
		this.config = config;
		try {
			config.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}