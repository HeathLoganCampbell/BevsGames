package games.bevs.library.modules.playerdata.types;

import games.bevs.library.commons.utils.Rank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankDuration
{
    private Rank rank;
    private long start, expire;
}
