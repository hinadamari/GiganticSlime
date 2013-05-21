package hinadamari.giganticslime;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * GiganticSlime
 * @author hinadamari
 */
public class GiganticSlime extends JavaPlugin implements Listener {

    /**
     * プラグインが有効になったときに呼び出されるメソッド
     * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
     */
    public void onEnable(){

        getServer().getPluginManager().registerEvents(this, this);

    }

    /**
     * スライムにスライムボールを使った時の処理
     * @param event
     */
    @EventHandler
    public void onPlayerInteractSlime(PlayerInteractEntityEvent event) {

    	Player p = event.getPlayer();

    	if (!p.hasPermission("giganticslime.feed")) return;

    	if (event.getRightClicked().getType() == EntityType.MAGMA_CUBE) {

    		if (p.getItemInHand().getType() != Material.MAGMA_CREAM) return;

    		MagmaCube slime = (MagmaCube) event.getRightClicked();
    		int hp = slime.getHealth();

    		// 体力が一定量を超えていればサイズと最大体力を増やす
    		if (hp >= Math.pow(slime.getSize(), 2)) {
    			if (slime.getSize() >= 100) {
    				p.sendMessage(ChatColor.YELLOW + "これ以上大きく出来ません");
    				return;
    			}
    			slime.setSize(slime.getSize() + 1);
    			slime.setMaxHealth((int) Math.pow(slime.getSize(), 2));
    		}
    		slime.setHealth(hp + 1);
    		p.playEffect(slime.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);

    	} else if (event.getRightClicked().getType() == EntityType.SLIME) {

    		if (p.getItemInHand().getType() != Material.SLIME_BALL) return;

    		Slime slime = (Slime) event.getRightClicked();
    		int hp = slime.getHealth();

    		// 体力が一定量を超えていればサイズと最大体力を増やす
    		if (hp >= Math.pow(slime.getSize(), 2)) {
    			if (slime.getSize() >= 100) {
    				p.sendMessage(ChatColor.YELLOW + "これ以上大きく出来ません");
    				return;
    			}
    			slime.setSize(slime.getSize() + 1);
    			slime.setMaxHealth((int) Math.pow(slime.getSize(), 2));
    		}
    		slime.setHealth(hp + 1);
    		p.playEffect(slime.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);

    	} else {
    		return;
    	}

    	if (p.getGameMode() != GameMode.CREATIVE)
			p.setItemInHand(consumeItem(p.getItemInHand()));

    }

    /**
     * アイテムを1個減らします
     * @param is
     * @return
     */
    public static ItemStack consumeItem(ItemStack is) {
    	if (is.getAmount() <= 1) {
    		return new ItemStack(Material.AIR, 1);
    	} else {
    		is.setAmount(is.getAmount() - 1);
    		return is;
    	}
    }

}
