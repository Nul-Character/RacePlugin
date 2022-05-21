package 插件.字符.种族插件.监听器;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.potion.PotionEffectType;

public class 吸血鬼处理器 implements Listener {

    @EventHandler
    public void 吸血(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.PLAYER))
            return;
        if (!event.getDamager().hasPermission("吸血鬼"))
            return;
        var 伤害 = event.getFinalDamage();
        var 攻击者 = ((Player) event.getDamager());
        var 当前生命 = 攻击者.getHealth();
        var 生命 = 伤害 + 当前生命;
        攻击者.setHealth(Math.min(生命, 攻击者.getMaxHealth()));
    }

    @EventHandler
    public void 受伤(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType().equals(EntityType.PLAYER))
            return;
        if (!event.getEntity().hasPermission("吸血鬼"))
            return;
        if (!(event.getDamager() instanceof LivingEntity 攻击者))
            return;
        if (攻击者.getEquipment() == null)
            return;
        var 手持物品 = 攻击者.getEquipment().getItemInMainHand();
        var 亡灵杀手等级 = 手持物品.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
        event.setDamage(event.getDamage() + 亡灵杀手等级 * 2.5);
    }

    @EventHandler
    public void 受到瞬间伤害(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player 玩家))
            return;
        if (!event.getEntity().hasPermission("吸血鬼"))
            return;
        if (event.getCause().equals(EntityDamageEvent.DamageCause.MAGIC)) {
            event.setCancelled(true);
            double 血量 = 玩家.getHealth();
            血量 += event.getFinalDamage();
            玩家.setHealth(Math.min(玩家.getMaxHealth(), 血量));
        }
    }

    @EventHandler
    public void 受到瞬间治疗(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof Player 玩家))
            return;
        if (!event.getEntity().hasPermission("吸血鬼"))
            return;
        if (event.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.MAGIC)) {
            event.setCancelled(true);
            double 伤害 = event.getAmount();
            玩家.damage(伤害);
        }
    }

    @EventHandler
    public void 获得药水效果(EntityPotionEffectEvent event) {
        if (!(event.getEntity() instanceof Player 玩家))
            return;
        if (!event.getEntity().hasPermission("吸血鬼"))
            return;
        if (event.getNewEffect() == null)
            return;
        var 药水类型 = event.getModifiedType();
        if (药水类型.equals(PotionEffectType.POISON) || 药水类型.equals(PotionEffectType.REGENERATION)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerAirChange(EntityAirChangeEvent event) {
        if (!event.getEntity().getType().equals(EntityType.PLAYER))
            return;
        if (!event.getEntity().hasPermission("吸血鬼"))
            return;
        event.setAmount(3000);
    }

}
