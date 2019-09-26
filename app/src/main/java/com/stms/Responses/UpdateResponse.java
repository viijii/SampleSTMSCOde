package com.stms.Responses;

public class UpdateResponse {

    public String getStatus() {
        return status;
    }

    String status;

    public data[] getData() {
        return data;
    }

    data[] data;

    public class  data{

        public String getUpdateDescription() {
            return updateDescription;
        }

        String updateDescription;


    }
}
