package moe.reinwd.inf.power.marker;

import moe.reinwd.inf.InfExtentionPlugin;
import org.bukkit.NamespacedKey;
import think.rpgitems.power.Property;
import think.rpgitems.power.marker.BaseMarker;

import java.util.HashSet;
import java.util.Set;

public abstract class ConditionedMarker extends BaseMarker {
    @Property
    Set<String> conditions = new HashSet<>();

    @Override
    public NamespacedKey getNamespacedKey() {
        return new NamespacedKey(InfExtentionPlugin.plugin, getName());
    }
}
