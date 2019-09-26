package com.stms.Responses;

public class FieldActivityResponse {
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

    String status,message;

    public FieldActivityResponse.data[] getData() {
        return data;
    }

    data[] data;


    public class data{
       String title;
        String location;

        public String getTitle() {
            return title;
        }

        public String getLocation() {
            return location;
        }

        public String getPath() {
            return path;
        }

        String path;
    }
}
