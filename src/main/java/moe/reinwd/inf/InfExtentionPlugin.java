package moe.reinwd.inf;

import org.bukkit.plugin.java.JavaPlugin;
import think.rpgitems.power.PowerManager;

public class InfExtentionPlugin extends JavaPlugin {
    public static InfExtentionPlugin plugin;
    InfExtEvent infExtEvent;

    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;
        infExtEvent = new InfExtEvent();

        getServer().getPluginManager().registerEvents(infExtEvent, this);
        PowerManager.registerPowers(this, "moe.reinwd.inf.power.impl");
        PowerManager.registerConditions(this, "moe.reinwd.inf.power.cond");
        PowerManager.registerMarkers(this, "moe.reinwd.inf.power.marker");
    }
}
