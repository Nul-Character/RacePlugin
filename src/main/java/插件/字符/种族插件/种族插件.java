package 插件.字符.种族插件;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import 插件.字符.种族插件.监听器.吸血鬼处理器;

public final class 种族插件 extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new 吸血鬼处理器(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
