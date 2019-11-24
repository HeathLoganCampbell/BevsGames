package games.bevs.library.modules.commands.commands;

import games.bevs.library.commons.CC;
import games.bevs.library.modules.commands.Command;
import games.bevs.library.modules.commands.CommandArgs;

public class GamemodeCommands
{
    @Command(name = "example", description = "Example!")
    public void test(CommandArgs args) {
        args.getSender().sendMessage(CC.green + "Example");
    }
}
