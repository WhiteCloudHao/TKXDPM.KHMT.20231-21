import controller.AdminController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.SQLException;

public class LimitAllowUpdateTest {
    private AdminController adminController;

    @BeforeEach
    public void SetUp() {
        adminController = new AdminController();
    }

    @ParameterizedTest
    @CsvSource({
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "true",
            "false",
    })

    public void testValidateAddress(boolean expected) throws SQLException {
        boolean isValid = AdminController.checkAllowUpdate();
        Assertions.assertEquals(expected, isValid);
    }
}
