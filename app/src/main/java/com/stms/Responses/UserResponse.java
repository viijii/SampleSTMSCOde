package com.stms.Responses;

/**
 * Created by mugdha on 8/19/18.
 */
public class UserResponse
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
        public String getestatus(){ return  estatus;}

        public void setestatus(String estatus){ this.estatus=estatus;}
        public String getmobile(){ return  mobile;}

        public void setmobile(String mobile){ this.mobile=mobile;}
        public String getdesignation(){ return  designation;}

        public void setdesignation(String designation){ this.designation=designation;}
        String status,message,estatus,designation,mobile;

        public UserResponse.data[] getData() {
            return data;
        }

        public void setData(UserResponse.data[] data) {
            this.data = data;
        }

        data[] data;

        public class data
            {
                public String getestatus(){
                    return  estatus;}

                public void setestatus(String estatus){ this.estatus=estatus;}
                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getAlt_phoneno() {
                    return alt_phoneno;
                }

                public void setAlt_phoneno(String alt_phoneno) {
                    this.alt_phoneno = alt_phoneno;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getFull_name() {
                    return full_name;
                }

                public void setFull_name(String full_name) {
                    this.full_name = full_name;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getUser_type() {
                    return user_type;
                }

                public void setUser_type(String user_type) {
                    this.user_type = user_type;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }
                public  void setAction(String action){
                    this.action=action;
                }
                public  String getAction(){
                    return action;
                }

                String id;
                String full_name;
                String email;
                String mobile;
                String alt_phoneno;
                String username;
                String password;
                String user_type;
                String status;
                String designation;
                String action;
                String estatus;
                String count;

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                String city;

                public void setCount(String count) {
                    this.count = count;
                }

                public String getCount() {
                    return count;
                }

                public String getDesignation() {
                    return designation;
                }

                public void setDesignation(){
                 this.designation = designation;
                }
            }
    }
