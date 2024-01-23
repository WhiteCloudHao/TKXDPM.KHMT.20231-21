import controller.AdminController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.SQLException;

public class LimitDeleteInSession {
    private AdminController adminController;

    @BeforeEach
    public void SetUp() {
        adminController = new AdminController();
    }

    @ParameterizedTest
    @CsvSource({
            "10, true",
            "10, true",
            "10, true",
            "10,false",
            "10,false",

    })

    public void testValidateAddress(int quantity, boolean expected) throws SQLException {
        boolean isValid = AdminController.checkAllowDelete(quantity);
        Assertions.assertEquals(expected, isValid);
    }
}
