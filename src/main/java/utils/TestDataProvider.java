package utils;

import org.testng.annotations.DataProvider;
public class TestDataProvider {

    @DataProvider(name = "accountData")
    public static Object[][] accountData() {
        return new Object[][]{
                {"chrome", "user1", "password1"},
                {"firefox", "user2", "password2"}
        };
    }
}