package pl.vertty.phase;

import pl.vertty.phase.objects.Metrics;
import pl.vertty.phase.utils.ChatUtilities;

import java.util.HashMap;
import java.util.Map;

public class PluginMetrics {
    public static void metricsStart() {
        try {
            int pluginId = 17628;
            Metrics metrics = new Metrics(Main.getPlugin(), pluginId);
            metrics.addCustomChart(new Metrics.AdvancedPie("player_platform", () -> {
                Map<String, Integer> valueMap = new HashMap<>();
                Main.getPlugin().getServer().getOnlinePlayers().forEach((uuid, player) -> {
                    String deviceOS = mapDeviceOSToString(player.getLoginChainData().getDeviceOS());
                    if (!valueMap.containsKey(deviceOS)) {
                        valueMap.put(deviceOS, 1);
                    } else {
                        valueMap.put(deviceOS, valueMap.get(deviceOS) + 1);
                    }
                });
                return valueMap;
            }));

            metrics.addCustomChart(new Metrics.AdvancedPie("player_game_version", () -> {
                Map<String, Integer> valueMap = new HashMap<>();
                Main.getPlugin().getServer().getOnlinePlayers().forEach((uuid, player) -> {
                    String gameVersion = player.getLoginChainData().getGameVersion();
                    if (!valueMap.containsKey(gameVersion)) {
                        valueMap.put(gameVersion, 1);
                    } else {
                        valueMap.put(gameVersion, valueMap.get(gameVersion) + 1);
                    }
                });
                return valueMap;
            }));
            Main.getPlugin().getLogger().info(ChatUtilities.colored("&3&lLoaded Metrics"));
        } catch (Exception e) {
            Main.getPlugin().getLogger().info(ChatUtilities.colored("&c&lCan't load metrics"));
        }
    }

    private static String mapDeviceOSToString(int os) {
        return switch (os) {
            case 1 -> "Android";
            case 2 -> "iOS";
            case 3 -> "macOS";
            case 4 -> "FireOS";
            case 5 -> "Gear VR";
            case 6 -> "Hololens";
            case 7 -> "Windows 10";
            case 8 -> "Windows";
            case 9 -> "Dedicated";
            case 10 -> "tvos";
            case 11 -> "PlayStation";
            case 12 -> "Switch";
            case 13 -> "Xbox One";
            case 14 -> "Windows Phone";
            default -> "Unknown";
        };
    }
}
