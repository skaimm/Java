package lucky.luckytime.Model;

import java.util.ArrayList;

public class Store {

    private ArrayList<Product> buyProducts;
    private ArrayList<Product> changeProducts;

    public Store(ArrayList<Product> buyProducts, ArrayList<Product> changeProducts) {
        this.buyProducts = buyProducts;
        this.changeProducts = changeProducts;
    }

    public Store(){

    }

    public ArrayList<Product> getBuyProducts() {
        return buyProducts;
    }

    public void setBuyProducts(ArrayList<Product> buyProducts) {
        this.buyProducts = buyProducts;
    }

    public ArrayList<Product> getChangeProducts() {
        return changeProducts;
    }

    public void setChangeProducts(ArrayList<Product> changeProducts) {
        this.changeProducts = changeProducts;
    }

    public void addBuyProducts(Product product){
        this.buyProducts.add(product);
    }
    public void addChangeProducts(Product product){
        this.changeProducts.add(product);
    }

    @Override
    public String toString() {
        return "Store{" +
                "buyProducts=" + buyProducts +
                ", changeProducts=" + changeProducts +
                '}';
    }
}
