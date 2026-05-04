package business.product;

import java.util.Objects;

public class tProduct {
    private String name;
    private int price;

    public tProduct(String name, int price) {
    	if(name == null || name.trim().isEmpty() || price < 0) throw new IllegalArgumentException("Invalid args for Builder in tProduct");
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	if(name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Invalid arg for setName in tProduct");
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
    	if(price < 0) throw new IllegalArgumentException("Invalid arg for setPrice in tProduct");
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        tProduct product = (tProduct)obj;
        return name.equals(product.name) && price == product.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
    
    @Override
    public String toString() {
    	return this.name;
    }
}
