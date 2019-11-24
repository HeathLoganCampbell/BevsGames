package games.bevs.library.modules.playerdata.types;

import com.google.common.collect.Lists;
import games.bevs.library.commons.Duration;
import games.bevs.library.commons.Rank;
import lombok.*;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Transient;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(noClassnameStored = true)
public class PlayerData
{
    @NonNull
    @Id
    private UUID uniqueId;
    @Setter
    @Indexed
    private String username;

    private Rank rank = Rank.NORMAL;
    private long rankExpires = 0l;
    private List<RankDuration> rankHistory = Lists.newArrayList();

    @Transient @Setter
    private boolean loaded = false;

    public PlayerData(UUID uniqueId, String username, Rank rank, List<RankDuration> rankHistory) {
        this.uniqueId = uniqueId;
        this.username = username;
        this.rank = rank;
        this.rankHistory = rankHistory;
    }

    /**
     * Apply a new rank to the player,
     * all ranks greater than this rank will be overwritten
     * @param rank
     * @param duration
     */
    public void setRank(Rank rank, Duration duration)
    {
        this.rankHistory.add(new RankDuration(rank, System.currentTimeMillis(), duration.getMillis(), true));
        this.rankExpires = duration.getMillis();
        this.rank = rank;
        this.invalidUpperRanks(this.rank);
    }

    /**
     * If current rank has expired,
     * We'll mark it as expired along with another expired ranks
     * and find the next best rank and set that
     */
    public void handleExpireRanks()
    {
        //We don't care about nons
        if(this.getRank() == Rank.NORMAL) return;
        long now = System.currentTimeMillis();
        Rank bestRank = Rank.NORMAL;
        long expires = -1;

        for (RankDuration rankDuration : this.getRankHistory())
        {
            //rank is history is,
            //1. not expired
            //2. better or equal to the current rank
            //3. not invalid from past actions
            if(rankDuration.getExpire() > now
                    && rankDuration.isValid()
                    && rankDuration.getRank().hasPermissionsOf(bestRank))
            {
                bestRank = rankDuration.getRank();
                expires = rankDuration.getExpire();
            }
        }

        if(bestRank == Rank.NORMAL)
        {
            this.rank = Rank.NORMAL;
            this.rankExpires = 0l;
            return;
        }

        this.rank = bestRank;
        this.rankExpires = expires;
        //updated rank
    }

    /**
     * Makes all the ranks greater than this rank invalid to avoid rank stack abuse
     * @param newMaxRank
     */
    private void invalidUpperRanks(Rank newMaxRank)
    {
        long now =  System.currentTimeMillis();
        for (RankDuration rankDuration : this.rankHistory) {

            if(rankDuration.getRank().hasPermissionsOf(newMaxRank))
            {
                rankDuration.setValid(false);
            }
        }
    }
}
