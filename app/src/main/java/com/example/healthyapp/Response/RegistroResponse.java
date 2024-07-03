package com.example.healthyapp.Response;

public class RegistroResponse {
    private UserData data;

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public RegistroResponse(String message) {
        this.message = message;
    }

    public static class UserData {
        private int user_id;
        private String name;
        private String lastname;
        private String email;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
