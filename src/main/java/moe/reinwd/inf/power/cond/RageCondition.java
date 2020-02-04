package moe.reinwd.inf.power.cond;

import cat.nyaa.infiniteinfernal.InfPlugin;
import cat.nyaa.infiniteinfernal.api.InfVarApi;
import cat.nyaa.infiniteinfernal.ui.impl.VarMana;
import cat.nyaa.infiniteinfernal.ui.impl.VarRage;
import moe.reinwd.inf.InfExtentionPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.power.*;

import java.util.Map;

@Meta(marker = true)
public class RageCondition extends InfCondition<Double> {
    @Property
    public double rage = 0;

    @Override
    public PowerResult<Double> check(Player player, ItemStack stack, Map<PropertyHolder, PowerResult<?>> context) {
        InfPlugin plugin = InfExtentionPlugin.getPlugin(InfPlugin.class);
        if (plugin.isEnabled()) {
            InfVarApi varApi = plugin.getVarApi();
            VarRage rage = varApi.getRage(player);
            Double value = rage.getValue();
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
