package de.pascxl.hologram;

import de.pascxl.hologram.factory.PlibHologramFactory;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Created by Pascal K. on 25.05.2023.
 */
@Getter
public class HologramLib {

    @Getter
    private static HologramLib instance;

    private final Plugin plugin;
    private final Map<String, AbstractHologram> hologramMap = new HashMap<>();
    private PlibHologramFactory hologramFactory;

    public HologramLib(Plugin plugin) {
        this.plugin = plugin;
        if (instance != null) return;
        instance = this;
        hologramFactory = new PlibHologramFactory();
    }

    public AbstractHologram getHologram(String hologramID) {
        return this.hologramMap.get(hologramID);
    }

    public AbstractHologram createHologram(Location location, String name) {
        if (this.hologramMap.containsKey(name)) return null;
        AbstractHologram hologram = this.hologramFactory.createHologram(location, name);
        this.hologramMap.put(name, hologram);
        return hologram;
    }

    public void deleteHologram(String hologramID) {
        AbstractHologram hologram = this.hologramMap.remove(hologramID);
        if (hologram == null) {
            return;
        }
        Bukkit.getOnlinePlayers().forEach(hologram::hideFrom);
    }

    public void initPlayer(Player player) {
        this.hologramMap.values().forEach(hologram -> hologram.showTo(player));
    }

    public void initPlayer(Player player, Predicate<AbstractHologram> predicate) {
        this.hologramMap.values().stream().filter(predicate).toList().forEach(abstractHologram -> abstractHologram.showTo(player));
    }

}
