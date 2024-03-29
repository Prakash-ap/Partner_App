package Model;

public class Vehicle_User {

    private String id;

    private String name;
    private String mobile_no;
    private String emailid;
    private String address;
    private String vehicle_no;
    private String vehicle_make;
    private String vehicle_model;
    private String yor;
    private String rc_image_url;
    private String driver_license_image_url;
    private String dp_image_url;
    private String status;
    private String password;
    private String location;
    private double lat;
    private double lng;
    private  String admin;
    private String message;
    private String tokens;
    private String group;

    public Vehicle_User(String id, String name, String mobile_no, String emailid, String address, String vehicle_no, String vehicle_make, String vehicle_model, String yor, String rc_image_url, String driver_license_image_url, String dp_image_url, String status, String password, double lat, double lng, String admin, String tokens) {
        this.id = id;
        this.name = name;
        this.mobile_no = mobile_no;
        this.emailid = emailid;
        this.address = address;
        this.vehicle_no = vehicle_no;
        this.vehicle_make = vehicle_make;
        this.vehicle_model = vehicle_model;
        this.yor = yor;
        this.rc_image_url = rc_image_url;
        this.driver_license_image_url = driver_license_image_url;
        this.dp_image_url = dp_image_url;
        this.status = status;
        this.password = password;
        this.lat = lat;
        this.lng = lng;
        this.admin = admin;
        this.tokens = tokens;
    }

    public Vehicle_User(String userID, String names, String mobilenos, String emailds, String addresss, String vnos, String vms, String vmos, String yors, String rcimagess, String dlicesenes, String dpimages, String status, String password, double lat, double lng, String admin, String message, String token, String group) {
        this.id = userID;
        this.name = names;
        this.mobile_no = mobilenos;
        this.emailid = emailds;
        this.address = addresss;
        this.vehicle_no = vnos;
        this.vehicle_make = vms;
        this.vehicle_model = vmos;
        this.yor = yors;
        this.rc_image_url = rcimagess;
        this.driver_license_image_url = dlicesenes;
        this.dp_image_url = dpimages;
        this.status = status;
        this.password = password;
        this.lat = lat;
        this.lng = lng;
        this.admin = admin;
        this.message = message;
        this.tokens = token;
        this.group = group;
    }

    public Vehicle_User(String id, String name, String mobile_no, String emailid, String address, String vehicle_no, String vehicle_make, String vehicle_model, String yor, String rc_image_url, String driver_license_image_url, String dp_image_url, String status, String password, double lat, double lng, String admin, String message, String token) {
        this.id = id;
        this.name = name;
        this.mobile_no = mobile_no;
        this.emailid = emailid;
        this.address = address;
        this.vehicle_no = vehicle_no;
        this.vehicle_make = vehicle_make;
        this.vehicle_model = vehicle_model;
        this.yor = yor;
        this.rc_image_url = rc_image_url;
        this.driver_license_image_url = driver_license_image_url;
        this.dp_image_url = dp_image_url;
        this.status = status;
        this.password = password;
        this.lat = lat;
        this.lng = lng;
        this.admin = admin;
        this.message = message;
        this.tokens = token;
    }

    public Vehicle_User(String id, String name, String mobile_no, String emailid, String address, String vehicle_no, String vehicle_make, String vehicle_model, String yor, String rc_image_url, String driver_license_image_url, String dp_image_url, String status, String password, double lat, double lng, String admin) {
        this.id = id;
        this.name = name;
        this.mobile_no = mobile_no;
        this.emailid = emailid;
        this.address = address;
        this.vehicle_no = vehicle_no;
        this.vehicle_make = vehicle_make;
        this.vehicle_model = vehicle_model;
        this.yor = yor;
        this.rc_image_url = rc_image_url;
        this.driver_license_image_url = driver_license_image_url;
        this.dp_image_url = dp_image_url;
        this.status = status;
        this.password = password;
        this.lat = lat;
        this.lng = lng;
        this.admin = admin;
    }




    public Vehicle_User(String dp_image_url) {
        this.dp_image_url = dp_image_url;
    }




    public String getToken() {
        return tokens;
    }

    public void setToken(String token) {
        this.tokens = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Vehicle_User(String trim, String s) {
    }

    public Vehicle_User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getVehicle_make() {
        return vehicle_make;
    }

    public void setVehicle_make(String vehicle_make) {
        this.vehicle_make = vehicle_make;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public String getYor() {
        return yor;
    }

    public void setYor(String yor) {
        this.yor = yor;
    }

    public String getRc_image_url() {
        return rc_image_url;
    }

    public void setRc_image_url(String rc_image_url) {
        this.rc_image_url = rc_image_url;
    }

    public String getDriver_license_image_url() {
        return driver_license_image_url;
    }

    public void setDriver_license_image_url(String driver_license_image_url) {
        this.driver_license_image_url = driver_license_image_url;
    }

    public String getDp_image_url() {
        return dp_image_url;
    }

    public void setDp_image_url(String dp_image_url) {
        this.dp_image_url = dp_image_url;
    }

    public String getTokens() {
        return tokens;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
