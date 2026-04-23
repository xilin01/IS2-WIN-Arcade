package business.packages;

import java.util.Collection;

public interface SAGamesPackage {
    int createGamesPackage(tGamesPackage gamesPackage);
    tGamesPackage readGamesPackage(String name);
    int updateGamesPackage(tGamesPackage gamesPackage);
    int deleteGamesPackage(String name);
    Collection<tGamesPackage> readAllGamesPackages();
}
