package pl.vertty.phase.listener;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockFence;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.level.Location;
import cn.nukkit.math.BlockFace;
import cn.nukkit.utils.Faceable;
import pl.vertty.phase.objects.Cooldown;
import pl.vertty.phase.utils.ChatUtilities;
import pl.vertty.phase.utils.Util;

public class PhaseModule implements Listener
{
    @EventHandler(ignoreCancelled = false)
    public void onPhase2(final PlayerMoveEvent e) {
        if (e.getTo().getY() < 0.0) {
            e.getPlayer().teleport(Util.getHighestLocation(e.getFrom()));
            return;
        }
        if (e.getFrom().clone().setComponents(e.getFrom().getX(), 0.0, e.getFrom().getZ()).distance(e.getTo().clone().setComponents(e.getTo().getX(), 0.0, e.getTo().getZ())) > 3.0) {
            e.getPlayer().teleport(e.getFrom());
            if (!Cooldown.getInstance().has(e.getPlayer(), "phase1")) {
                ChatUtilities.sendMessageToAdmins(Server.getInstance().getConfig().getString("notify.alert").replace("{PLAYER}", e.getPlayer().getName()).replace("{WHAT}", "movement was too distant"));
                Cooldown.getInstance().add(e.getPlayer(), "phase1", 2.0f);
            }
        }
        else if (e.getFrom().getFloorX() != e.getTo().getFloorX() || e.getFrom().getFloorZ() != e.getTo().getFloorZ() || e.getFrom().getFloorY() != e.getTo().getFloorY()) {
            if (this.inBlock(e.getFrom()) && this.inBlock(e.getTo())) {
                e.getPlayer().teleport(e.getFrom());
                if (!Cooldown.getInstance().has(e.getPlayer(), "phase2")) {
                    ChatUtilities.sendMessageToAdmins(Server.getInstance().getConfig().getString("notify.alert").replace("{PLAYER}", e.getPlayer().getName()).replace("{WHAT}", "trying to walk too wall when he is in wall"));
                    Cooldown.getInstance().add(e.getPlayer(), "phase2", 2.0f);
                }
                return;
            }
            if (!this.inBlock(e.getFrom()) && this.inBlock(e.getTo())) {
                e.getPlayer().teleport(e.getFrom());
                if (!Cooldown.getInstance().has(e.getPlayer(), "phase3")) {
                    ChatUtilities.sendMessageToAdmins(Server.getInstance().getConfig().getString("notify.alert").replace("{PLAYER}", e.getPlayer().getName()).replace("{WHAT}", "trying to walk too wall"));
                    Cooldown.getInstance().add(e.getPlayer(), "phase3", 2.0f);
                }
            }
        }
    }
    
    private boolean inBlock(final Location loc) {
        final Block b1 = Util.getBlock(loc.getLevel(), loc.getFloorX(), loc.getFloorY(), loc.getFloorZ(), false);
        final Location lup = loc.getSide(BlockFace.UP).getLocation();
        final Block b2 = Util.getBlock(loc.getLevel(), lup.getFloorX(), lup.getFloorY(), lup.getFloorZ(), false);
        return b1.isNormalBlock() && b2.isNormalBlock() && b1.getId() != 0 && b2.getId() != 0 && !(b1 instanceof BlockFence) && !(b2 instanceof BlockFence) && !(b1 instanceof Faceable) && !(b2 instanceof Faceable) && !b2.canPassThrough() && !b1.canPassThrough();
    }
}
