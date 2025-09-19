package Attestation01;
import java.util.ArrayList;
import java.util.List;


public class Person {
    private String namePers; //Имя покупателя
    private double sumMoney; // Сумма денег
    private List<Product> prodPers; //продукт, который выбрал покупатель

    public Person(String namePers, double sumMoney) {
        setNamePers(namePers);
        setSumMoney(sumMoney);
        this.prodPers = new ArrayList<>();
    }

    public String getNamePers() {
        return namePers;
    }

    public void setNamePers(String namePers) {
        if (namePers == null || namePers.trim().isEmpty()) {
            System.out.println(namePers);
            System.out.println("Имя не может быть пустым!");
            throw new IllegalArgumentException("Имя не может быть пустым.");
        }
        this.namePers = namePers;
    }

    public double getSumMoney() {
        return sumMoney;
    }
    public void setSumMoney (double sumMoney) {
        if (sumMoney < 0) {
            System.out.println("Деньги не могут быть отрицательными.");
            throw new IllegalArgumentException("Деньги не могут быть отрицательными.");
        }
        this.sumMoney = sumMoney;
    }
    public List<Product> getProdPers(){
        return prodPers;
    }
    public void buyProduct (Product product){
            if (sumMoney >= product.getPrice()) {
                prodPers.add(product);
                sumMoney -= product.getPrice();
                System.out.println(namePers + " купил(а) " + product.getNameProduct());
            } else {
                System.out.println(namePers + " не может позволить себе " + product.getNameProduct());
            }
        }
    }
