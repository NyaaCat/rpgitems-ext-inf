package moe.reinwd.inf;

import cat.nyaa.infiniteinfernal.InfPlugin;
import cat.nyaa.infiniteinfernal.api.InfVarApi;
import cat.nyaa.infiniteinfernal.event.ui.PlayerManaMaxEvent;
import cat.nyaa.infiniteinfernal.event.ui.PlayerRageMaxEvent;
import cat.nyaa.infiniteinfernal.ui.RegenerationEvent;
import cat.nyaa.infiniteinfernal.ui.impl.VarRage;
import moe.reinwd.inf.power.ConditionChecker;
import moe.reinwd.inf.power.marker.ConditionedMarker;
import moe.reinwd.inf.power.marker.ManaMax;
import moe.reinwd.inf.power.marker.RageMax;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import think.rpgitems.item.ItemManager;
import think.rpgitems.item.RPGItem;
import think.rpgitems.power.Condition;
import think.rpgitems.power.PowerResult;
import think.rpgitems.power.PropertyHolder;
import moe.reinwd.inf.utils.Utils;

import java.util.*;

public class InfExtEvent implements Listener {

    @EventHandler
    public void onManaRegeneration(RegenerationEvent event) {

    }

    @EventHandler
    public void onManaMaxTick(PlayerManaMaxEvent event) {
        Player player = event.getPlayer();

        ItemStack[] inventory = player.getInventory().getContents();
        double bonus = 0;
        for (ItemStack part : inventory) {
            Optional<RPGItem> item = ItemManager.toRPGItem(part);
            if (!item.isPresent())
                continue;
            RPGItem rgi = item.get();
            bonus += getBonus(player, part, rgi, ManaMax.class);
        }
        event.setBonus(bonus);
    }

    private double getBonus(Player player, ItemStack part, RPGItem rgi, Class<? extends ConditionedMarker<Double>> tClass) {
        List<? extends ConditionedMarker<Double>> markers = rgi.getMarker(tClass);
        List<Condition<?>> conditions = rgi.getConditions();
        Map<Condition<?>, PowerResult<?>> staticCondition = ConditionChecker.checkStaticCondition(player, part, conditions, markers);
        Map<PropertyHolder, PowerResult<?>> resultMap = new LinkedHashMap<>(staticCondition);
        double bonus = 0;
        for (ConditionedMarker<Double> marker : markers) {
            PowerResult<Object> result = ConditionChecker.checkConditions(player, part, marker, conditions, resultMap);
            if (result == null) {
                bonus += marker.getValue();
            }
        }
        return bonus;
    }

    @EventHandler
    public void onRageMaxTick(PlayerRageMaxEvent event) {
        Player player = event.getPlayer();

        double bonus = 0;
        ItemStack[] inventory = player.getInventory().getContents();
        for (ItemStack part : inventory) {
            Optional<RPGItem> item = ItemManager.toRPGItem(part);
            if (!item.isPresent())
                continue;
            RPGItem rgi = item.get();
            bonus += getBonus(player, part, rgi, RageMax.class);
        }
        event.setBonus(bonus);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity damager1 = event.getDamager();
        if (damager1 instanceof Projectile){
            ProjectileSource shooter = ((Projectile) damager1).getShooter();
            if (shooter instanceof Player){
                damager1 = (Entity) shooter;
            }
        }
        if (!(damager1 instanceof Player)) {
            return;
        }
        Player damager = (Player) damager1;
        InfPlugin plugin = InfExtentionPlugin.getPlugin(InfPlugin.class);
        if (plugin.isEnabled()) {
            InfVarApi varApi = plugin.getVarApi();
            VarRage rage = varApi.getRage(damager);
            int lastDrop = rage.getLastDrop();
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (varApi.getTick() - lastDrop < 40){
                        return;
                    }
                    double damage = event.getFinalDamage();
                    rage.drop(-Utils.damageFunc(damage, 1, rage), varApi.getTick());
                }
            }.runTaskLater(InfExtentionPlugin.plugin, 1);
        }
    }


}
