package uk.co.quartzcraft.example.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import uk.co.quartzcraft.core.command.framework.CommandArgs;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.systems.chat.QCChat;

import uk.co.quartzcraft.example.ExamplePlugin

//Always name the class with the command prefixed by the word "Command"
public class CommandCommand {
    private static ExamplePlugin plugin;
    private static QCommandFramework framework;

    public CommandExample(ExamplePlugin plugin) {
        this.plugin = plugin;
    }

    //Example Command
    @QCommand(name = "command", aliases = { "c" }, permission = "QCE.command", description = "This is the command description", usage = "Use /command")
    public void command(CommandArgs args) {
        //You do not need to check whether a player is executing the command or not. 
        //If you want the command to be executable by the console, add "requirePlayer = false" to the @QCommand
        
        Util.sendMessage(args.getPlayer(), QCChat.getPhrase("player_use_only"));
        //Always use Util.sendMessage(player, message) when second the player a message from the QuartzCore plugin.
        
        //This is where you put the code which is executed when the command is executed
    }
    
    //Example Subcommand
    @QCommand(name = "command.subcommand", aliases = { "c.sub" }, permission = "QCE.command.subcommand", description = "This is the subcommand description", usage = "Use /command subcommand")
    public void subcommand(CommandArgs args) {
        //This is where you put the code which is executed when the subcommand is executed. The main command is NOT executed as well.
    }
}
