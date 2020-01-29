package moe.reinwd.inf.power.cond;

import moe.reinwd.inf.InfExtentionPlugin;
import org.bukkit.NamespacedKey;
import think.rpgitems.power.cond.BaseCondition;

public abstract class InfCondition<T> extends BaseCondition<T> {
    @Override
    public NamespacedKey getNamespacedKey() {
        return new NamespacedKey(InfExtentionPlugin.plugin, getName());
    }
}
