package com.sandor.models;

import java.util.ArrayList;

public class Container {
    private int maxSize;

    private ArrayList<Product> products;

    public Container(int maxSize){
        this.maxSize = maxSize;
        products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void decreaseMaxSize(int amount){
        maxSize -= amount;
        if(maxSize < 0) maxSize = 0;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int getAvailableSpace() {
        int usedSpace = 0;
        for(int i = 0; i < products.size(); i++){
            usedSpace += products.get(i).getWeight();
        }
        return maxSize - usedSpace;
    }

    public ArrayList<Product> copyProducts(){
        return new ArrayList(products);
    }

    public boolean canProductFit(Product product){
        return (getAvailableSpace() - product.getWeight()) >= 0;
    }

    public String productsToString(){
        String result = "";
        for(Product p : products){
            if(p.getColor() == "Red"){
                result += "Rood - ";
            }
            else if(p.getColor() == "Green"){
                result += "Groen - ";
            }
            else if(p.getColor() == "Blue"){
                result += "Blauw - ";
            }
        }
        return result;
    }

}
