package dto;

public class OrderDto {

    private int id;
    private String email;
    private String address;
    private String phone;
    private int userID;
    private int shippingFee;
    private String status;
    private String rushShippingTime;
    private String province;
    private String shippingInstruction;
    private String rushShippingInstruction;
    private int isRushShipping;

    // Constructors, getters, and setters

    public OrderDto(int id, String email, String address, String phone, int userID, int shippingFee,
                    String status, String rushShippingTime, String province, String shippingInstruction,
                    String rushShippingInstruction, int isRushShipping) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.userID = userID;
        this.shippingFee = shippingFee;
        this.status = status;
        this.rushShippingTime = rushShippingTime;
        this.province = province;
        this.shippingInstruction = shippingInstruction;
        this.rushShippingInstruction = rushShippingInstruction;
        this.isRushShipping = isRushShipping;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(int shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRushShippingTime() {
        return rushShippingTime;
    }

    public void setRushShippingTime(String rushShippingTime) {
        this.rushShippingTime = rushShippingTime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getShippingInstruction() {
        return shippingInstruction;
    }

    public void setShippingInstruction(String shippingInstruction) {
        this.shippingInstruction = shippingInstruction;
    }

    public String getRushShippingInstruction() {
        return rushShippingInstruction;
    }

    public void setRushShippingInstruction(String rushShippingInstruction) {
        this.rushShippingInstruction = rushShippingInstruction;
    }

    public int getIsRushShipping() {
        return isRushShipping;
    }

    public void setIsRushShipping(int isRushShipping) {
        this.isRushShipping = isRushShipping;
    }
}
