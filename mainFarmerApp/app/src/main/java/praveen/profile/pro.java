package praveen.profile;

public class pro {
    String id;
    String name;
    String mobile;
    String address;
    String aadhar;

    public pro(){

    }

    public pro(String id, String name, String mobile, String address, String aadhar) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.aadhar = aadhar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

}
