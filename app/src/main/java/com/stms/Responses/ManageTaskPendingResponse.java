package com.stms.Responses;

public class ManageTaskPendingResponse {


    public String getStatus() {
        return status;
    }

    String status;

    public ManageTaskPendingResponse.data[] getData() {
        return data;
    }

    data[] data;

    public class  data{
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
        String instruments;
        String assignedToName;

        public String getAssignedByName() {
            return assignedByName;
        }

        public void setAssignedByName(String assignedByName) {
            this.assignedByName = assignedByName;
        }

        String assignedByName;

        public String getStatus() {
            return status;
        }

        String status;


        public String getInstruments() {
            return instruments;
        }

        public String getAssignedToName() {
            return assignedToName;
        }

        public String getEstimatedDays() {
            return estimatedDays;
        }

        String estimatedDays;

        public String getAssignedTo() {
            return assignedTo;
        }

        public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
        }

        String assignedTo;

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

