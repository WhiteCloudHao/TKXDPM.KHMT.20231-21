package controller;

import entity.media.Media;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminController extends BaseController {
    public static boolean validatePrice(int value, int price) {
        return !(price >= 1.5 * value) && !(price <= 0.3 * value);
    }

    public static boolean lengthOfListDeleteMedia(int length) {
        return length <= 10 && length >= 0;
    }

    public static boolean validateIntegerType(String value, String type) {
        String[] types = {"price", "quantity", "value", "numOfPages", "runtime"};

        boolean isValidType = false;
        for (String validType : types) {
            if (validType.equals(type)) {
                isValidType = true;
                break;
            }
        }
        if (isValidType) {
            try {
                Integer.parseInt(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return false;
    }

    public static boolean checkAllowDelete(int quantity) throws SQLException {
        int leftAllow = Media.getMedia().allowedDelete;
        if (leftAllow < quantity)
            return false;
        else {
            Media.getMedia().decreaseDelete(quantity);
            return true;
        }

    }

    public static boolean checkAllowUpdate() throws SQLException {
        int leftAllow = Media.getMedia().allowedUpdate;
        if (leftAllow < 1)
            return false;
        else {
            Media.getMedia().decreaseUpdate();
            return true;
        }

    }

}
