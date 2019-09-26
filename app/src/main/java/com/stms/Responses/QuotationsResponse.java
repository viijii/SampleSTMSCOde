package com.stms.Responses;

public class QuotationsResponse {
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

    public QuotationsResponse.data[] getData() {
        return data;
    }

    public void setData(QuotationsResponse.data[] data) {
        this.data = data;
    }

    data[] data;
    public class  data {
        String customerName;
        String mobileNumber;
        String quotationStatus;
        String vehType,quotationId;
        String email;
        String model;

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        String vehicleType;

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }



        public String getQuotationId() {
            return quotationId;
        }

        public void setQuotationId(String quotationId) {
            this.quotationId = quotationId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getQuotationStatus() {
            return quotationStatus;
        }

        public void setQuotationStatus(String quotationStatus) {
            this.quotationStatus = quotationStatus;
        }

        public String getVehType() {
            return vehType;
        }

        public void setVehType(String vehType) {
            this.vehType = vehType;
        }

        public String getVehModel() {
            return vehModel;
        }

        public void setVehModel(String vehModel) {
            this.vehModel = vehModel;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        String vehModel;


    }

}
