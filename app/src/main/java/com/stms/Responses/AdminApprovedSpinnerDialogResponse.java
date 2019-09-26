package com.stms.Responses;

public class AdminApprovedSpinnerDialogResponse {


    String status;

    public String getStatus() {
        return status;
    }


    public AdminApprovedSpinnerDialogResponse.data[] getData() {
        return data;
    }

    data[] data;

    public class data {

        public String getName() {

            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        String name;

        public String getEmpId() {
            return empId;
        }

        public void setEmpId(String empId) {
            this.empId = empId;
        }

        String empId;


    }
}