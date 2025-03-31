package mdb_k25.my_disc_bag.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Disc {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String manufacturer, plastic;
    private double price;
    private boolean lost;
    private int speed, glide, turn, fade;

    @NotEmpty(message = "Disc's name can't be empty.")
    @Size(min = 2, max = 250)
    private String name;

    @Min(value = 100, message = "Weight must be atleast 100 grams.")
    @Max(value = 200, message = "Weight must be at most 200 grams.")
    private int weight;


    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;

    public Disc() {
    }

    public Disc(String name, String manufacturer, String plastic, int weight, int speed, int glide, int turn, int fade, double price, Category category, boolean lost) {
        super();
        this.name = name;
        this.manufacturer = manufacturer;
        this.plastic = plastic;
        this.weight = weight;
        this.speed = speed;
        this.glide = glide;
        this.turn = turn;
        this.fade = fade;
        this.price = price;
        this.category = category;
        this.lost = lost;
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

    public int getSpeed() {
        return speed;
    }

    public int getGlide() {
        return glide;
    }

    public int getTurn() {
        return turn;
    }

    public int getFade() {
        return fade;
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

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setGlide(int glide) {
        this.glide = glide;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setFade(int fade) {
        this.fade = fade;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    @Override
    public String toString() {
        if (this.category != null)
            return "Disc [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", plastic=" + plastic + ", weight="
                + weight + ", speed=" + speed + ", glide=" + glide + ", turn=" + turn + ", fade=" + fade 
                + ", price=" + price + ", category=" + this.getCategory() + ", lost=" + lost + "]";
        else
            return "Disc [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", plastic=" + plastic + ", weight="
                + weight + ", speed=" + speed + ", glide=" + glide + ", turn=" + turn + ", fade=" + fade + ", price=" + price + ", lost=" + lost + "]";
    }
}