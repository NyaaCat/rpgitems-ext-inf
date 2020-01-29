package moe.reinwd.inf.power.marker;

import think.rpgitems.power.Meta;
import think.rpgitems.power.Property;;

@Meta(marker = true)
public class RageMax extends ConditionedMarker {
    @Property
    public double rage = 10;

    @Override
    public String displayText() {
        return null;
    }

    @Override
    public String getName() {
        return "rage_max";
    }
}
