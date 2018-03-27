package com.teamnamenotfoundexception.hoteller.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

public class DishRepository {

    public static final String TAG = "DishRepository";
    private static DishRepository mDishRepository = null;
    private static DatabaseHelper mDatabaseHelper;
    private static ArrayList<DishItem> mDishItemsList = null;
    private Context mAppContext;
    private SharedPreferences mSharedPref;

    private DishRepository(Context context) {
        mAppContext = context;
        mDatabaseHelper = new DatabaseHelper(mAppContext);
        mDishItemsList = new ArrayList<>();
        mSharedPref = mAppContext.getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }

    public static DishRepository get(Context c) {
        if (mDishRepository == null) {
            mDishRepository = new DishRepository(c);
        }
        return mDishRepository;
    }

    public static void setDishRepository(DishRepository dishRepository) {
        mDishRepository = dishRepository;
    }

    public void insertAllDishItems() {
        boolean isInsertedBefore = !mSharedPref.getString("inserted_before", "0").equals("0");
        if (!isInsertedBefore) {
            mDatabaseHelper.insertDishItem(new DishItem(1001, "Chicken Biriyani", "NonVeg", 120, "Who doesn't love Biriyani? Come, experience the taste of delight.", "http://blogs.dunyanews.tv/urdu/wp-content/uploads/2018/03/d815e816-4664-472e-990b-d880be41499f-chicken-biryani-recipe.jpg", "biryani, rice"));
            mDatabaseHelper.insertDishItem(new DishItem(1002, "Chicken Tandoori", "NonVeg", 400, "Relish this everyone's endearing chicken that leaves you stomach-full.", "https://i2.wp.com/media.hungryforever.com/wp-content/uploads/2017/05/10125620/feature-image-tandoori-chicken.jpg?w=1269&strip=all&quality=80", "tandoori, chicken"));
            mDatabaseHelper.insertDishItem(new DishItem(1003, "Chicken Kabab", "NonVeg", 200, "Kabab is our most dearest dish that makes you ask for more!", "https://s-media-cache-ak0.pinimg.com/originals/d1/e4/5e/d1e45e24af1bb5603647e737ba80c341.jpg", "kabab, chicken"));
            mDatabaseHelper.insertDishItem(new DishItem(1004, "Chicken Butter Masala", "NonVeg", 200, "Curry yourself up!", "https://i.ytimg.com/vi/a03U45jFxOI/maxresdefault.jpg", "butter, chicken, gravy"));
            mDatabaseHelper.insertDishItem(new DishItem(1005, "Chicken Grill", "NonVeg", 400, "Brace yourselves! Chicken Grill is here.!", "https://fthmb.tqn.com/OofIElcuRkRtwdXyjJHCf5O6DIw=/3865x2576/filters:no_upscale()/two-grilled-chicken-legs-green-onion-dill-and-lime-on-wooden-board-viewed-from-above-508738308-57628f9a3df78c98dcfa5fd8.jpg", "grill, chicken"));
            mDatabaseHelper.insertDishItem(new DishItem(1006, "Chicken Tikka", "NonVeg", 200, "Chicken of it's own kind when it comes to providing enchanting experience", "https://i2.wp.com/media.hungryforever.com/wp-content/uploads/2015/12/09103934/CHicken-REcipe.jpg?w=1269&strip=all&quality=80", "tikka, spicy"));
            mDatabaseHelper.insertDishItem(new DishItem(1007, "Parota", "Veg", 30, "A Indian bread that is slightly hard, but is ready to melt for you  ", "http://2.bp.blogspot.com/-Sdznt6PnBek/Vq0QwPPNY0I/AAAAAAAAGCs/OtdydMZShPU/s1600/IMG_3958.JPG", "bread, roti, naan"));
            mDatabaseHelper.insertDishItem(new DishItem(1008, "Kadai Chicken", "NonVeg", 150, "Kadai Chicken is your go-to-curry if you care for your taste-buds", "https://1.bp.blogspot.com/-CEd6WNb8EJk/WbTHc8OiVEI/AAAAAAAAv6U/LFdVSfVMAF0OEC6_JaS4zOZq6h2nMM44QCLcBGAs/s1600/3-DSC_0055.JPG", "kadai, curry"));
            mDatabaseHelper.insertDishItem(new DishItem(1009, "Chicken Kurma", "NonVeg", 150, "Eat this beautiful dish, get a short ride to heaven", "http://2.bp.blogspot.com/-zGBkb2rHn6s/T38fvkieQAI/AAAAAAAAO5c/FhlowzCCeXc/s1600/CHIC+KORMA+FIVE.jpg", "kurma, curry"));
            mDatabaseHelper.insertDishItem(new DishItem(1010, "Gobi Manchurian", "Veg", 100, "Fried and seasoned gobi that melts in your mouth.", "https://savourexoticwithsari.files.wordpress.com/2015/01/dscn4900.jpg", "gobi, fry"));
            mDatabaseHelper.insertDishItem(new DishItem(1011, "Vegetable Pulav", "Veg", 100, "A dish of cooked rice that is cooked in a seasoned broth.", "https://droolingfoodies.com/wp-content/uploads/2018/02/veg-biryani.jpg", "basmati, pulav"));
            mDatabaseHelper.insertDishItem(new DishItem(1012, "Ghee Rice", "Veg", 100, "Rice fried with ghee with an everlasting aroma!", "https://3.bp.blogspot.com/-ftkd4cXor5I/WMHi77gQhcI/AAAAAAAAC6c/hcVkvqJ9LZMedaqhqzJQX8RFwXgcqRt4ACLcB/s640/Ghee%2BRice.JPG", "ghee, basmati"));
            mDatabaseHelper.insertDishItem(new DishItem(1013, "Egg Fried Rice", "NonVeg", 100, "A dish of cooked rice that has been stir-fried in a wok.", "https://indianhealthyrecipes.com/wp-content/uploads/2015/12/schezwan-egg-fried-rice-14.jpg", "rice, fried"));
            mDatabaseHelper.insertDishItem(new DishItem(1014, "Paneer Butter Masala", "Veg", 150, "Marinated paneer cheese served in a spiced gravy that melts in your mouth.", "https://i.ytimg.com/vi/-BF5H9-Xoh0/maxresdefault.jpg", "paneer, masala"));
            mDatabaseHelper.insertDishItem(new DishItem(1015, "Falooda", "Veg", 100, "A perfect drink to calm your senses!", "http://images-cdn.azureedge.net/azure/in-resources/5294459a-2f80-4db0-a890-4d891abc5ce6/Images/ProductImages/Source/Rose-Falooda.JPG", "dessert, sweet"));
            SharedPreferences.Editor editor = mSharedPref.edit();
            editor.putString("inserted_before", "1");
            editor.apply();
            editor.commit();
        }
    }

    public void initializeDishItemsList() {
        if (DishRepository.get(mAppContext).getDishItemsList().size() == 0) {
            DatabaseHelper.DishItemCursor mDishItemCursor = mDatabaseHelper.getAllDishItems();
            if (mDishItemCursor.getCount() != 0) {
                mDishItemCursor.moveToFirst();
                while (!mDishItemCursor.isAfterLast()) {
                    DishItem dishItem = mDishItemCursor.getDishItem();
                    mDishItemsList.add(dishItem);
                    mDishItemCursor.moveToNext();
                }
            }
            mDishItemCursor.close();
        }
    }

    public ArrayList<DishItem> getDishItemsList() {
        return mDishItemsList;
    }
}
