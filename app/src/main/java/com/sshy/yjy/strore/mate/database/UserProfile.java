package com.sshy.yjy.strore.mate.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

/**
 * Created by 周正尧 on 2018/3/15 0015.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */

@Entity(nameInDb = "user_profile")
public class UserProfile {

    @Id
    private String customerId = null;
    private String mobile = null;

    @Generated(hash = 292935378)
    public UserProfile(String customerId, String mobile) {
        this.customerId = customerId;
        this.mobile = mobile;
    }

    @Generated(hash = 968487393)
    public UserProfile() {
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}