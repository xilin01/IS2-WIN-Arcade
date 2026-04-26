package business.staff;

import java.util.Collection;

public interface SAStaff {
	
    int createStaff(tStaff staff);
    tStaff readStaff(String name);
    int updateStaff(tStaff staff);
    int deleteStaff(String name);
    Collection<tStaff> readAllStaffs();
}
