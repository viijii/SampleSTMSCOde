package com.stms.Responses;

public class view_VehicleResponse{

    String status;

    public String getStatus() {
        return status;
    }

    public String getCoins() {
        return coins;
    }

    String coins;

    public view_VehicleResponse.data[] getData() {
        return data;
    }

    public void setData(view_VehicleResponse.data[] data) {
        this.data = data;
    }

    data[] data;

    public class data{
        public String getVehicleid() {
            return vehicleid;
        }

        public void setVehicleid(String vehicleid) {
            this.vehicleid = vehicleid;
        }

        public String getVehicletype() {
            return vehicletype;
        }

        public void setVehicletype(String vehicletype) {
            this.vehicletype = vehicletype;
        }

        public String getTrackerid() {
            return trackerid;
        }

        public void setTrackerid(String trackerid) {
            this.trackerid = trackerid;
        }

        public String getTrackerpassword() {
            return trackerpassword;
        }

        public void setTrackerpassword(String trackerpassword) {
            this.trackerpassword = trackerpassword;
        }

        public String getPurchasedate() {
            return purchasedate;
        }

        public void setPurchasedate(String purchasedate) {
            this.purchasedate = purchasedate;
        }

        String vehicleid;
        String vehicletype;
        String trackerid;
        String trackerpassword;
        String purchasedate;
        String mobileNumber;
        String customername;
        String customerinfo;

        public String getNoofservices() {
            return noofservices;
        }

        public void setNoofservices(String noofservices) {
            this.noofservices = noofservices;
        }

        String noofservices;

        public String getEmpid() {
            return empid;
        }

        public void setEmpid(String empid) {
            this.empid = empid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }



        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAadhar() {
            return aadhar;
        }

        public void setAadhar(String aadhar) {
            this.aadhar = aadhar;
        }

        public String getJoiningdate() {
            return joiningdate;
        }

        public void setJoiningdate(String joiningdate) {
            this.joiningdate = joiningdate;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }



        public String getPermanentaddress() {
            return permanentaddress;
        }

        public void setPermanentaddress(String permanentaddress) {
            this.permanentaddress = permanentaddress;
        }

        String empid;
        String name;
        String email;

        public String getMobilenumber() {
            return mobilenumber;
        }

        public void setMobilenumber(String mobilenumber) {
            this.mobilenumber = mobilenumber;
        }

        String mobilenumber;
        String city;
        String aadhar;
        String joiningdate;
        String username;
        String password;

        public String getCurrentaddress() {
            return currentaddress;
        }

        public void setCurrentaddress(String currentaddress) {
            this.currentaddress = currentaddress;
        }

        String currentaddress;
        String permanentaddress;

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }



        public String getCustomername() {
            return customername;
        }

        public void setCustomername(String customername) {
            this.customername = customername;
        }







    }


}