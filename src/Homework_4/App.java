package Homework_4;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main (String[]args){
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Television tv1 = new Television("Sumsung", 60, 2,8);
        Television tv2 = new Television("Sony", 32, 5,22);

        System.out.println("Ниже введите самостоятельно параметры телевизора");
        System.out.println("Бренд: ");
        String CustomManufacturer = scanner.nextLine();
        System.out.println("Введите диагональ телевизора: " );
        double CustomDiagonal= Double.parseDouble(scanner.nextLine());
        System.out.println("Введите номер канала: " );
        int Customchannel = scanner.nextInt();
        System.out.println("Введите уровень громкости: " );
        int Customvolume = scanner.nextInt();
        tv2 = new Television(CustomManufacturer ,CustomDiagonal,Customchannel, Customvolume);

        tv1.turnOff();
        System.out.println("Телевизора 1: " + tv1);
    }
}
