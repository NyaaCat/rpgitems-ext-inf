package moe.reinwd.inf.power.cond;

import cat.nyaa.infiniteinfernal.InfPlugin;
import cat.nyaa.infiniteinfernal.api.InfVarApi;
import cat.nyaa.infiniteinfernal.ui.impl.VarMana;
import moe.reinwd.inf.InfExtentionPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.power.Meta;
import think.rpgitems.power.PowerResult;
import think.rpgitems.power.Property;
import think.rpgitems.power.PropertyHolder;

import java.util.Map;

@Meta(marker = true)
public class ManaCondition extends InfCondition<Double> {

    @Property
    public double mana = 0;

    @Override
    public PowerResult<Double> check(Player player, ItemStack stack, Map<PropertyHolder, PowerResult<?>> context) {
        InfPlugin plugin = InfExtentionPlugin.getPlugin(InfPlugin.class);
        if (plugin.isEnabled()) {
            InfVarApi varApi = plugin.getVarApi();
            VarMana mana = varApi.getMana(player);
            Double value = mana.getValue();
            if (value >= this.mana) {
                return PowerResult.ok(value);
            } else {
                return PowerResult.fail(value);
            }
        }
        return PowerResult.fail(0d);
    }

    @Override
    public String getName() {
        return "mana";
    }
}
