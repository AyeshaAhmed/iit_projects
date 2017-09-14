package aahmed31_a3.cs442.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ayesha Ahmed on 9/27/2015.
 */
public class FoodModel {

    public static List<FoodItem> myFood = new ArrayList<FoodItem>();
    public static Map<String, FoodItem> ITEM_MAP = new HashMap<String, FoodItem>();

    public static class FoodItem {
        String id;
        String title;
        String name;
        String price;
        int imageID;
        String descrip;
        String ingred;
        String instruct;

        public FoodItem(String id, String title, String name,
                        String price, int imageID, String descrip,
                        String ingred, String instruct) {
            super();
            this.id = id;
            this.title = title;
            this.name = name;
            this.price = price;
            this.imageID = imageID;
            this.descrip = descrip;
            this.ingred = ingred;
            this.instruct = instruct;
        }

        public String toString(){return title;}
    }
    public static void addItem(FoodItem item) {
        myFood.add(item);
        ITEM_MAP.put(item.id, item);
    }
    static {
        // Add 3 sample items.
        addItem(new FoodItem("1", "1. Chicken Manchurian \t\t\t\t\t\t$12.99","1. Chicken Manchurian", "$12.99", R.drawable.chicken_man,
                "Made from authentic Chinese flavors, this dish is a must " +
                    "try! Fried chicken balls cooked in a spicy sauce " +
                    "batter. Served with steamed rice or hakka noodles. " +
                        "Caution contains egg which may cause allergies.",
                "Chicken, Eggs, All Purpose Flour, Ginger Garlic Paste, " +
                        "Vegetable Oil, Onion, Capsicum, Vinegar, Salt, " +
                        "Tomato Puree, Cornflour, Soy Sauce, Celery",
                "Mix together the chicken, egg, flour, ginger garlic paste, " +
                        "ajino moto and enough water for a thick batter. " +
                        "Leave this for 5-10 minutes. \n\nHeat the oil on high heat. " +
                        "Drop heapful teaspoons of batter and fry until golden brown. " +
                        "Drain on tissue paper until dry."));
        addItem(new FoodItem("2", "2. Boiled Eggs \t\t\t\t\t\t\t\t\t\t\t\t$0.49","2. Boiled Eggs", "$0.49", R.drawable.boiled_egg,
                "A home breakfast staple. It can be halved or sliced. " +
                        "Great source of protein. Very quick and easy " +
                        "to make, just sprinkle with your favorite seasoning. " +
                        "Boiled egg can also be mushed and mixed with mayo " +
                        "to make an egg spread for sandwhiches." +
                        "Caution contains egg which may cause allergies.",
                "Egg, Water, Black Pepper, Salt.",
                "Boil the egg in the water for 10 minutes. Take it out and let it cool." +
                        "Once cooled peel off the shell. Dust with pepper and salt " +
                        "for desired taste."));
        addItem(new FoodItem("3", "3. Egg Sandwich \t\t\t\t\t\t\t\t\t\t$1.99", "3. Egg Sandwich", "$1.99", R.drawable.egg_sand,
                "This is a new invention of the old egg sandwich." +
                        "By adding avocado we have made this a super food" +
                        "that has fiber and protein all in one. It also has " +
                        "an irresistble taste, like why didn't people put avocados" +
                        " and eggs together to begin with? Lastly, the spicy flavor of mustard " +
                        "brings everything together so well your mouth will water just from the" +
                        " smell of this dish. Caution contains egg which may cause allergies.",
                "Egg, Black Pepper, Salt, Oil, Bread Slices, Avocado Sliced, Mustard Sauce.",
                "Heat the oil in a pan then fry the egg in the oil. " +
                        "Season the egg with pepper and salt as it fries. " +
                        "Also, put the bread in the toaster while you are" +
                        " frying your egg. Lastly, layer your fried egg and " +
                        "avocado on the bread and top it with mustard sauce." +
                        "Bon Appetite!"));
        addItem(new FoodItem("4", "4. Peppered Tomatoes \t\t\t\t\t\t\t$0.99", "4. Peppered Tomatoes", "$0.99", R.drawable.tomato_cuccum,
                "The perfect of sweet and spicy. This mouth watering" +
                        " delight is a healthy snack to eat anywhere" +
                        "and especially at a picnic. So, simple yet so good!",
                "Sliced Roma Tomatoes, Black Pepper",
                "Dust the tomato slices with black pepper. Serve cold. Easy as that! You're done!"));
        addItem(new FoodItem("5", "5. Peppered Cucumbers \t\t\t\t\t\t$0.99", "5. Peppered Cucumbers", "$0.99", R.drawable.tomato_cuccum,
                "This refreshing green dish is a salad of it's own! " +
                        "This can be taken to picnics and served on the " +
                        "side of peppered tomatoes. This will definitly " +
                        "hydrate you on a hot summer day!",
                "Sliced Cucumber, Black Pepper.",
                "Dust the cucumber slices with black pepper. Serve cold. Easy as that! You're done!"));
        addItem(new FoodItem("6", "6. Waffles \t\t\t\t\t\t\t\t\t\t\t\t\t\t$1.99", "6. Waffles", "$1.99", R.drawable.waffles,
                "The American favorite. It is a delightful dessert " +
                        "at any brekfast table that is sure to bring those" +
                        " smiles out. Let the taste of syrup linger on....",
                "Waffle mix, Water, Milk, Oil, Butter, Syrup.",
                "Prepare the waffle mix as instructed on the box. Top with syrup and dig in!"));
        addItem(new FoodItem("7", "7. Swiss Cheese Sandwich \t\t\t\t\t$0.99", "7. Swiss Cheese Sandwich", "$0.99", R.drawable.swiss,
                "O the dreams of swiss.... Swiss cheese is known " +
                        "for it's subtle and creamy taste. A great " +
                        "source of protein and good fats. Just put it " +
                        "on some bread and watch it become a melting joy!",
                "Swiss Cheese Singles, Bread.",
                "Put 2 slices of swiss cheese between 2 slices of bread. " +
                        "Heat a pan on high heat and grill the sandwich for 3 minutes " +
                        "or until cheese begins to melt. Then dig in while it is " +
                        "still warm."));
        addItem(new FoodItem("8", "8. Fruit Salad \t\t\t\t\t\t\t\t\t\t\t\t$2.99", "8. Fruit Salad", "$2.99", R.drawable.fuit_salad,
                "This wonder ful medley of fruits can be" +
                        " made of any fruits you have lying around " +
                        "the house. It makes a very good picnic, potluck or dessert" +
                        "dish. It is sure to keep yo hydrates on a hot day as well!",
                "5 Different Fruits Cubed, Black Pepper, Red Pepper, Sugar.",
                "Dust the cubed fruits with both peppers and sugar. Enjoy!"));
        addItem(new FoodItem("9", "9. Dry Fruit Mix \t\t\t\t\t\t\t\t\t\t\t$1.99", "9. Dry Fruit Mix", "$1.99", R.drawable.dry_fruit,
                "A great source of protein and goof fats! " +
                        "This dish gives you a boost of energy " +
                        "that will last a long time. It undoubtedly " +
                        "makes a great snack!",
                "Cashews, Almonds, Crainsins, Raisins, Dried Pineapple, Banana Chips, Dried Papaya.",
                "Mix 1/2 cup of each ingredient in a bowl. Portion " +
                        "into snack sized ZipLocks and you're ready to go!"));
        addItem(new FoodItem("10", "10. Hot Chocolate \t\t\t\t\t\t\t\t\t\t$1.99", "10. Hot Chocolate", "$1.99", R.drawable.hot_chocolate,
                "O the irresistable creamy, sweet warmth and aroma " +
                        "of hot chocolate. The perfect relaxing drink " +
                        "that will warm your frozen toes in the cold " +
                        "winter monts. O how we love hot chocolate!",
                "Hot Chocolate Mix Packet, Water.",
                "Warm up the water until it boils, then pour the contents of the packet inside " +
                        "the hot water. Stir until all of the powder dissovles and drink" +
                        " your worries away!"));
    }
}

