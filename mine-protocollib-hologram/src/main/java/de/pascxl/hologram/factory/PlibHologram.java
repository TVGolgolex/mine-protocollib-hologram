package de.pascxl.hologram.factory;

import de.pascxl.hologram.AbstractHologram;
import org.bukkit.Location;

public class PlibHologram extends AbstractHologram {
    public PlibHologram(Location location, String name) {
        super(location, name);
    }

    @Override
    protected PlibHologramLine createLine(Location location) {
        return new PlibHologramLine(location);
    }
}
