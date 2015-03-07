package uk.co.quartzcraft.example.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import uk.co.quartzcraft.core.command.framework.CommandArgs;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.systems.chat.QCChat;

import uk.co.quartzcraft.example.ExamplePlugin

public class CommandExample {
    private static ExamplePlugin plugin;
    private static QCommandFramework framework;

    public CommandExample(ExamplePlugin plugin) {
        this.plugin = plugin;
    }

    //Example Command
    @QCommand(name = "example", aliases = { "e" }, permission = "QCE.example", description = "This is the exmaple commands example description", usage = "Use /example sub")
    public void example(CommandArgs args) {
        //You can use this to check if the sender is a player and you send a message if they aren't.
        if(!(args.getSender() instanceof Player)){
            args.getSender().sendMessage(QCChat.getPhrase("player_use_only"));
            return; //Remove this if you still want the command to execute even if they aren't a player
        }
        
        //This is where you put the code which is executed when the command is executed
    }
    
    //Example Subcommand
    @QCommand(name = "example.sub", aliases = { "e.sub" }, permission = "QCE.example.subcommand", description = "This is the exmaple subcommands example description", usage = "Use /example sub")
    public void example(CommandArgs args) {
        //This is where you put the code which is executed when the subcommand is executed. The main command is NOT executed as well.
    }
}
