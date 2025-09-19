package Attestation01;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.lang.String;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Person> people = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        // Ввод покупателей
        System.out.println("Введите имя покупателя и его деньги через знак '='. Для завершения введите 'END'");
        String input = scanner.nextLine();
        while (!input.equals("END")) {
            if (input.contains("=")) { // Добавлена проверка на наличие "="
                String[] parts = input.split("=");
                String namePers = parts[0];
                double sumMoney = Double.parseDouble(parts[1]);
                try {
                    Person person = new Person(namePers, sumMoney);
                    people.add(person);
                } catch (IllegalArgumentException e) {
                    System.out.println("Что-то пошло не так");
                }
            } else {
                System.out.println("Неверный формат ввода покупателя и суммы. Используйте 'Имя=Деньги' или 'END'.");
            }
            input = scanner.nextLine();
        }

        // Ввод продуктов
        System.out.println("Введите продукты и цену (Название=Цена).Для завершения введите 'END'");
       input = scanner.nextLine();
        while (!input.equals("END")) {
            if (input.contains("=")) {  // Добавлена проверка на наличие "="
                String[] parts = input.split("=");
                String nameProduct = parts[0];
                double price = Double.parseDouble(parts[1]);
                try { Product product = new Product(nameProduct, price);
                        products.add(product);
                    }  catch (IllegalArgumentException e) {
                System.out.println("Неверный формат ввода продукта.");
                } catch (Exception e) {
                    System.out.println("Ошибка при  ввода продукта: " + e.getMessage()); // Проверяем ошибки NumberFormatException и DateTimeParseException
                }
            } else {
                System.out.println("Неверный формат ввода продукта. Используйте 'Название=Цена' или 'END'");
            }
            input = scanner.nextLine();
        }

        // Процесс покупки
        System.out.println("Переходим к покупке продуктов.");
        System.out.println("Введите наименование товара, купленное покупателем.  Для завершения введите 'END'");

        int personIndex = 0;
        input = scanner.nextLine();
        while (!input.equals("END")) {
            Person currentPerson = people.get(personIndex % people.size()); //Циклический перебор покупателей
            Product chosenProduct = null;
            for (Product product : products) {
                if (product.getNameProduct().trim().equals(input)) {
                    chosenProduct = product;
                    break;
                }
            }
            if (chosenProduct != null) {
                currentPerson.buyProduct(chosenProduct);
            } else {
                System.out.println("Продукт с названием '" + input + " не найден.");
            }
            personIndex++;
            input = scanner.nextLine();
        }

        // Вывод информации о покупках каждого покупателя
        System.out.println("Итоги покупок--- Результаты ---");
        for (Person person : people) {
            System.out.print(person.getNamePers() + " - ");
            if (person.getProdPers().isEmpty()) {
                System.out.println("Ничего не куплено");
            } else {
                List<String> productNames = new ArrayList<>();
                for (Product product : person.getProdPers()) {
                    productNames.add(product.getNameProduct());
                }
                System.out.println(String.join(", ", productNames));
            }
        }

        scanner.close();
    }
}

