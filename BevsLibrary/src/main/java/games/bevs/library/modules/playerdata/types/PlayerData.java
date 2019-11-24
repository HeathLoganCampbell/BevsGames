package games.bevs.library.modules.playerdata.types;

import com.google.common.collect.Lists;
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
    private List<RankDuration> rankHistory = Lists.newArrayList();

    @Transient @Setter
    private boolean loaded = false;

    public PlayerData(UUID uniqueId, String username, Rank rank, List<RankDuration> rankHistory) {
        this.uniqueId = uniqueId;
        this.username = username;
        this.rank = rank;
        this.rankHistory = rankHistory;
    }

    public void setRank(Rank rank)
    {
        this.rank = rank;
    }
}
