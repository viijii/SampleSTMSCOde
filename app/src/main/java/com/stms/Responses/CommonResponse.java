package com.stms.Responses;

/**
 * Created by mugdha on 8/19/18.
 */
public class CommonResponse
    {
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
    }
