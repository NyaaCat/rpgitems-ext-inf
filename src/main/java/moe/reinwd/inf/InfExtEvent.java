package moe.reinwd.inf;

import cat.nyaa.infiniteinfernal.InfPlugin;
import cat.nyaa.infiniteinfernal.api.InfVarApi;
import cat.nyaa.infiniteinfernal.event.ui.PlayerManaMaxEvent;
import cat.nyaa.infiniteinfernal.event.ui.PlayerRageMaxEvent;
import cat.nyaa.infiniteinfernal.ui.RegenerationEvent;
import cat.nyaa.infiniteinfernal.ui.impl.VarRage;
import moe.reinwd.inf.power.marker.ManaMax;
import moe.reinwd.inf.power.marker.RageMax;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.item.ItemManager;
import think.rpgitems.item.RPGItem;
import think.rpgitems.power.*;
import think.rpgitems.power.trigger.BaseTriggers;

import java.util.*;
import java.util.stream.Collectors;

public class InfExtEvent implements Listener {

    @EventHandler
    public void onManaRegeneration(RegenerationEvent event) {

    }

    @EventHandler
    public void onManaMaxTick(PlayerManaMaxEvent event) {
        Player player = event.getPlayer();
        ItemStack[] armour = player.getInventory().getArmorContents();
        for (ItemStack part : armour) {
            Optional<RPGItem> item = ItemManager.toRPGItem(part);
            if (!item.isPresent())
                continue;
            RPGItem rgi = item.get();
            List<Power> powers = rgi.getPowers();
        }
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
        Optional<RPGItem> offhand = ItemManager.toRPGItem(itemInOffHand);
        Optional<RPGItem> mainhand = ItemManager.toRPGItem(itemInMainHand);

        if (mainhand.isPresent()) {
            List<Power> powers = mainhand.get().getPowers();
            List<Condition<?>> conditions = mainhand.get().getConditions();
        }
        if (offhand.isPresent()) {
            List<Power> powers = offhand.get().getPowers();
            List<Condition<?>> conditions = offhand.get().getConditions();
        }
    }

    @EventHandler
    public void onRageMaxTick(PlayerRageMaxEvent event) {
        Player player = event.getPlayer();
        ItemStack[] armour = player.getInventory().getArmorContents();
        for (ItemStack part : armour) {
            Optional<RPGItem> item = ItemManager.toRPGItem(part);
            if (!item.isPresent())
                continue;
            RPGItem rgi = item.get();

        }
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
        Optional<RPGItem> offhand = ItemManager.toRPGItem(itemInOffHand);
        Optional<RPGItem> mainhand = ItemManager.toRPGItem(itemInMainHand);

        if (mainhand.isPresent()) {
            List<Power> powers = mainhand.get().getPowers();
            List<Condition<?>> conditions = mainhand.get().getConditions();
        }
        if (offhand.isPresent()) {
            List<Power> powers = offhand.get().getPowers();
            List<Condition<?>> conditions = offhand.get().getConditions();

        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        Player damager = (Player) event.getDamager();
        InfPlugin plugin = InfExtentionPlugin.getPlugin(InfPlugin.class);
        if (plugin.isEnabled()) {
            InfVarApi varApi = plugin.getVarApi();
            VarRage rage = varApi.getRage(damager);
            double damage = event.getDamage();
            rage.drop(-damageFunc(damage, 1), varApi.getTick());
        }
    }

    private double damageFunc(double damage, double factor) {
        if (damage < 0) return 0;
        double x = damage + 1;
        double log = Math.log(x);
        return 6 * log * factor;
    }
}
