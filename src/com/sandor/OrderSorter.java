package com.sandor;

import com.sandor.extra.ExtraFunctions;
import com.sandor.models.Container;
import com.sandor.models.Order;
import com.sandor.models.OrderLine;
import com.sandor.models.Product;

import java.lang.reflect.Array;
import java.util.*;

public class OrderSorter {

    public OrderSorter(Order order, Container[] containers){
        //TODO: sort order
        Product[] products = getProductList(order);
        products = sortProductList(products);

        int containerCount = 0;
        int maxContainers = containers.length;

        Product[] leftOver = products;
        while(leftOver.length > 0 && containerCount < maxContainers) {
            Container result = null;
            while (result == null) {
                result = getBestFit(leftOver, containers[containerCount]);
                containers[containerCount].decreaseMaxSize(1);
            }
            containers[containerCount] = result;
            leftOver = getLeftover(result, leftOver);
            containerCount++;
        }
    }

    //Backtracking algorithm that fits the products as tight as possible in a certain container
    public Container getBestFit(Product[] products, Container container){
        Container newContainer = new Container(container.getMaxSize());
        newContainer.setProducts(container.copyProducts());

        Product[] newProducts = products.clone();

        if(container.getAvailableSpace() >= getLeastWeight(products)){

            for (int i = 0; i < newProducts.length; i++) {
                if(newProducts[i] == null)
                    continue;

                if (container.canProductFit(newProducts[i])) {
                    newContainer.addProduct(newProducts[i]);
                    if (newContainer.getAvailableSpace() == 0) {
                        //Done
                        return newContainer;
                    } else {
                        newProducts = ExtraFunctions.removeTheElement(newProducts, i);
                        Container c = getBestFit(newProducts, newContainer);
                        if(c != null)
                            return c;
                    }
                }
            }
        }
        return null;
    }

    Product[] getProductList(Order order){
        ArrayList<Product> products = new ArrayList<>();
        for(OrderLine orderLine : order.getOrderLines()){
            for(int i = 0; i < orderLine.getQuantity(); i++){
                Product orderLineProduct = orderLine.getProduct();
                Product newProduct = new Product();
                newProduct.setBrand(orderLineProduct.getBrand());
                newProduct.setName(orderLineProduct.getName());
                newProduct.setWeight(orderLineProduct.getWeight());
                newProduct.setColor(orderLineProduct.getColor());
                products.add(newProduct);
            }
        }
        return products.toArray(new Product[0]);
    }

    Product[] sortProductList(Product[] products){
        int i, j;
        Product temp;

        for (i = 0; i < ( products.length - 1 ); i++) {
            for (j = 0; j < products.length - i - 1; j++) {
                if (products[j].getWeight() < products[j+1].getWeight())
                {
                    temp = products[j];
                    products[j] = products[j+1];
                    products[j+1] = temp;
                }
            }
        }
        return products;
    }

    public int getLeastWeight(Product[] products){
        if(products.length > 0) {
            int least = products[0].getWeight();
            for (Product product : products) {
                if (least > product.getWeight()) {
                    least = product.getWeight();
                }
            }
            return least;
        }
        return -1;
    }

    public Product[] getLeftover(Container container, Product[] products){
        ArrayList<Product> result = new ArrayList<>();
        Collections.addAll(result, products);
        for(Product p : container.getProducts()) {
            result.remove(p);
        }
        return result.toArray(new Product[0]);
    }
}
