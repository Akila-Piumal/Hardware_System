package model;

public class Supplier {
    private String id;
    private String name;
    private String address;
    private String contact;
    private String company;

    public Supplier() {
    }

    public Supplier(String id, String name, String address, String contact, String company) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.company = company;
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

    @Override
    public String toString() {
        return "Supplier{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
