package Communication.common.info_packages;

import common.shared_data.UserLite;

import java.time.LocalDate;

public class BanUserPackage extends InfoPackage {
    public UserLite userToBan;
    public LocalDate endDate;
    public boolean isPermanent;
    public String explanation;
}
