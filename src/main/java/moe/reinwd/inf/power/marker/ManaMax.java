package moe.reinwd.inf.power.marker;

import think.rpgitems.power.Meta;
import think.rpgitems.power.Property;

@Meta(marker = true)
public class ManaMax extends ConditionedMarker {
    @Property
    public double mana = 10;



    @Override
    public String displayText() {
        return null;
    }

    @Override
    public String getName() {
        return "mana_max";
    }
}
