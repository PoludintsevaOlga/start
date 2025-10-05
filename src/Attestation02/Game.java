package Attestation02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Game {
    private final GameState state = new GameState();
    private final Map<String, Command> commands = new LinkedHashMap<>();

    static {
        WorldInfo.touch("Game");
    }

    public Game() {
        registerCommands();
        bootstrapWorld();
    }

    private void registerCommands() {
        commands.put("help", (ctx, a) -> System.out.println("Команды: " + String.join(", ", commands.keySet())));
        commands.put("gc-stats", (ctx, a) -> {
            Runtime rt = Runtime.getRuntime();
            long free = rt.freeMemory(), total = rt.totalMemory(), used = total - free;
            System.out.println("Память: used=" + used + " free=" + free + " total=" + total);
        });
//======================================================================================================================
        commands.put("look", (ctx, a) -> System.out.println(ctx.getCurrent().describe()));
//======================================================================================================================
        commands.put("move", (ctx, a) -> {
            System.out.println(a.getFirst());
            System.out.println(ctx.getCurrent().describe());

            Map<String, Room> n = ctx.getCurrent().getNeighbors();
            if(n.containsKey(a.getFirst())){
                ctx.setCurrent(n.get(a.getFirst()));
                System.out.println("Вы попали в " + ctx.getCurrent().getName());
            }
            else{
                System.out.println("Нет такого направления");
                throw new InvalidCommandException("Некуда там идти.");
            }
        });
//======================================================================================================================
        commands.put("take", (ctx, a) -> {
            StringBuilder item = new StringBuilder();
            boolean fl = false;
            System.out.println("Валяются: ");
            for(Item i : ctx.getCurrent().getItems()){
                System.out.println(i.getName());
            }

            boolean first = true;
            for(String s : a){
                if(first){
                    first = false;
                }
                else{
                    item.append(" ");
                }
                item.append(s);
            }

            List<Item> ctx_items = ctx.getCurrent().getItems();
            int ic = ctx_items.size();

            for(int i = 0;i<ic;i++){
                if(item.toString().equalsIgnoreCase(ctx_items.get(i).getName())){
                    System.out.println("Взят " + ctx_items.get(i).getName());
                    ctx.getPlayer().getInventory().add(ctx_items.get(i));
                    ctx_items.remove(i);
                    fl = true;
                    break;
                }
            }

            if(!fl){
                throw new InvalidCommandException("У-у-упс.... Нет указанного предмета в этом месте.");
            }

            System.out.println("В карманах у нас лежит:");
            ctx.getPlayer().getInventory().stream().map(Item::getName).forEach(System.out::println);
        });
//======================================================================================================================
        commands.put("inventory", (ctx, a) -> {
            boolean searched = false;
            StringBuilder item = new StringBuilder();

            for(String s : a){
                item.append(s);
                List<Item> ctx_items = ctx.getCurrent().getItems();
                List<Item> player_items = ctx.getPlayer().getInventory();
                int ic = player_items.size();

                boolean fl = false;
                for(int i = 0;i<ic;i++){
                    if(item.toString().equalsIgnoreCase(player_items.get(i).getName())){
                        System.out.println("Вытряхнули " + player_items.get(i).getName());
                        ctx.getCurrent().getItems().add(player_items.get(i));
                        player_items.remove(i);
                        fl = true;
                        searched = true;
                        item.delete(0, item.length()-1);
                        break;
                    }
                }
                if(!fl){
                    item.append(" ");
                }
            }

            if(!searched){
                System.out.println("Тю-тю.... Нет такого предмета в карманах...");
            }

            if(ctx.getPlayer().getInventory().isEmpty()){
                System.out.println("Ничего не осталось в карманах.");
            }
            else{
                System.out.println("Осталось в карманах:");
                ctx.getPlayer().getInventory().stream().map(Item::getName).forEach(System.out::println);
            }
        });
//======================================================================================================================
        commands.put("use", (ctx, a) -> {
            StringBuilder item = new StringBuilder();
            boolean used = false;

            for(String s : a){
                item.append(s);
                List<Item> player_items = ctx.getPlayer().getInventory();
                int ic = player_items.size();

                boolean fl = false;
                for(int i = 0;i<ic;i++){
                    if(item.toString().equalsIgnoreCase(player_items.get(i).getName())){
                        System.out.println("Использовали " + player_items.get(i).getName());
                        player_items.get(i).apply(ctx);
                        fl = true;
                        used = true;
                        item.delete(0, item.length()-1);
                        break;
                    }
                }
                if(!fl){
                    item.append(" ");
                }
            }

            if(ctx.getPlayer().getInventory().isEmpty()){
                System.out.println("Ничего не осталось в карманах.");
            }
            else{
                System.out.println("Осталось в карманах:");
                ctx.getPlayer().getInventory().stream().map(Item::getName).forEach(System.out::println);
            }

            if(!used){
                throw new InvalidCommandException("Тю-тю.... Нет такого предмета в карманах....");
            }
        });
//======================================================================================================================
        commands.put("fight", (ctx, a) -> {
            StringBuilder monster = new StringBuilder();
            boolean killed = false;
            boolean searched = false;

            Monster ctx_monster = ctx.getCurrent().getMonster();

            for(String s : a){
                monster.append(s);

                boolean fl = false;

                if(monster.toString().equalsIgnoreCase(ctx_monster.getName())){
                    fl = true;
                    searched = true;

                    System.out.println("Наше HP: " + ctx.getPlayer().getHp());
                    System.out.println("Наше Attack: " + ctx.getPlayer().getAttack());
                    System.out.println("Monster HP: " + ctx_monster.getHp());
                    System.out.println("Monster Level: " + ctx_monster.getLevel());

                    System.out.println("Дерёмся с " + ctx_monster.getName());
                    ctx_monster.setHp(ctx_monster.getHp() - ctx.getPlayer().getAttack());
                    ctx.getPlayer().setHp(ctx.getPlayer().getHp() - 1);
                    if(ctx_monster.getHp() <= 0){
                        killed = true;
                        ctx.getCurrent().setMonster(null);
                        System.out.println("Монстр " + ctx_monster.getName() + " повержен!");
                    }

                    if(ctx.getPlayer().getHp() <= 0){
                        System.out.println("Монстр " + ctx_monster.getName() + " одолел Вас!");
                        System.out.println("Игра завершена");
                        System.exit(0);
                    }

                    monster.delete(0, monster.length()-1);
                    break;
                }

                if(!fl){
                    monster.append(" ");
                }
            }

            if(!searched){
                throw new InvalidCommandException("Ха!.... Нет такого монстра тут....");
            }
        });
//======================================================================================================================
        commands.put("save", (ctx, a) -> SaveLoad.save(ctx));
        commands.put("load", (ctx, a) -> SaveLoad.load(ctx));
        commands.put("scores", (ctx, a) -> SaveLoad.printScores());
        commands.put("about", (ctx, a) -> {
            System.out.println("Команды игры реализованы Инженером Ростелеком - Накоскиным П.В.");
        });
        commands.put("exit", (ctx, a) -> {
            System.out.println("Пока!");
            System.exit(0);
        });
    }

    private void bootstrapWorld() {
        Player hero = new Player("Герой", 20, 5);
        state.setPlayer(hero);

        Room square = new Room("Площадь", "Каменная площадь с фонтаном.");
        Room forest = new Room("Лес", "Шелест листвы и птичий щебет.");
        Room cave = new Room("Пещера", "Темно и сыро.");
        square.getNeighbors().put("north", forest);
        forest.getNeighbors().put("south", square);
        forest.getNeighbors().put("east", cave);
        cave.getNeighbors().put("west", forest);

        forest.getItems().add(new Potion("Малое зелье", 5));
        forest.setMonster(new Monster("Волк", 1, 8));

        state.setCurrent(square);
    }

    public void run() {
        System.out.println("DungeonMini (TEMPLATE). 'help' — команды.");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("> ");
                String line = in.readLine();
                if (line == null) break;
                line = line.trim();
                if (line.isEmpty()) continue;
                List<String> parts = Arrays.asList(line.split("\s+"));
                String cmd = parts.getFirst().toLowerCase(Locale.ROOT);
                List<String> args = parts.subList(1, parts.size());
                Command c = commands.get(cmd);
                try {
                    if (c == null) throw new InvalidCommandException("Неизвестная команда: " + cmd);
                    c.execute(state, args);
                    state.addScore(1);
                } catch (InvalidCommandException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Непредвиденная ошибка: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }
}
