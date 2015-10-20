package uk.co.quartzcraft.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;

import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.database.MySQL;
import uk.co.quartzcraft.core.features.QCAlertTypes;

import uk.co.quartzcraft.example.command.*;
import uk.co.quartzcraft.example.listeners.*;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.systems.notifications.AlertTypeHandler;

/**
 * Core file for Example plugin.
 */
public class ExamplePlugin extends JavaPlugin {
	public static String version;
	
	public static Plugin plugin;
	public static final Logger log = Logger.getLogger("Minecraft");
	
	public static Connection DBExample = null;
    
	public static MySQL MySQLexample = null;

  public QCommandFramework commandFramework;
	
	@Override
	public void onDisable() {

	      	//Close database
	      	log.info("[Example]Closing database connections");
	      	MySQLexample.closeConnection();
	      	try {
	        	DBExample.close();
	        	log.info("[Example]Successfully closed database connections");
	      	} catch(SQLException e) {
	        	log.log(Level.SEVERE,"[Example]Failed to close database connections!");
	      	}
	
	    	//Shutdown notice
		log.info("[Example]The QuartzCore Plugin has been disabled!");
	}
	@Override
	public void onEnable() {
		
		log.info("[Example][STARTUP LOGGER]Console logger discovered");

    		plugin = this;
    		version = plugin.getDescription().getVersion();
		
		//Config files - Set up config files if this is the first startup 
		log.info("[PluginPrefix]Running plugin configuration");
		this.saveDefaultConfig();
		boolean DBConnect = this.getConfig().getBoolean("settings.database-connect");
		
		//Create SQL instances or shutdown server if database connection is not allowed
		if(DBConnect) {
			//Example Database
			String SQLExampleHost = this.getConfig().getString("database.example.host");
			String SQLExampleDatabase = this.getConfig().getString("database.example.database");
			String SQLExampleUser = this.getConfig().getString("database.example.username");
			String SQLExamplePassword = this.getConfig().getString("database.example.password");
			MySQLexample = new MySQL(plugin, SQLExampleHost, "3306", SQLExampleDatabase, SQLExampleUser, SQLExamplePassword);
		} else {
      			log.warning("[Example]Database connection set to false! Please fix this in the config.yml file!");
      			this.getServer().shutdown();
    		}

	    	//Database - Connect to the database
	    	if(DBConnect) {
	      		log.info("[Example][STARTUP]Connecting to Database");
	      		DBExample = MySQLcore.openConnection();
	    	}
	
		//Phrases - Create plugin specific phrases here
		log.info("[Example][STARTUP]Creating Phrases");
		QCChat.addPhrase("example_phrase", "&3This is an example phrase");
	
	    	//Alert Types - Register all Alert Types here
	    	log.info("[Example][STARTUP] Registering alert types");
	    	AlertTypeHandler.registerAlertTypes(new ExampleAlertTypes());
	
		//Listeners - Register all listeners here
		log.info("[Example][STARTUP]Registering listeners...");
		new ExampleListener(this);
	
		//Commands - Register all commands here
		log.info("[Example][STARTUP]Registering commands...");
	    	commandFramework = new QCommandFramework(this);
	    	commandFramework.registerCommands(new CommandExample(this));
		  		
		//Startup notice
		log.info("[Example]The Example Plugin has been enabled!");
	}
	
    	//This method sends commands to be handled by the QCommand Framework
    	@Override
    	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        	return commandFramework.handleCommand(sender, label, command, args);
    	}
}
