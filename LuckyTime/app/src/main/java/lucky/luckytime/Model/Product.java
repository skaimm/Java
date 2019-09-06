package lucky.luckytime.Model;

public class Product {

    private int id;
    private String type;
    private long price;

    public Product(int id, String type, long price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }

    public Product(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return type;
    }
}
