package view.TM;

import javafx.scene.layout.HBox;

public class SupplierTM {
    private String id;
    private String name;
    private String address;
    private String contact;
    private String company;
    private HBox box;

    public SupplierTM() {
    }

    public SupplierTM(String id, String name, String address, String contact, String company, HBox box) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.company = company;
        this.box = box;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public HBox getBox() {
        return box;
    }

    public void setBox(HBox box) {
        this.box = box;
    }

    @Override
    public String toString() {
        return "SupplierTM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", company='" + company + '\'' +
                ", box=" + box +
                '}';
    }
}
