package pl.vertty.phase.utils;


import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.TextFormat;

public class ChatUtilities {

    public static String colored(final String s) {
        if (s == null) {
            return "";
        }
        return TextFormat.colorize('&', s);
    }

    public static void sendMessageToAdmins(String message) {
        for (Player player : Server.getInstance().getOnlinePlayers().values()) {
            if (player != null) {
                if (player.hasPermission(Server.getInstance().getConfig().getString("notify.permission"))) {
                    player.sendMessage(colored(message));
                }
            }
        }
    }
}
