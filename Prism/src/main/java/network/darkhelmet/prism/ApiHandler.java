package network.darkhelmet.prism;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import network.darkhelmet.prism.bridge.PrismBlockEditHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;

public class ApiHandler {

    public enum WEType {
        WORLDEDIT("WorldEdit"),
        ASYNC_WORLDEDIT("AsyncWorldEdit"),
        FAST_ASYNC_WORLDEDIT("FastAsyncWorldEdit");

        private final String pluginId;

        WEType(String pluginId) {
            this.pluginId = pluginId;
        }

        public String getPluginId() {
            return pluginId;
        }
    }

    private static final Collection<String> enabledPlugins = new ArrayList<>();
    public static WorldEditPlugin worldEditPlugin = null;
    private static PrismBlockEditHandler handler;
    private static WEType weType = null;

    private ApiHandler() {
    }

    static void hookWorldEdit() {
        if (Bukkit.getServer().getPluginManager().getPlugin("FastAsyncWorldEdit") != null) {
            weType = WEType.FAST_ASYNC_WORLDEDIT;
        } else if (Bukkit.getServer().getPluginManager().getPlugin("AsyncWorldEdit") != null) {
            weType = WEType.ASYNC_WORLDEDIT;
        } else if (Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") != null) {
            weType = WEType.WORLDEDIT;
        }

        final Plugin we = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if (we != null) {
            worldEditPlugin = (WorldEditPlugin) we;
            enabledPlugins.add(we.getName());
            // Easier and foolproof way.
            try {
                handler = new PrismBlockEditHandler(weType);
                WorldEdit.getInstance().getEventBus().register(handler);
                Prism.log(weType.pluginId + " found. Associated features enabled.");
            } catch (Throwable error) {
                Prism.log("Required WorldEdit version is 7.1.0 or greater!"
                        + " Certain optional features of Prism disabled.");
                Prism.debug(error.getMessage());
            }

        } else {
            Prism.log("WorldEdit not found. Certain optional features of Prism disabled.");
        }
    }

    static boolean checkDependency(String pluginName) {
        return ApiHandler.enabledPlugins.contains(pluginName);
    }
    
    static boolean disableWorldEditHook() {
        if (worldEditPlugin != null) {
            try {
                WorldEdit.getInstance().getEventBus().unregister(handler);
                Prism.log(weType.pluginId + " unhooked");
                enabledPlugins.remove(worldEditPlugin.getName());
                worldEditPlugin = null;
                return true;
            } catch (Throwable error) {
                Prism.log("We could not unhook worldEdit...was it enabled???");
                Prism.debug(error.getMessage());
                return false;
            }
        } else {
            return true;
        }
    }
}
