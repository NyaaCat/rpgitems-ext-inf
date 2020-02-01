package moe.reinwd.inf.power.marker;

import moe.reinwd.inf.InfExtentionPlugin;
import org.bukkit.NamespacedKey;
import think.rpgitems.power.Property;
import think.rpgitems.power.marker.BaseMarker;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ConditionedMarker<T> extends BaseMarker {
    @Property
    Set<String> conditions = new HashSet<>();

    @Override
    public NamespacedKey getNamespacedKey() {
        return new NamespacedKey(InfExtentionPlugin.plugin, getName());
    }

    public Set<String> getConditions() {
        return conditions;
    }

    public abstract T getValue();
}
