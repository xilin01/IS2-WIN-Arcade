package business.packages;

import java.util.Collection;
import integration.packages.DAOGamesPackage;
import integration.packages.DAOGamesPackageImp;

public class SAGamesPackageImp implements SAGamesPackage {
    private DAOGamesPackage daoGamesPackage;

    public SAGamesPackageImp() {
        this.daoGamesPackage = new DAOGamesPackageImp();
    }

    @Override
    public int createGamesPackage(tGamesPackage gamesPackage) {
        return daoGamesPackage.createGamesPackage(gamesPackage);
    }

    @Override
    public tGamesPackage readGamesPackage(String name) {
        return daoGamesPackage.readGamesPackage(name);
    }

    @Override
    public int updateGamesPackage(tGamesPackage gamesPackage) {
        return daoGamesPackage.updateGamesPackage(gamesPackage);
    }

    @Override
    public int deleteGamesPackage(String name) {
        return daoGamesPackage.deleteGamesPackage(name);
    }

    @Override
    public Collection<tGamesPackage> readAllGamesPackages() {
        return daoGamesPackage.readAllGamesPackages();
    }
}
