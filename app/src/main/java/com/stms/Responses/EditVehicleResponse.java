package com.stms.Responses;

public class EditVehicleResponse {
    String status;

    public EditVehicleResponse.data[] getData() {
        return data;
    }

    public void setData(EditVehicleResponse.data[] data) {
        this.data = data;
    }

    data[] data;

    public class data {
        String customerName;
        String mobile;
        String vehicleId;
        String vehicleType;
        String purchaseDate;
        String noOfService;
        String nextService;
        String trackerId;

        public String getVehicleModel() {
            return vehicleModel;
        }

        public void setVehicleModel(String vehicleModel) {
            this.vehicleModel = vehicleModel;
        }

        String vehicleModel;

        public String getTrackerId() {
            return trackerId;
        }

        public void setTrackerId(String trackerId) {
            this.trackerId = trackerId;
        }

        public String getTrackerPassword() {
            return trackerPassword;
        }

        public void setTrackerPassword(String trackerPassword) {
            this.trackerPassword = trackerPassword;
        }

        String trackerPassword;






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

        public String getNextService() {
            return nextService;
        }

        public void setNextService(String nextService) {
            this.nextService = nextService;
        }




    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String message;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
