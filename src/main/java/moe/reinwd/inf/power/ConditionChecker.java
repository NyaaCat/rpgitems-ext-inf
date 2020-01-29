package moe.reinwd.inf.power;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.power.Condition;
import think.rpgitems.power.Pimpl;
import think.rpgitems.power.PowerResult;
import think.rpgitems.power.PropertyHolder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ConditionChecker {
    private static <T> PowerResult<T> checkConditions(Player player, ItemStack i, Pimpl pimpl, List<Condition<?>> conds, Map<PropertyHolder, PowerResult<?>> context) {
        Set<String> ids = pimpl.getPower().getConditions();
        List<Condition<?>> conditions = conds.stream().filter(p -> ids.contains(p.id())).collect(Collectors.toList());
        List<Condition<?>> failed = conditions.stream().filter(p -> p.isStatic() ? !context.get(p).isOK() : !p.check(player, i, context).isOK()).collect(Collectors.toList());
        if (failed.isEmpty()) return null;
        return failed.stream().anyMatch(Condition::isCritical) ? PowerResult.abort() : PowerResult.condition();
    }
}
