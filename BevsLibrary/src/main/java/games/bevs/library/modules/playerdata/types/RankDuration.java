package games.bevs.library.modules.playerdata.types;

import games.bevs.library.commons.Rank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mongodb.morphia.annotations.Embedded;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embedded
public class RankDuration
{
    private Rank rank;
    private long start, expire;
    private boolean valid = false;
}
