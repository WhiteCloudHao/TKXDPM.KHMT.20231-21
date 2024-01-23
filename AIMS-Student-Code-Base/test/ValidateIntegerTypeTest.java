import controller.AdminController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ValidateIntegerTypeTest {

    @BeforeEach
    public void SetUp() {
        AdminController adminController = new AdminController();
    }

    @ParameterizedTest
    @CsvSource({
            "12 ,price, true",
            "a,quantity, false",
            "c,value, false",
            "12 ,numOfPages, true",
            "12,runtime, true",
            "12,author, false",
    })

    public void testValidateAddress(String value, String type, boolean expected) {
        boolean isValid = AdminController.validateIntegerType(value, type);
        Assertions.assertEquals(expected, isValid);
    }
}
