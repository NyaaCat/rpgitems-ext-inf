package moe.reinwd.inf.power.impl;

import cat.nyaa.infiniteinfernal.InfPlugin;
import cat.nyaa.infiniteinfernal.api.InfVarApi;
import cat.nyaa.infiniteinfernal.ui.impl.VarRage;
import moe.reinwd.inf.InfExtentionPlugin;
import moe.reinwd.inf.utils.Utils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.power.*;

import static think.rpgitems.power.Utils.checkCooldown;

@Meta(defaultTrigger = "RIGHT_CLICK", generalInterface = PowerPlain.class, implClass = Rage.Impl.class)
public class Rage extends BasePower {

    public double getUseRage() {
        return useRage;
    }

    public void setUseRage(double useRage) {
        this.useRage = useRage;
    }

    public void setDamageFactor(double damageFactor) {
        this.damageFactor = damageFactor;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    public void setRequireHurtByEntity(boolean requireHurtByEntity) {
        this.requireHurtByEntity = requireHurtByEntity;
    }

    public double getDamageFactor() {
        return damageFactor;
    }

    public int getCost() {
        return cost;
    }

    public long getCooldown() {
        return cooldown;
    }

    public boolean isRequireHurtByEntity() {
        return requireHurtByEntity;
    }

    @Property
    public double useRage = 0;

    @Property
    public double damageFactor = 1;

    /**
     * Cost of this power
     */
    @Property
    public int cost = 0;
    /**
     * Cooldown time of this power
     */
    @Property
    public long cooldown = 0;

    @Property
    public boolean requireHurtByEntity = true;

    @Override
    public String displayText() {
        return "rage";
    }

    @Override
    public String getName() {
        return "rage";
    }

    public class Impl implements PowerPlain, PowerRightClick, PowerLeftClick, PowerSneak, PowerSneaking, PowerSprint, PowerBowShoot, PowerHitTaken, PowerHit, PowerHurt {
        @Override
        public PowerResult<Float> bowShoot(Player player, ItemStack stack, EntityShootBowEvent event) {
            return fire(player, stack).with(event.getForce());
        }

        @Override
        public PowerResult<Double> hit(Player player, ItemStack stack, LivingEntity entity, double damage, EntityDamageByEntityEvent event) {
            return rageByDamage(player, stack, damage).with(damage);
        }

        @Override
        public PowerResult<Double> takeHit(Player target, ItemStack stack, double damage, EntityDamageEvent event) {
            if (!isRequireHurtByEntity() || event instanceof EntityDamageByEntityEvent) {
                return rageByDamage(target, stack, damage).with(damage);
            }
            return rageByDamage(target, stack, event.getDamage()).with(damage);
        }

        @Override
        public PowerResult<Void> hurt(Player target, ItemStack stack, EntityDamageEvent event) {
            if (!isRequireHurtByEntity() || event instanceof EntityDamageByEntityEvent) {
                return rageByDamage(target, stack, event.getDamage());
            }
            return rageByDamage(target, stack, event.getDamage());
        }

        @Override
        public PowerResult<Void> leftClick(Player player, ItemStack stack, PlayerInteractEvent event) {
            return fire(player, stack);
        }

        @Override
        public PowerResult<Void> fire(Player player, ItemStack stack) {
            if (!checkCooldown(getPower(), player, getCooldown(), true, true)) return PowerResult.cd();
            if (!getItem().consumeDurability(stack, getCost())) return PowerResult.cost();
            return costRage(player, stack);
        }

        private PowerResult<Void> rageByDamage(Player player, ItemStack stack, double damage){
            InfPlugin plugin = InfExtentionPlugin.getPlugin(InfPlugin.class);
            if (plugin.isEnabled()){
                InfVarApi varApi = plugin.getVarApi();
                VarRage rage = varApi.getRage(player);
                rage.drop(Utils.damageFunc(damage, 1, rage, - Double.MAX_VALUE), varApi.getTick());
            }
            return PowerResult.ok();
        }

        private PowerResult<Void> costRage(Player player, ItemStack stack) {
            InfPlugin plugin = InfExtentionPlugin.getPlugin(InfPlugin.class);
            if (plugin.isEnabled()){
                InfVarApi varApi = plugin.getVarApi();
                VarRage rage = varApi.getRage(player);
                rage.drop(getUseRage(), varApi.getTick());
            }
            return PowerResult.ok();
        }

        @Override
        public PowerResult<Void> rightClick(Player player, ItemStack stack, PlayerInteractEvent event) {
            return fire(player, stack);
        }

        @Override
        public PowerResult<Void> sneak(Player player, ItemStack stack, PlayerToggleSneakEvent event) {
            return fire(player, stack);
        }

        @Override
        public PowerResult<Void> sneaking(Player player, ItemStack stack) {
            return fire(player, stack);
        }

        @Override
        public PowerResult<Void> sprint(Player player, ItemStack stack, PlayerToggleSprintEvent event) {
            return fire(player, stack);
        }

        @Override
        public Power getPower() {
            return Rage.this;
        }
    }
}
