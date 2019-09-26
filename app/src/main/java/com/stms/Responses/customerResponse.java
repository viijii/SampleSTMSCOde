package com.stms.Responses;

public class customerResponse {

    String status,message;
    public String getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }

    public customerResponse.data[] getData() {
        return data;
    }

    data[] data;

    public  class  data{

        public String getMobile() {
            return mobile;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getNoOfVeh() {
            return noOfVeh;
        }

        public String getAddress() {
            return address;
        }

        String mobile;
        String customerName;
        String noOfVeh;
        String address;

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        String customerId;
    }
}
