package Data;

import Utils.JSONFileManger;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;

import java.io.FileReader;

public class dataProv {
    private static final Logger log = LoggerFactory.getLogger(dataProv.class);
    JSONFileManger jsonFileManger =
            new JSONFileManger("src/main/resources/AddEmployee.json");

    @DataProvider(name = "validLoginData")
    public Object[][] loginData() {
        return new Object[][]{
                {"Admin", "admin123"},
        };
    }

    @DataProvider(name = "invalidLoginDataWithMessages")
    public Object[][] invalidloginData() {
        return new Object[][]{
                {"Admin", "wrongPassword","Invalid credentials"},
                {"wrongUser", "admin123", "Invalid credentials"},
                {"wrongUser", "wrongPassword", "Invalid credentials"},
                {"Admssin", "wrongPdssdassword","Invalid credentials"},
                {"wrosdsdngUser", "admisdsdn123", "Invalid credentials"},
                {"wrongsdUser", "wrongPasdsdssword", "Invalid credentials"}
        };
    }

    @DataProvider(name = "addEmployeeData")
    public Object[][] addEmployeeData() {

        Object[][] data = new Object[10][6];

        for (int i = 1; i <= 10; i++) {

            String user = "user" + i;

            data[i - 1] = new Object[]{
                    jsonFileManger.getValue(user + ".firstName"),
                    jsonFileManger.getValue(user + ".middleName"),
                    jsonFileManger.getValue(user + ".lastName"),
                    jsonFileManger.getValue(user + ".username"),
                    jsonFileManger.getValue(user + ".password"),
                    jsonFileManger.getValue(user + ".confirmPassword")
            };
        }

        return data;
    }
    @DataProvider(name = "searchUser1Data")
    public Object[][] searchUser1Data() {

        return new Object[][]{
                {
                        jsonFileManger.getValue("user1.username")
                }
        };
    }


    @DataProvider(name = "addressData")
    public Object[][] addressData() {
        return new Object[][]{
                {
                        "123 Test Street",
                        "Building 5",
                        "Cairo",
                        "Cairo",
                        "11511",
                        "Egypt",
                        "0223456789",
                        "01012345678",
                        "0223456790",
                        "abdallahwork@example.com",
                        "abdallahother@example.com"
                }
        };
    }

    @DataProvider(name = "addEmergencyContactData")
    public Object[][] addEmergencyContactData() {
        return new Object[][]{
                {"Ahmed Mohamed", "Brother", "0223456789", "01012345678", "0234567890"},
                {"Sara Ali", "Sister", "0229876543", "01112345678", "0234567891"},
                {"Mohamed Ahmed", "Friend", "0223456780", "01212345678", "0234567892"}
        };
    }




}
