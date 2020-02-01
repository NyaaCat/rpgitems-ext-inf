package moe.reinwd.inf.power.cond;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.item.ItemManager;
import think.rpgitems.item.RPGItem;
import think.rpgitems.power.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Meta(marker = true)
public class ArmorSetCondition extends InfCondition<Void> {
    @Property
    public String helmet = "";
    @Property
    public String chestplate = "";
    @Property
    public String legging = "";
    @Property
    public String boots = "";

    @Override
    public PowerResult<Void> check(Player player, ItemStack stack, Map<PropertyHolder, PowerResult<?>> context) {
        boolean result = checkHelmet(player) && checkChestPlate(player) && checkLegging(player) && checkBoots(player);
        return result ? PowerResult.ok() : PowerResult.fail();
    }

    private boolean checkHelmet(Player player) {
        Optional<RPGItem> rpgItem = ItemManager.toRPGItem(player.getInventory().getHelmet());
        Set<RPGItem> items = ItemManager.getItems(helmet);
        return helmet.equals("") || rpgItem.isPresent() && items.contains(rpgItem.get());
    }

    private boolean checkChestPlate(Player player) {
        Optional<RPGItem> rpgItem = ItemManager.toRPGItem(player.getInventory().getChestplate());
        Set<RPGItem> items = ItemManager.getItems(chestplate);
        return helmet.equals("") || rpgItem.isPresent() && items.contains(rpgItem.get());
    }

    private boolean checkLegging(Player player) {
        Optional<RPGItem> rpgItem = ItemManager.toRPGItem(player.getInventory().getLeggings());
        Set<RPGItem> items = ItemManager.getItems(legging);
        return helmet.equals("") || rpgItem.isPresent() && items.contains(rpgItem.get());
    }

    private boolean checkBoots(Player player) {
        Optional<RPGItem> rpgItem = ItemManager.toRPGItem(player.getInventory().getBoots());
        Set<RPGItem> items = ItemManager.getItems(boots);
        return helmet.equals("") || rpgItem.isPresent() && items.contains(rpgItem.get());
    }
    @Override
    public String getName() {
        return "armorset";
    }
}
