package resources;

import helper.JSONHelpers;
import org.testng.annotations.DataProvider;

public class UserTestLogin {
    @DataProvider
    public static Object[][] LoginDataFromJson(){
        return JSONHelpers.ReadJsonFromFile("src/test/java/resources/LoginInfo.json");
    }

}
