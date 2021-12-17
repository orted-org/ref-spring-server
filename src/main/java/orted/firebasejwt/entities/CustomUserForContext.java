package orted.firebasejwt.entities;

public class CustomUserForContext {
    private String phone;
    private String name;
    private String businessName;

    public CustomUserForContext() {
    }

    public CustomUserForContext(String phone, String name, String businessName) {
        this.phone = phone;
        this.name = name;
        this.businessName = businessName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "CustomUserForContext{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", businessName='" + businessName + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
