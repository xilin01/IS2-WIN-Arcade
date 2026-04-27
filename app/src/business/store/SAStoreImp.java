package business.store;

import java.util.Map;
import business.game.tGame;
import business.packages.tGamesPackage;
import business.player.SAInventory;
import business.player.SAPlayer;
import business.player.tPlayer;
import business.product.tProduct;

public class SAStoreImp implements SAStore {
    private final SAPlayer saPlayer;
    private final SAInventory saInventory;

    public SAStoreImp(SAPlayer saPlayer, SAInventory saInventory) {
        this.saPlayer = saPlayer;
        this.saInventory = saInventory;
    }

    @Override
    public boolean buyProduct(String playerName, tProduct product) {
        tPlayer player = saPlayer.readPlayer(playerName);
        
        if(player.getPoints() < product.getPrice()) return false;
        player.setPoints(player.getPoints() - product.getPrice());
        Map<tProduct, Integer> inv = player.getInventory().getProducts();
        inv.put(product, inv.getOrDefault(product, 0) + 1);
        saPlayer.updatePlayer(player);
        return true;
    }

    @Override
    public boolean buyPackage(String playerName, tGamesPackage pack) {
        tPlayer player = saPlayer.readPlayer(playerName);
        
        Map<tGame, Integer> inv = player.getInventory().getGames();
        for(Map.Entry<tGame, Integer> entry: pack.getGames().entrySet()) inv.put(entry.getKey(), inv.getOrDefault(entry.getKey(), 0) + entry.getValue());
        saInventory.updateInventory(player.getInventory());
        return true;
    }
}
