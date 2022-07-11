package view.TM;

import javafx.scene.layout.HBox;

public class EmployeeTM {
    private String id;
    private String nic;
    private String name;
    private String address;
    private String contact;
    private double salary;
    private HBox box;

    public EmployeeTM() {
    }

    public EmployeeTM(String id, String nic, String name, String address, String contact, double salary, HBox box) {
        this.id = id;
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.salary = salary;
        this.box = box;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public HBox getBox() {
        return box;
    }

    public void setBox(HBox box) {
        this.box = box;
    }

    @Override
    public String toString() {
        return "EmployeeTM{" +
                "id='" + id + '\'' +
                ", nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", salary=" + salary +
                ", box=" + box +
                '}';
    }
}
