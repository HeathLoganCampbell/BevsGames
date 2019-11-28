package games.bevs.library.modules.worldoptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorldOptionSettings
{
    private boolean enableWeather = true;
    private boolean enableDecay = true;
    private boolean enableDayNights = true;

    private boolean enableBlockBreak = true;
    private boolean enableBlockPlace = true;
    private boolean enableBlockInteraction = true;

    private boolean enableHunger = true;
    private boolean enablePlayerDamage = true;

    private boolean enableDropItems = true;
    private boolean enablePickUpItems = true;
}
