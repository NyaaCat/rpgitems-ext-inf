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
public class ManaMaxCondition extends InfCondition<Double> {
    @Property(required = true)
    public String id;

    @Property
    public double maxMana = 0;

    @Property
    public boolean checkBase = false;

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
            Double baseMax = mana.getBaseMax();
            Double maxValue = mana.getMaxValue();

            if (checkBase){
                if (baseMax >= this.maxMana) {
                    return PowerResult.ok(baseMax);
                } else {
                    return PowerResult.fail(baseMax);
                }
            }else {
                if (maxValue >= this.maxMana) {
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
        return "manaMax";
    }
}
