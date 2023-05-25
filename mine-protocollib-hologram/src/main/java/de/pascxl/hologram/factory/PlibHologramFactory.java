package de.pascxl.hologram.factory;

import org.bukkit.Location;

public class PlibHologramFactory {
    public PlibHologram createHologram(Location location, String hologramName) {
        return new PlibHologram(location, hologramName);
    }
}
