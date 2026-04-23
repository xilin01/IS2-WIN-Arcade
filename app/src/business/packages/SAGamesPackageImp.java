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
        if(daoGamesPackage.readGamesPackage(gamesPackage.getName()) == null) return daoGamesPackage.createGamesPackage(gamesPackage);
        return -1;
    }

    @Override
    public tGamesPackage readGamesPackage(String name) {
        return daoGamesPackage.readGamesPackage(name);
    }

    @Override
    public int updateGamesPackage(tGamesPackage gamesPackage) {
        if(daoGamesPackage.readGamesPackage(gamesPackage.getName()) != null) return daoGamesPackage.updateGamesPackage(gamesPackage);
        return -1;
    }

    @Override
    public int deleteGamesPackage(String name) {
        if(daoGamesPackage.readGamesPackage(name) != null) return daoGamesPackage.deleteGamesPackage(name);
        return -1;
    }

    @Override
    public Collection<tGamesPackage> readAllGamesPackages() {
        return daoGamesPackage.readAllGamesPackages();
    }
}
