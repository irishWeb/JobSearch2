package org.example.jobsearch23.dto;




public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String userType;

    public UserDTO() {}

    public UserDTO(Long id, String email, String name, String phone, String userType) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}