package com.stms.Responses;

public class MyTaskResponse {

    public String getStatus() {
        return status;
    }

    String status;

    public MyTaskResponse.data[] getData() {
        return data;
    }

    data[] data;

    public class  data{
        public String getAssignedTo() {
            return assignedTo;
        }

        public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
        }

        String assignedTo;

        public String getStatus() {
            return status;
        }

        String status;

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        String imagePath;
        public String getTaskId() {
            return taskId;
        }

        public String getTaskTitle() {
            return taskTitle;
        }

        public String getComplaintId() {
            return complaintId;
        }

        public String getAssignedTime() {
            return assignedTime;
        }

        public String getTargetTime() {
            return targetTime;
        }

        public String getCompletedTime() {
            return completedTime;
        }

        public String getTaskStatus() {
            return taskStatus;
        }

        public String getVehicleId() {
            return vehicleId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public String getName() {
            return name;
        }

        public String getTimeLeft() {
            return timeLeft;
        }

        String taskId;
        String taskTitle;
        String complaintId;
        String assignedTime;
        String targetTime;
        String completedTime;
        String taskStatus;
        String vehicleId;
        String customerId;
        String vehicleType;
        String name;
        String timeLeft;

        public String getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(String serviceStatus) {
            this.serviceStatus = serviceStatus;
        }

        String serviceStatus;

        public String getTrackerId() {
            return trackerId;
        }

        public String getTrackerPassword() {
            return trackerPassword;
        }

        String trackerId;
        String trackerPassword;
    }
}
