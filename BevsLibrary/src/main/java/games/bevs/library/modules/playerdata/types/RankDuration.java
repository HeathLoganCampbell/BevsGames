package games.bevs.library.modules.playerdata.types;

import games.bevs.library.commons.Rank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RankDuration
{
    private Rank rank;
    private long start, expire;
    private boolean valid = false;
}
