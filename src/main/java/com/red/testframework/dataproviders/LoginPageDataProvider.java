package com.red.testframework.dataproviders;

import com.red.testframework.utils.Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

public class LoginPageDataProvider {

    @DataProvider(name = "getUsersAndPasswordsDataProvider")
    public static Object[][] getUsersAndPasswordsDataProvider() {
        return new Object[][]
                {
                        {"", ""},
                        {"", Utils.getProperty("admin.password")},
                        {"", "incorrectPassword"},
                        {Utils.getProperty("user.username"), ""},
                        {"hacker", ""},
                        {"mr.robot", "drowssap"},
                        {"neo", Utils.getProperty("user.password")},
                        {Utils.getProperty("user.username"), RandomStringUtils.random(256, true, true)},
                };
    }
}
