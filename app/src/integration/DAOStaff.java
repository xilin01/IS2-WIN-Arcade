package integration;

import java.util.Collection;
import business.tStaff;

public interface DAOStaff {
	
    int createStaff(tStaff staff);
    tStaff readStaff(String name);
    int updateStaff(tStaff staff);
    int deleteStaff(String name);
    Collection<tStaff> readAllStaffs();
}
