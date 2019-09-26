package com.stms.Responses;

public class AdminRequestedResponse {


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

    public AdminRequestedResponse.data[] getData() {
        return data;
    }

    data[] data;
    public class data

    {

        String complaintId;
        String title;
        String description;
        String imagePath;
        String vehicleId;
        String vehicleType;
        String mobileNumber;
        String noTasks;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        String taskId;



        public String getNoTasks() {
            return noTasks;
        }

        public String getCustomerId() {
            return customerId;
        }

        String customerId;
        public String getComplaintId () {
            return complaintId;
        }

        public String getTitle () {
            return title;
        }

        public String getDescription () {
            return description;
        }

        public String getImagePath () {
            return imagePath;
        }

        public String getVehicleId () {
            return vehicleId;
        }

        public String getVehicleType () {
            return vehicleType;
        }

        public String getMobileNumber () {
            return mobileNumber;
        }

        public String getName () {
            return name;
        }

        String name;

    }
}