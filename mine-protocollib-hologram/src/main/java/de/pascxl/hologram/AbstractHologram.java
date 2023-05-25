package de.pascxl.hologram;

import com.google.common.base.Preconditions;
import de.pascxl.hologram.factory.PlibHologramLine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHologram {

    private static final double LINE_SPACE = 0.27;

    private final List<PlibHologramLine> hologramLines = new ArrayList<>();
    private final String name;
    private Location location;

    public AbstractHologram(Location location, String name) {
        this.name = name;
        this.location = location;
    }

    private Location getRelativeLocationForIndex(int index) {
        return location.clone().add(0, -LINE_SPACE * index, 0);
    }

    public String getId() {
        return name;
    }

    public int size() {
        return hologramLines.size();
    }

    public void addLine(String line) {
        int nextIndex = size();
        Location lineLocation = getRelativeLocationForIndex(nextIndex);
        PlibHologramLine hologramLine = createLine(lineLocation);
        hologramLine.setText(line);
        hologramLines.add(hologramLine);
        Bukkit.getOnlinePlayers().forEach(hologramLine::showTo);
    }

    public void setLine(int index, String line) {
        Preconditions.checkArgument(index < size());
        PlibHologramLine hologramLine = hologramLines.get(index);
        hologramLine.setText(line);
    }

    public String getLine(int index) {
        Preconditions.checkArgument(index < size());
        return hologramLines.get(index).getText();
    }

    public Location getLocation() {
        return location;
    }

    public void teleport(Location target) {
        this.location = target;
        for (int index = 0; index < size(); index++) {
            Location lineLoc = getRelativeLocationForIndex(index);
            PlibHologramLine hologramLine = hologramLines.get(index);
            hologramLine.teleport(lineLoc);
        }
    }

    public void showTo(Player player) {
        hologramLines.forEach(line -> line.showTo(player));
    }

    public void hideFrom(Player player) {
        hologramLines.forEach(line -> line.hideFrom(player));
    }

    public void removeLine(int index) {
        Preconditions.checkArgument(index < size());
        PlibHologramLine line = hologramLines.remove(index);
        Bukkit.getOnlinePlayers().forEach(line::hideFrom);
        teleport(this.location);
    }

    protected abstract PlibHologramLine createLine(Location location);
}
