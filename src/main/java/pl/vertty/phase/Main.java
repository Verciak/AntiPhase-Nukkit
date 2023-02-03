package pl.vertty.phase;

import cn.nukkit.plugin.PluginBase;
import pl.vertty.phase.listener.PhaseModule;

public class Main extends PluginBase {

    private static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getLogger().alert("§3§lSaving config.yml");
        this.saveDefaultConfig();
        getLogger().alert("§3§lLoading events....");
        getServer().getPluginManager().registerEvents(new PhaseModule(), this);
        getLogger().alert("§3§lEvents loaded");
        PluginMetrics.metricsStart();
        getLogger().alert("§3§lThe plugin works properly, in case of problems, please visit here: https://discord.gg/AahXrsPvAq");
    }

    public static Main getPlugin() {
        return plugin;
    }
}