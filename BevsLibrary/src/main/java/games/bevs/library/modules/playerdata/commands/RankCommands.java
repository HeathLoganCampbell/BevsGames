package games.bevs.library.modules.playerdata.commands;

import games.bevs.library.commons.CC;
import games.bevs.library.commons.Console;
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

    public void applyRank(CommandArgs args, boolean forced, boolean promote)
    {
        CommandSender sender = args.getSender(); //Should only be the cosnole
        Duration duration = new Duration(100000, Duration.TimeUnit.YEAR);

        if(args.length() < 2)
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

        if(args.length() == 3) {
            String durationStr = args.getArgs(2);
            duration = new Duration(durationStr);
        }
        duration = duration.withNow();
        final Duration finalDuration = duration;


        sender.sendMessage(CC.aqua + "Loading " + playerName +"'s PlayerData...");
        playerDataHandler.asyncFetchPlayerData(playerName, (playerdata) -> {
            if (!forced) {
                if(!promote) {
                    if (!playerdata.getRank().hasPermissionsOf(rank)) {
                        sender.sendMessage(CC.red + "You cannot demote a player by promoting them");
                        return;
                    }
                }
                else
                {
                    if(playerdata.getRank().hasPermissionsOf(rank))
                    {
                        sender.sendMessage(CC.red + "The player canot be promoted via demote!");
                        return;
                    }
                }
            }
            playerdata.setRank(rank, finalDuration);
            playerDataHandler.save(playerdata);
            sender.sendMessage(CC.green + "Player rank is now set to " + rank.getDisplayName() + " for " + finalDuration.getFormatedTime());
        });
    }


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
        applyRank(args, false, true);
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
       this.applyRank(args, false, false);
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
        applyRank(args, true, false);
    }
}
