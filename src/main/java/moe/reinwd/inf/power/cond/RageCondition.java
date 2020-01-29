package moe.reinwd.inf.power.cond;

import cat.nyaa.infiniteinfernal.InfPlugin;
import cat.nyaa.infiniteinfernal.api.InfVarApi;
import cat.nyaa.infiniteinfernal.ui.impl.VarMana;
import moe.reinwd.inf.InfExtentionPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.power.*;

import java.util.Map;

@Meta(marker = true)
public class RageCondition extends InfCondition<Double> {
    @Property(required = true)
    public String id;

    @Property
    public double rage = 0;

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
    public PowerResult<Double> check(Player player, ItemStack stack, Map<PropertyHolder, PowerResult<?>> context) {
        InfPlugin plugin = InfExtentionPlugin.getPlugin(InfPlugin.class);
        if (plugin.isEnabled()) {
            InfVarApi varApi = plugin.getVarApi();
            VarMana mana = varApi.getMana(player);
            Double value = mana.getValue();
            if (value >= this.rage) {
                return PowerResult.ok(value);
            } else {
                return PowerResult.fail(value);
            }
        }
        return PowerResult.fail(0d);
    }

    @Override
    public String getName() {
        return "rage";
    }
}
