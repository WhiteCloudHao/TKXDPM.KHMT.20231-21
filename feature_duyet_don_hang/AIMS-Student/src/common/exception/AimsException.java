package common.exception;;
//low coupling (data coupling)
/**
 * The AimsException wraps all unchecked exceptions You can use this
 * exception to inform
 * 
 * @author nguyenlm
 */

//functional cohesion
//Đây là một lớp ngoại lệ không kiểm soát được dùng để bao gồm tất cả các ngoại lệ không kiểm soát. Bạn có thể sử dụng nó để thông báo về các vấn đề liên quan đế
public class AimsException extends RuntimeException {

    public AimsException() {

	}

	public AimsException(String message) {
		super(message);
	}
}