package controller;

//low coupling (data coupling) 
//HomeController sử dụng lớp Media để lấy danh sách tất cả các phương tiện từ cơ sở dữ liệu, nhưng cũng không có mức độ phụ thuộc cao vào Media đối với HomeController.

//Liskov Substitution
//HomeController mở rộng từ BaseController, nhưng không triển khai các phương thức checkMediaInCart và getListCartMedia từ BaseController. Điều này có thể làm cho việc thay thế BaseController bằng HomeController gây r

import java.sql.SQLException;
import java.util.List;

import entity.cart.Cart;
import entity.media.Media;

/**
 * This class controls the flow of events in homescreen
 * @author nguyenlm
 */
public class HomeController extends BaseController{


    /**
     * this method gets all Media in DB and return back to home to display
     * @return List[Media]
     * @throws SQLException
     */
	// control cohesion
	// Phương thức getAllMedia() làm việc trực tiếp với lớp Media để lấy danh sách tất cả các đối tượng Media từ cơ sở dữ liệu và trả về để hiển thị trên màn hình chính.
    public List getAllMedia() throws SQLException{
        return new Media().getAllMedia();
    }

}
