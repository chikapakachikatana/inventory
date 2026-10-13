import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class inventory {

    // Хранилище инвентаря: ключ — название предмета, значение — количество.
    // LinkedHashMap сохраняет порядок добавления предметов.
    private static final Map<String, Integer> items = new LinkedHashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в Инвентарь приключенца!");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addItem();
                case "2" -> changeQuantity();
                case "3" -> removeItem();
                case "4" -> findItem();
                case "5" -> showInventory();
                case "6" -> {
                    running = false;
                    System.out.println("Выход из программы. Удачного приключения!");
                }
                default -> System.out.println("Ошибка: неверный пункт меню. Попробуйте снова.");
            }
            System.out.println();
        }
        scanner.close();
    }

    // Меню
    private static void printMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 - Добавить новый предмет");
        System.out.println("2 - Изменить количество предметов");
        System.out.println("3 - Удалить предмет");
        System.out.println("4 - Найти предмет по названию");
        System.out.println("5 - Показать весь инвентарь");
        System.out.println("6 - Выход");
        System.out.print("> ");
    }

    // ---------- 1. Добавление предмета ----------
    private static void addItem() {
        System.out.print("Введите название предмета: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Ошибка: название предмета не может быть пустым.");
            return;
        }

        if (items.containsKey(name)) {
            // Предмет уже есть — предлагаем увеличить или уменьшить количество
            System.out.println("Предмет \"" + name + "\" уже существует. Текущее количество: " + items.get(name));
            System.out.print("Введите количество для добавления (можно отрицательное для уменьшения): ");
            int delta = readInt();
            if (delta == Integer.MIN_VALUE) return;

            int newQty = items.get(name) + delta;
            if (newQty <= 0) {
                items.remove(name);
                System.out.println("Количество стало <= 0. Предмет \"" + name + "\" удалён из инвентаря.");
            } else {
                items.put(name, newQty);
                System.out.println("Количество для \"" + name + "\" обновлено: " + newQty);
            }
            return;
        }

        System.out.print("Введите количество: ");
        int qty = readInt();
        if (qty == Integer.MIN_VALUE) return;

        if (qty <= 0) {
            System.out.println("Ошибка: количество должно быть больше нуля.");
            return;
        }

        items.put(name, qty);
        System.out.println("Предмет \"" + name + "\" успешно добавлен.");
    }

    // 2. Изменение количества
    private static void changeQuantity() {
        System.out.print("Введите название предмета: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Ошибка: название предмета не может быть пустым.");
            return;
        }
        if (!items.containsKey(name)) {
            System.out.println("Предмет \"" + name + "\" не найден в инвентаре.");
            return;
        }

        System.out.print("Введите новое количество: ");
        int qty = readInt();
        if (qty == Integer.MIN_VALUE) return;

        if (qty <= 0) {
            items.remove(name);
            System.out.println("Количество стало <= 0. Предмет \"" + name + "\" удалён из инвентаря.");
        } else {
            items.put(name, qty);
            System.out.println("Количество для \"" + name + "\" обновлено.");
        }
    }

    // 3. Удаление предмета
    private static void removeItem() {
        System.out.print("Введите название предмета для удаления: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Ошибка: название предмета не может быть пустым.");
            return;
        }
        if (items.remove(name) != null) {
            System.out.println("Предмет \"" + name + "\" успешно удалён.");
        } else {
            System.out.println("Предмет \"" + name + "\" не найден в инвентаре.");
        }
    }

    // 4. Поиск предмета
    private static void findItem() {
        System.out.print("Введите название предмета: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Ошибка: название предмета не может быть пустым.");
            return;
        }
        Integer qty = items.get(name);
        if (qty == null) {
            System.out.println("Предмет \"" + name + "\" не найден в инвентаре.");
        } else {
            System.out.println("Количество \"" + name + "\": " + qty);
        }
    }

    // 5. Вывод инвентаря
    private static void showInventory() {
        if (items.isEmpty()) {
            System.out.println("Инвентарь пуст.");
            return;
        }
        System.out.println("Текущий инвентарь:");
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            System.out.println(entry.getKey() + " – " + entry.getValue());
        }
    }

    // Вспомогательный метод чтения числа
    // Возвращает Integer.MIN_VALUE при ошибке ввода.
    private static int readInt() {
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            System.out.println("Ошибка: количество не может быть пустым.");
            return Integer.MIN_VALUE;
        }
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректное целое число.");
            return Integer.MIN_VALUE;
        }
    }
}