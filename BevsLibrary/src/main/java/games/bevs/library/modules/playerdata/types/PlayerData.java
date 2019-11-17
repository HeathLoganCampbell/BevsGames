package games.bevs.library.modules.playerdata.types;

import games.bevs.library.commons.utils.Rank;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mongodb.morphia.annotations.Transient;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class PlayerData
{
    @NonNull
    private UUID uniqueId;
    @Setter
    private String username;

    private Rank rank;
    private List<RankDuration> rankHistory;

    @Transient @Setter
    private boolean loaded = false;
}
