package firewolf8385.playerpasswords.commands;

import firewolf8385.playerpasswords.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PP implements CommandExecutor
{
    SettingsManager settings = SettingsManager.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(args.length != 0)
        {
            switch(args[0])
            {
                default:
                    break;

                case "version":
                    break;

                case "support":
                    break;

                case "disable":
                    break;

                case "help":
                    break;
            }
        }
        else
        {

        }

        return true;
    }
}
