package games.bevs.library.modules.playerdata.commands;

import games.bevs.library.commons.CC;
import games.bevs.library.commons.Duration;
import games.bevs.library.commons.Rank;
import games.bevs.library.modules.commands.Command;
import games.bevs.library.modules.commands.CommandArgs;
import games.bevs.library.modules.playerdata.PlayerDataHandler;
import games.bevs.library.modules.playerdata.types.PlayerData;
import lombok.AllArgsConstructor;
import org.bukkit.command.CommandSender;

/**
 * <code>
 *     /playerdata rank promote [player] [Rank] [Duration (not required)]
 *     /playerdata rank demote [player] [Rank] [Duration (not required)]
 *     /playerdata rank force [player] [Rank] [Duration (not required)]
 *     /playerdata rank view [player]
 * </code>
 */
@AllArgsConstructor
public class RankCommands<P extends PlayerData>
{
    private PlayerDataHandler<P> playerDataHandler;

    /**
     * Promotes a players rank,
     * only accessible via console
     * @param args
     */
    @Command(name = "playerdata.rank.promote",
             description = "if rank is higher than their current rank, it'll upgrade their rank",
             usage = "/playerdata rank promote [player] [Rank]",
             consoleOnly = true
            )
    public void onForceCommand(CommandArgs args)
    {
        CommandSender sender = args.getSender(); //Should only be the cosnole
        Duration duration = new Duration(100000, Duration.TimeUnit.YEAR);

        if(args.length() <= 2)
        {
            sender.sendMessage(CC.red + "Please give a player and a rank");
            return;
        }

        final String playerName = args.getArgs(0);
        final String rankStr = args.getArgs(1);
        final String durationStr = args.getArgs(2);

        final Rank rank = Rank.toRank(rankStr);
        if(rank == null)
        {
            sender.sendMessage(CC.red + "Unknown rank " + rankStr);
            return;
        }

        if(args.length() == 3)
            duration = new Duration(durationStr);
        duration = duration.withNow();
        final Duration finalDuration = duration;


        sender.sendMessage(CC.aqua + "Loading...");
        playerDataHandler.asyncFetchPlayerData(playerName, (playerdata) -> {
            if(!playerdata.getRank().hasPermissionsOf(rank))
            {
                sender.sendMessage(CC.red + "You cannot demote a player by promoting them");
                return;
            }
            playerdata.setRank(rank);
            playerDataHandler.save(playerdata);
            sender.sendMessage(CC.green + "Player rank is now set to " + rank.getDisplayName() + " for " + finalDuration.getFormatedTime());
        });
    }

    /**
     * demote a players rank,
     * only accessible via console
     * @param args
     */
    @Command(name = "playerdata.rank.demote",
            description = "if rank is higher than their current rank, it'll downgrade their rank",
            usage = "/playerdata rank promote [player] [Rank]",
            consoleOnly = true
    )
    public void onDemoteCommand(CommandArgs args)
    {
        CommandSender sender = args.getSender(); //Should only be the cosnole

        if(args.length() <= 2)
        {
            sender.sendMessage(CC.red + "Please give a player and a rank");
            return;
        }

        final String playerName = args.getArgs(0);
        final String rankStr = args.getArgs(1);

        final Rank rank = Rank.toRank(rankStr);
        if(rank == null)
        {
            sender.sendMessage(CC.red + "Unknown rank " + rankStr);
            return;
        }


        sender.sendMessage(CC.aqua + "Loading...");
        playerDataHandler.asyncFetchPlayerData(playerName, (playerdata) -> {
            if(playerdata.getRank().hasPermissionsOf(rank))
            {
                sender.sendMessage(CC.red + "The player canot be promoted via demote!");
                return;
            }
            playerdata.setRank(rank);
            sender.sendMessage(CC.green + "Player rank is now set to " + rank.getDisplayName());
        });
    }

    /**
     * force a players rank,
     * only accessible via console
     * @param args
     */
    @Command(name = "playerdata.rank.force",
            description = "if rank is higher than their current rank, it'll upgrade their rank",
            usage = "/playerdata rank promote [player] [Rank]",
            consoleOnly = true
    )
    public void onPromoteCommand(CommandArgs args)
    {
        CommandSender sender = args.getSender(); //Should only be the cosnole
        Duration duration = new Duration(100000, Duration.TimeUnit.YEAR);

        if(args.length() <= 2)
        {
            sender.sendMessage(CC.red + "Please give a player and a rank");
            return;
        }

        final String playerName = args.getArgs(0);
        final String rankStr = args.getArgs(1);
        final String durationStr = args.getArgs(2);

        final Rank rank = Rank.toRank(rankStr);
        if(rank == null)
        {
            sender.sendMessage(CC.red + "Unknown rank " + rankStr);
            return;
        }

        if(args.length() == 3)
            duration = new Duration(durationStr);
        duration = duration.withNow();
        final Duration finalDuration = duration;


        sender.sendMessage(CC.aqua + "Loading...");
        playerDataHandler.asyncFetchPlayerData(playerName, (playerdata) -> {
            playerdata.setRank(rank);
            playerDataHandler.save(playerdata);
            sender.sendMessage(CC.green + "Player rank is now set to " + rank.getDisplayName() + " for " + finalDuration.getFormatedTime());
        });
    }
}
