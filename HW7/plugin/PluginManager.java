package homeworks.HW7.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Yaroslav on 19.08.16.
 */


public class PluginManager {
    private final String pluginRootDirectory;


    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) {
        try {
            File file = new File(pluginRootDirectory + "/" + pluginName);
            if (file.exists() == false) throw new RuntimeException("File doesn't exist");

            URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
            Class<?> clazz = classLoader.loadClass(pluginClassName);

            return new Plugin() {
                @Override
                public void doUsefull() {
                    try {
                        clazz.getMethod("doUsefull").invoke(clazz.newInstance());
                    } catch (Exception e) {
                        throw new RuntimeException("can't invoke method");
                    }

                }
            };
        } catch (MalformedURLException e) {
            throw new RuntimeException("Can't load class");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Can't find class \"" + pluginClassName + "\"");
        } catch (Exception e) {
            throw new RuntimeException("Can't cast class");
        }
    }

}

