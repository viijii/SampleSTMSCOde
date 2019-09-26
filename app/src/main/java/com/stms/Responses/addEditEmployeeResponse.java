package com.stms.Responses;

public class addEditEmployeeResponse {
    String status, message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public addEditEmployeeResponse.data[] getData() {
        return data;
    }

    data[] data;

    public class data {
        public String getFullName() {
            return fullName;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public String getAadhar() {
            return aadhar;
        }

        public String getCity() {
            return city;
        }

        public String getEmpId() {
            return empId;
        }

        public String getEmail() {
            return email;
        }

        public String getCurrentAddress() {
            return currentAddress;
        }

        public String getJoiningDate() {
            return joiningDate;
        }

        public String getPermanentAddress() {
            return permanentAddress;
        }

        public String getDesignation() {
            return designation;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public String getUserType() {
            return userType;
        }

        String fullName, mobileNumber, aadhar, city, empId, email, currentAddress, joiningDate, permanentAddress, designation, userName, password, userStatus, userType;

    }
}
