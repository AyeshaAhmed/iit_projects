package aahmed31_a6.cs442.com.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        // Add 3 sample items.
        addItem(new DummyItem("1", "1. Chicken Manchurian \t\t\t\t\t\t$12.99"));
        addItem(new DummyItem("2", "2. Boiled Eggs \t\t\t\t\t\t\t\t\t\t\t\t$0.49"));
        addItem(new DummyItem("3", "3. Egg Sandwich \t\t\t\t\t\t\t\t\t\t$1.99"));
        addItem(new DummyItem("4", "4. Peppered Tomatoes \t\t\t\t\t\t\t$0.99"));
        addItem(new DummyItem("5", "5. Peppered Cucumbers \t\t\t\t\t\t$0.99"));
        addItem(new DummyItem("6", "6. Waffles \t\t\t\t\t\t\t\t\t\t\t\t\t\t$1.99"));
        addItem(new DummyItem("7", "7. Swiss Cheese Sandwich \t\t\t\t\t$0.99"));
        addItem(new DummyItem("8", "8. Fruit Salad \t\t\t\t\t\t\t\t\t\t\t\t$2.99"));
        addItem(new DummyItem("9", "9. Dry Fruit Mix \t\t\t\t\t\t\t\t\t\t\t$1.99"));
        addItem(new DummyItem("10", "10. Hot Chocolate \t\t\t\t\t\t\t\t\t\t$1.99"));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String content;

        public DummyItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
