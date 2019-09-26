package com.stms.Responses;

public class VehicalResponse {

String status,message;

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

    public VehicalResponse.data[] getData() {
        return data;
    }

    public void setData(VehicalResponse.data[] data) {
        this.data = data;
    }

    data[] data;

    public class data{
       String customerName,mobile,vehicleId,vehicleType,purchaseDate,noOfService,nextServiceDue;

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public String getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(String purchaseDate) {
            this.purchaseDate = purchaseDate;
        }

        public String getNoOfService() {
            return noOfService;
        }

        public void setNoOfService(String noOfService) {
            this.noOfService = noOfService;
        }

        public String getNextServiceDue() {
            return nextServiceDue;
        }

        public void setNextServiceDue(String nextServiceDue) {
            this.nextServiceDue = nextServiceDue;
        }
    }
}
