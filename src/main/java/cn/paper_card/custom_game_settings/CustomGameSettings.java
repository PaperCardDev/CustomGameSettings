package cn.paper_card.custom_game_settings;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class CustomGameSettings extends JavaPlugin {

    @Override
    public void onEnable() {

        // 设置默认游戏模式为生存模式
        this.getLogger().info("DefaultGameMode: SURVIVAL");
        this.getServer().setDefaultGameMode(GameMode.SURVIVAL);

        // 设置最大在线人数
        final int max = Math.max(99, this.getServer().getOfflinePlayers().length);
        this.getLogger().info("MaxPlayers: %d".formatted(max));
        this.getServer().setMaxPlayers(max);


        // 设置重生半径为0
        this.getServer().setSpawnRadius(0);

        this.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void on(@NotNull ServerLoadEvent event) {
                getServer().getGlobalRegionScheduler().run(CustomGameSettings.this, scheduledTask -> {
                    for (final World world : getServer().getWorlds()) {
                        // 设置重生半径为0
                        world.setGameRule(GameRule.SPAWN_RADIUS, 0);
                        // 设置游戏难度为困难模式
                        world.setDifficulty(Difficulty.HARD);
                        // 开启所有世界的死亡不掉落
                        world.setGameRule(GameRule.KEEP_INVENTORY, true);
                        // 调整睡觉比例为四分之一
                        world.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 25);
                    }
                });
            }
        }, this);


    }
}
