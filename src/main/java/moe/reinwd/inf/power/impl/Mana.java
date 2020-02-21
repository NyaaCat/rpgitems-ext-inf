package moe.reinwd.inf.power.impl;

import cat.nyaa.infiniteinfernal.InfPlugin;
import cat.nyaa.infiniteinfernal.api.InfVarApi;
import cat.nyaa.infiniteinfernal.ui.impl.VarMana;
import moe.reinwd.inf.InfExtentionPlugin;
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

@Meta(defaultTrigger = "RIGHT_CLICK", generalInterface = PowerPlain.class, implClass = Mana.Impl.class)
public class Mana extends BasePower{
    public void setUseMana(double useMana) {
        this.useMana = useMana;
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

    public double getUseMana() {
        return useMana;
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
    public double useMana = 0;

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
        return "mana";
    }

    @Override
    public String getName() {
        return "mana";
    }

    public class Impl implements PowerPlain, PowerRightClick, PowerLeftClick, PowerSneak, PowerSneaking, PowerSprint, PowerBowShoot, PowerHitTaken, PowerHit, PowerHurt, PowerTick{
        @Override
        public PowerResult<Float> bowShoot(Player player, ItemStack stack, EntityShootBowEvent event) {
            return fire(player, stack).with(event.getForce());
        }

        @Override
        public PowerResult<Double> hit(Player player, ItemStack stack, LivingEntity entity, double damage, EntityDamageByEntityEvent event) {
            return fire(player, stack).with(damage);
        }

        @Override
        public PowerResult<Double> takeHit(Player target, ItemStack stack, double damage, EntityDamageEvent event) {
            if (!isRequireHurtByEntity() || event instanceof EntityDamageByEntityEvent) {
                return fire(target, stack).with(damage);
            }
            return PowerResult.noop();
        }

        @Override
        public PowerResult<Void> hurt(Player target, ItemStack stack, EntityDamageEvent event) {
            if (!isRequireHurtByEntity() || event instanceof EntityDamageByEntityEvent) {
                return fire(target, stack);
            }
            return PowerResult.noop();
        }

        @Override
        public PowerResult<Void> leftClick(Player player, ItemStack stack, PlayerInteractEvent event) {
            return fire(player, stack);
        }

        @Override
        public PowerResult<Void> fire(Player player, ItemStack stack) {
            if (!checkCooldown(getPower(), player, getCooldown(), true, true)) return PowerResult.cd();
            if (!getItem().consumeDurability(stack, getCost())) return PowerResult.cost();
            return costMana(player, stack);
        }

        private PowerResult<Void> costMana(Player player, ItemStack stack) {
            InfPlugin plugin = InfExtentionPlugin.getPlugin(InfPlugin.class);
            if (plugin.isEnabled()){
                InfVarApi varApi = plugin.getVarApi();
                VarMana mana = varApi.getMana(player);
                mana.drop(getUseMana(), varApi.getTick());
            }
            return PowerResult.ok();
        }

        public long getCooldown() {
            return cooldown;
        }

        public boolean isRequireHurtByEntity() {
            return requireHurtByEntity;
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
            return Mana.this;
        }

        @Override
        public PowerResult<Void> tick(Player player, ItemStack stack) {
            return fire(player, stack);
        }
    }
}
