package View;

import java.util.Map;

/**
 * Created by phillip on 12/1/15.
 */
public class MenuEntry {
    public Runnable action;
    public String description;

    public MenuEntry(Runnable action, String description) {
        this.action = action;
        this.description = description;
    }

    public static void printMenuMap(Map<String, MenuEntry> menu) {
        for (String key : menu.keySet()) {
            System.out.printf("%s: %s", key, menu.get(key));
        }
    }
}
