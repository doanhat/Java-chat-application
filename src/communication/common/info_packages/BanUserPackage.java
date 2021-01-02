package communication.common.info_packages;

import common.shared_data.UserLite;

import java.time.LocalDate;

public class BanUserPackage extends InfoPackage {
    private UserLite  userToBan;
    private LocalDate endDate;
    private boolean   isPermanent;
    private String    explanation;


    public UserLite getUserToBan() {
        return userToBan;
    }

    public void setUserToBan(UserLite userToBan) {
        this.userToBan = userToBan;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
