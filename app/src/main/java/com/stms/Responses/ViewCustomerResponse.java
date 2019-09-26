package com.stms.Responses;

public class ViewCustomerResponse {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    public ViewCustomerResponse.data[] getData() {
        return data;
    }

    public void setData(ViewCustomerResponse.data[] data) {
        this.data = data;
    }

    ViewCustomerResponse.data[] data;


    public class data {
        String name;
        String email;
        String mobileNumber;
        String aadhar;
        String username;
        String password;
        String city;
        String currentAddress;
        String permanentAddress;
        String vehicleId;

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public String getAadhar() {
            return aadhar;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getCity() {
            return city;
        }

        public String getCurrentAddress() {
            return currentAddress;
        }

        public String getPermanentAddress() {
            return permanentAddress;
        }

        public String getVehicleId() {
            return vehicleId;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        String vehicleType;
    }
}


