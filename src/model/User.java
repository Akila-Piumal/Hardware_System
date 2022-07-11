package model;

public class User {
    private String id;
    private String nic;
    private String name;
    private String address;
    private String contact;
    private Double salary;
    private String role;

    public User() {
    }

    public User(String id, String nic, String name, String address, String contact, Double salary, String role) {
        this.id = id;
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.salary = salary;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", salary=" + salary +
                ", role='" + role + '\'' +
                '}';
    }
}
