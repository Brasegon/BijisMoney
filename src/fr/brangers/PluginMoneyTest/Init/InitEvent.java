package fr.brangers.PluginMoneyTest.Init;

import fr.brangers.PluginMoneyTest.Main;
import fr.brangers.PluginMoneyTest.Event.CreateAccountJoin;

public class InitEvent {
	private Main main;
	
	public InitEvent(Main main) {
		this.main = main;
	}
	
	public void EventList() {
		main.getServer().getPluginManager().registerEvents(new CreateAccountJoin(main), main);
	}
}
