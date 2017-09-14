package sdutta9_a6.cs442.com.foodItem;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DataModel {
    private ArrayList<MenuItem> ItemsArray = new ArrayList<MenuItem>();
    private Context ctx;

    private static  String TAG = "DataModel";

    public DataModel(Context ctx, String itemsFilename) {
        String line;
        BufferedReader br;

        try {
            br = new BufferedReader(new InputStreamReader(ctx.getAssets().open(itemsFilename)));

            while ((line = br.readLine()) != null) {
                StringTokenizer sTok = new StringTokenizer(line, ":");
                MenuItem gc = new MenuItem(Integer.parseInt(sTok.nextToken()));
                gc.name = sTok.nextToken();
                gc.price = sTok.nextToken();
                gc.details = sTok.nextToken();
                gc.method = sTok.nextToken();
                gc.ingredients = sTok.nextToken();
                ItemsArray.add(gc);
            }
        } catch (IOException e) {
            return;
        }
    }

        // Method to retrieve MenuItems
        public ArrayList<MenuItem> getItems() {
            return ItemsArray;
        }
}
