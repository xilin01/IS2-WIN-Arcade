package integration;

import java.util.Collection;
import business.tGamesPackage;

public interface DAOGamesPackage {
	
    int createGamesPackage(tGamesPackage gamesPackage);
    tGamesPackage readGamesPackage(String name);
    int updateGamesPackage(tGamesPackage gamesPackage);
    int deleteGamesPackage(String name);
    Collection<tGamesPackage> readAllGamesPackages();
}
