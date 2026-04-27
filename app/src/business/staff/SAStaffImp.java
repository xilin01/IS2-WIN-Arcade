package business.staff;

import java.util.Collection;
import integration.staff.DAOStaff;
import integration.staff.DAOStaffImp;

public class SAStaffImp implements SAStaff {
    private DAOStaff daoStaff;

    public SAStaffImp() {
        this.daoStaff = new DAOStaffImp();
    }

    @Override
    public int createStaff(tStaff staff) {
        return daoStaff.createStaff(staff);
    }

    @Override
    public tStaff readStaff(String name) {
        return daoStaff.readStaff(name);
    }

    @Override
    public int updateStaff(tStaff staff) {
        return daoStaff.updateStaff(staff);
    }

    @Override
    public int deleteStaff(String name) {
        return daoStaff.deleteStaff(name);
    }

    @Override
    public Collection<tStaff> readAllStaffs() {
        return daoStaff.readAllStaffs();
    }
}
