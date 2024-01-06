import controller.AdminController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CheckAllowDeleteTest {
    private AdminController adminController;

    @BeforeEach
    public void SetUp() {
        adminController = new AdminController();
    }

    @ParameterizedTest
    @CsvSource({
            "12 ,13, true",
            "12,19, false",
            "12,1, false",
    })

    public void testValidateAddress(int value, int price, boolean expected) {
        boolean isValid = AdminController.validatePrice(value, price);
        Assertions.assertEquals(expected, isValid);
    }
}
