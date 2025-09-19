package Attestation01;

import java.util.Objects;

public class Product {
    private String nameProduct; //название продукта
    private double price; //стоимость продукта

    public Product(String nameProduct, double price) {
        setNameProduct(nameProduct);
        setPrice(price);
    }
    public String getNameProduct() {
return nameProduct;
    }
    public void setNameProduct(String nameProduct) {
        if (nameProduct == null || nameProduct.trim().isEmpty()) {
            System.out.println("Название продукта должно быть заполнено.");
            throw new IllegalArgumentException("Название продукта должно быть заполнено.");
        }
        if (nameProduct.matches("\\d+")) {
            System.out.println("Название продукта не должно содержать только цифры.");
            throw new IllegalArgumentException("Название продукта не должно содержать только цифры.");
        }
        if (nameProduct.length() < 3) {
            System.out.println("Название продукта не может быть короче 3 символов.");
            throw new IllegalArgumentException("Название продукта не может быть короче 3 символов.");
        }
        this.nameProduct = nameProduct;
    }
        public double getPrice () {
            return price;
        }
        public void setPrice (double price){
            if (price <= 0) {
                System.out.println("Цена продукта не может быть равна 0 или отрицательной.");
                throw new IllegalArgumentException("Цена продукта не может быть равна 0 или отрицательной.");
            }
            this.price = price;
        }
        @Override
        public String toString () {
            return "Product{" +"name='" + nameProduct + '\'' +", price=" + price +'}';
        }
        @Override
        public boolean equals (Object o){
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Product product = (Product) o;
            return Double.compare(product.price, price) == 0 && Objects.equals(nameProduct, product.nameProduct);
        }
        @Override
        public int hashCode () {
            return Objects.hash(nameProduct, price);
        }
    }


