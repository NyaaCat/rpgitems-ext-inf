package moe.reinwd.inf.power.cond;

import cat.nyaa.infiniteinfernal.InfPlugin;
import cat.nyaa.infiniteinfernal.api.InfVarApi;
import cat.nyaa.infiniteinfernal.ui.impl.VarRage;
import moe.reinwd.inf.InfExtentionPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.power.Meta;
import think.rpgitems.power.PowerResult;
import think.rpgitems.power.Property;
import think.rpgitems.power.PropertyHolder;

import java.util.Map;

@Meta(marker = true)
public class RageMaxCondition extends InfCondition<Double> {
    @Property
    public double maxRage = 0;

    @Property
    public boolean checkBase = false;

    @Override
    public PowerResult<Double> check(Player player, ItemStack stack, Map<PropertyHolder, PowerResult<?>> context) {
        InfPlugin plugin = InfExtentionPlugin.getPlugin(InfPlugin.class);
        if (plugin.isEnabled()) {
            InfVarApi varApi = plugin.getVarApi();
            VarRage rage = varApi.getRage(player);
            Double baseMax = rage.getBaseMax();
            Double maxValue = rage.getMaxValue();

            if (checkBase){
                if (baseMax <= this.maxRage) {
                    return PowerResult.ok(baseMax);
                } else {
                    return PowerResult.fail(baseMax);
                }
            }else {
                if (maxValue <= this.maxRage) {
                    return PowerResult.ok(maxValue);
                } else {
                    return PowerResult.fail(maxValue);
                }
            }
        }
        return PowerResult.fail(0d);
    }

    @Override
    public String getName() {
        return "rageMax";
    }
}
