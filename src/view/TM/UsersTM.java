package view.TM;

import javafx.scene.control.Button;

public class UsersTM {
    private String id;
    private String userName;
    private String password;
    private String role;
    private Button btnDetails;

    public UsersTM() {
    }

    public UsersTM(String id, String userName, String password, String role, Button btnDetails) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.btnDetails = btnDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Button getBtnDetails() {
        return btnDetails;
    }

    public void setBtnDetails(Button btnDetails) {
        this.btnDetails = btnDetails;
    }

    @Override
    public String toString() {
        return "UsersTM{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", btnDetails=" + btnDetails +
                '}';
    }
}
