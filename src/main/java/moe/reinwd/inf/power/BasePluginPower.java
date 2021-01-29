package moe.reinwd.inf.power;

import moe.reinwd.inf.InfExtentionPlugin;
import org.bukkit.NamespacedKey;
import think.rpgitems.power.BasePower;

public abstract class BasePluginPower extends BasePower {
    @Override
    public NamespacedKey getNamespacedKey() {
        return new NamespacedKey(InfExtentionPlugin.plugin, getName());
    }
}
