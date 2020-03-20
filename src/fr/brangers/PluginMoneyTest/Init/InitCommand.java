package fr.brangers.PluginMoneyTest.Init;

import fr.brangers.PluginMoneyTest.Main;
import fr.brangers.PluginMoneyTest.Command.CommandMoney;

public class InitCommand {
	
	private Main main;
	public InitCommand(Main main) {
		this.main = main;
	}

	public void CommandList() {
		main.getCommand("money").setExecutor(new CommandMoney(main));
	}
}
