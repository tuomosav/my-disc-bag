package mdb_k25.my_disc_bag.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Disc {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String manufacturer, plastic;
    private int weight;
    private double price;

    @NotEmpty(message = "Disc's name can't be empty.")
    @Size(min = 2, max = 250)
    private String name;


    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;

    public Disc() {
    }

    public Disc(String name, String manufacturer, String plastic, int weight, double price, Category category) {
        super();
        this.name = name;
        this.manufacturer = manufacturer;
        this.plastic = plastic;
        this.weight = weight;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getPlastic() {
        return plastic;
    }

    public int getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setPlastic(String plastic) {
        this.plastic = plastic;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        if (this.category != null)
            return "Disc [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", plastic=" + plastic + ", weight="
                + weight + ", price=" + price + ", category=" + this.getCategory() + "]";
        else
            return "Disc [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", plastic=" + plastic + ", weight="
                + weight + ", price=" + price + "]";
    }

}