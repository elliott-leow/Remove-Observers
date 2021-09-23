package me.elliottleow.antiObservers;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener {
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        Bukkit.getLogger().log(Level.INFO, "[DisableObservers] Plugin Enabled");
    }
    
    public void onDisable() {
        Bukkit.getLogger().log(Level.INFO, "[DisableObservers] Plugin Disabled");
    }
    
    @EventHandler
    public void place(final BlockPlaceEvent event) {
        if (event.getBlockPlaced().getTypeId() == 218) {
            final Player player = event.getPlayer();
            event.setCancelled(true);
            for (int i = 0; i < player.getInventory().getSize(); i++) {
                final ItemStack itm = player.getInventory().getItem(i);
                if (itm != null && itm.getType().equals(Material.OBSERVER)) {
                    final int amt = itm.getAmount() - 1;
                    itm.setAmount(amt);
                    player.getInventory().setItem(i, (amt > 0) ? itm : null);
                    player.updateInventory();
                    break;
                }
            }
            player.sendMessage("Placing observers is banned.");
        }
    }
}
