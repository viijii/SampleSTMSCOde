package com.stms.Responses;

public class Spinner_response {

    String status,message;
    data[] data;

    public Spinner_response.data[] getData() {
        return data;
    }

    public void setData(Spinner_response.data[] data) {
        this.data = data;
    }

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
    public class data {
        String cus;

        public String getCus() {
            return cus;
        }

        public void setCus(String cus) {
            this.cus = cus;
        }

        public String getVeh() {
            return veh;
        }

        public void setVeh(String veh) {
            this.veh = veh;
        }

        String veh;

        public String getVehModel() {
            return vehModel;
        }

        public void setVehModel(String vehModel) {
            this.vehModel = vehModel;
        }

        String vehModel;

        }
    }
