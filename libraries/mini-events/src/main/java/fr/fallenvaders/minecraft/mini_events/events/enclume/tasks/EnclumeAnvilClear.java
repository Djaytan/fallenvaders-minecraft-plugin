package fr.fallenvaders.minecraft.mini_events.events.enclume.tasks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.events.GetEventWorld;
import fr.fallenvaders.minecraft.mini_events.events.enclume.EnclumeCalculs;

public class EnclumeAnvilClear extends BukkitRunnable {

    private int lowestPointX;
    private int lowestPointZ;
    private int highestPointX;
    private int highestPointZ;
    private int groundY;

    public EnclumeAnvilClear(int lowestPointX, int lowestPointZ, int highestPointX, int highestPointZ, int groundY) {
        this.lowestPointX = lowestPointX;
        this.lowestPointZ = lowestPointZ;
        this.highestPointX = highestPointX;
        this.highestPointZ = highestPointZ;
        this.groundY = groundY;
    }


    @Override
    public void run() {
        GameName name = MiniEvents.getGame().getGameName();
        GameState state = MiniEvents.getGame().getGameState();
        if (name == GameName.ENCLUME && state == GameState.PLAYING) {
            clearAnvilsOnGround();
        } else {
            cancel();
        }
    }

    private void clearAnvilsOnGround() {
        int x = lowestPointX;
        int z = lowestPointZ;

        while (x <= highestPointX) {
            while (z <= highestPointZ) {
                World worldName = GetEventWorld.getWorld();
                Location loc = new Location(worldName, x, groundY, z);
                if (loc.getBlock().getType() == Material.ANVIL) {
                    loc.getBlock().setType(Material.AIR);
                } else {
                    z++;
                }
            }
            z = lowestPointZ;
            x++;
        }
        return;
    }

    public static void ClearParameters(int groundY) {
        String pathToParameters = ("config-event.enclume.spawning-area.");
        // Premier point de la zone
        int x1 = MiniEvents.PLUGIN.getConfig().getInt(pathToParameters + "first-point.x");
        int z1 = MiniEvents.PLUGIN.getConfig().getInt(pathToParameters + "first-point.z");
        // Deuxieme point de la zone
        int x2 = MiniEvents.PLUGIN.getConfig().getInt(pathToParameters + "second-point.x");
        int z2 = MiniEvents.PLUGIN.getConfig().getInt(pathToParameters + "second-point.z");

        int lowestPointX = EnclumeCalculs.getLowestPointX(x1, x2);
        int lowestPointZ = EnclumeCalculs.getLowestPointZ(z1, z2);

        int highestPointX = EnclumeCalculs.getHighestPointX(x1, x2);
        int highestPointZ = EnclumeCalculs.getHighestPointZ(z1, z2);

        EnclumeAnvilClear start = new EnclumeAnvilClear(lowestPointX, lowestPointZ, highestPointX, highestPointZ, groundY);
        start.runTaskTimer(MiniEvents.PLUGIN, 0, 5);

    }
}
