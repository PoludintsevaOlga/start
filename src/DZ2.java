/* Задача 1 - Для введенной с клавиатуры буквы английского алфавита
нужно вывести слева стоящую букву на стандартной клавиатуре. При этом
клавиатура замкнута, т.е. справа от буквы «p» стоит буква «a», а слева от "а"
буква "р", также соседними считаются буквы «l» и буква «z», а буква «m» с
буквой «q».*/

import java.util.Scanner;
public class DZ2 {
        public static void main (String[]args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите маленькую букву английского алфавита");
        String input = scanner.next();
        char single_letter = input.charAt(0);
        System.out.println("Введенная буква: " + single_letter);
        String keyboardRing = "qwertyuiopasdfghjklzxcvbnm";
        int index = keyboardRing.indexOf(single_letter);
        if (index == -1) {
            System.out.println("Ошибка! Неподдерживамый символ.");
        } else {
            int leftIndex = (index - 1 + keyboardRing.length()) % keyboardRing.length();
            char left_letter = keyboardRing.charAt(leftIndex);
            System.out.println("Слева на клавиатуре расположена буква: " + left_letter);
        }
        scanner.next();
    }
    }



