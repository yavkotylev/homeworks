package homeworks.HW7.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Yaroslav on 19.08.16.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write plugin root:");
        PluginManager pluginManager = new PluginManager(scanner.next());
        List<Plugin> plugins = new ArrayList<>();

        while (true) {
            String pluginName, className;
            System.out.println("Write plugin name or \"exit\"");
            pluginName = scanner.next();
            if (pluginName.equals("exit")) break;

            System.out.println("Write class name or \"exit\"");
            className = scanner.next();
            if (className.equals("exit")) break;

            System.out.println("Result of method doUsefull() in class " + className + " of " + pluginName + " plugin:");
            try {
                Plugin pl = pluginManager.load(pluginName, className);
                pl.doUsefull();
                plugins.add(pl);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                continue;
            }
            System.out.println("");
        }

        for (Plugin plugin : plugins) {
            plugin.doUsefull();
        }
    }
}
