package entity.shipping;

import java.util.regex.Pattern;

public class DeliveryInfo {
    private String email;
    private String name;
   private String phoneNumber;
   private String province;
   private String shippingAddress;
    private String shippingInstruction;
    private boolean isRushShipping;
    private String rushShippingTime;
    private String rushShippingInstruction;


//   private boolean isNormalShipping;

    public DeliveryInfo(){
        email = "";
        name = "";
        phoneNumber = "";
        province = "";
        shippingAddress = "";
        shippingInstruction = "";
        isRushShipping = false;
        rushShippingTime = "";
        rushShippingInstruction = "";
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getShippingAddress() {
        return shippingAddress;
    }
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    public String getShippingInstruction() {
        return shippingInstruction;
    }
    public void setShippingInstruction(String shippingInstruction) {
        this.shippingInstruction = shippingInstruction;
    }
    public boolean isRushShipping() {
        return isRushShipping;
    }
    public void setRushShipping(boolean isRushShipping) {
        this.isRushShipping = isRushShipping;
    }
    public String getRushShippingTime() { return rushShippingTime; }
    public void setRushShippingTime(String rushShippingTime) {
        this.rushShippingTime = rushShippingTime;
    }
    public String getRushShippingInstruction() {
        return rushShippingInstruction;
    }
    public void setRushShippingInstruction(String rushShippingInstruction) {
        this.rushShippingInstruction = rushShippingInstruction;
    }
    
    public boolean isUrban(){
        if(getProvince().toLowerCase().contains("hà nội") || getProvince().toLowerCase().contains("hồ chí minh")) return true;
        return false;
    }

    public String validateDeliveryInfo(){
        if(!validateName()) return "INVALID_NAME";
        if(!validateEmail()) return "INVALID_EMAIL";
        if(!validatePhoneNumber()) return "INVALID_PHONENUMBER";
        return "VALID";
    }
    public boolean validatePhoneNumber() {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        if (!phoneNumber.matches("\\d+")) {
            return false;
        }
        if (phoneNumber.length() != 10) {
            return false;
        }
        return phoneNumber.charAt(0) == '0';
    }

    // Check name contains only alphabetic characters and spaces
    public boolean validateName() {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return name.matches("^[a-zA-Z ]*$");
    }

    public boolean validateEmail() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email == null || email.isEmpty()) {
            return false;
        }
        return Pattern.matches(emailRegex, email);
    }

    public boolean validateAddressPlaceRushOrder() {
        if (province == null || province.isEmpty()) return false;
        return isUrban();
    }

}
