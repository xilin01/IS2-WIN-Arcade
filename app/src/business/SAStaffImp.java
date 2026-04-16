package business;

import java.util.Collection;
import integration.DAOStaff;
import integration.DAOStaffImp;

public class SAStaffImp implements SAStaff {
    private DAOStaff daoStaff;

    public SAStaffImp() {
        this.daoStaff = new DAOStaffImp();
    }

    @Override
    public int createStaff(tStaff staff) {
        if(daoStaff.readStaff(staff.getName()) == null) return daoStaff.createStaff(staff);
        return -1;
    }

    @Override
    public tStaff readStaff(String name) {
        return daoStaff.readStaff(name);
    }

    @Override
    public int updateStaff(tStaff staff) {
        if(daoStaff.readStaff(staff.getName()) != null) return daoStaff.updateStaff(staff);
        return -1;
    }

    @Override
    public int deleteStaff(String name) {
        if(daoStaff.readStaff(name) != null) return daoStaff.deleteStaff(name);
        return -1;
    }

    @Override
    public Collection<tStaff> readAllStaffs() {
        return daoStaff.readAllStaffs();
    }
}
