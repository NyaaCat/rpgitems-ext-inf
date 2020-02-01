package moe.reinwd.inf.power.cond;

import moe.reinwd.inf.InfExtentionPlugin;
import org.bukkit.NamespacedKey;
import think.rpgitems.power.Property;
import think.rpgitems.power.cond.BaseCondition;

public abstract class InfCondition<T> extends BaseCondition<T> {

    @Property(required = true)
    public String id;

    @Property
    public boolean isStatic = false;

    @Property
    public boolean isCritical = false;

    @Override
    public String id() {
        return id;
    }

    @Override
    public boolean isStatic() {
        return isStatic;
    }

    @Override
    public boolean isCritical() {
        return isCritical;
    }

    @Override
    public NamespacedKey getNamespacedKey() {
        return new NamespacedKey(InfExtentionPlugin.plugin, getName());
    }
}
