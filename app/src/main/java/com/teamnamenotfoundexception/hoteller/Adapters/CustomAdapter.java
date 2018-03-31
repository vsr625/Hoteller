package com.teamnamenotfoundexception.hoteller.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.github.zagum.switchicon.SwitchIconView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.shawnlin.numberpicker.NumberPicker;
import com.teamnamenotfoundexception.hoteller.Activities.CartActivity;
import com.teamnamenotfoundexception.hoteller.Activities.FavoriteActivity;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private List<DishItem> dishItems;
    public static int chosenQuantity = 1;
    private Activity activity;

    public CustomAdapter(Context mContext, List<DishItem> mDishItems) {
        context = mContext;
        dishItems = mDishItems;
        activity = (Activity) mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dishentrycart, parent, false);
        return new ViewHolder(v);
    }

    public void setData(List<DishItem> items) {
        dishItems = new ArrayList<>();
        dishItems.addAll(items);
        notifyDataSetChanged();
    }

    // For each of the items to display
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final DishItem dishItem = dishItems.get(position);
        holder.dishTitle.setText(dishItem.getDishName());
        holder.dishCat.setText(dishItem.getDishType());
        holder.tags.setText(dishItem.getTags());
        holder.dishCost.setText(dishItem.getPrice() + "");

        // Download and set image for the dish
        Glide.with(context).load(dishItem.getImagePath()).into(holder.dishImage);

        if (context instanceof CartActivity) {
            holder.x.setVisibility(View.VISIBLE);
            holder.dishCount.setVisibility(View.VISIBLE);
            holder.dishCount.setText(dishItem.getQuantity() + "");
        } else {
            holder.x.setVisibility(View.GONE);
            holder.dishCount.setVisibility(View.GONE);
        }

//        boolean isFavorite = CartManager.get(context).getFavoriteIdList().contains(dishItem.getDishId());
//
//        if (isFavorite) {
//            dishItem.setDishFav(1);
//        } else {
//            dishItem.setDishFav(0);
//        }

        if (dishItem.getIsFav() == 1) holder.heartBtn.setIconEnabled(true, false);
        if (dishItem.getIsCart() == 1) holder.cartBtn.setIconEnabled(true, false);

        holder.heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartManager cartManager = CartManager.get(context);
                if (dishItem.isDishFav() == 0) {
                    dishItem.setDishFav(1);
                    //cartManager.addToFavorites(dishItem);
                    if (activity instanceof FavoriteActivity) {
                        setData(cartManager.getAllFavItems());
                    }
                    holder.heartBtn.setIconEnabled(true, true);
                    final StyleableToast styleableToast = StyleableToast.makeText(context, dishItem.getDishName() + " is added to favourites!", R.style.love_rm);
                    styleableToast.show();
                    android.os.Handler handler = new android.os.Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            styleableToast.cancel();
                        }
                    }, 1000);
                } else {
                    dishItem.setDishFav(0);
                    //cartManager.removeFromFavorites(dishItem);
                    if (activity instanceof FavoriteActivity) {
                        setData(cartManager.getAllFavItems());
                    }
                    holder.heartBtn.setIconEnabled(false, true);
                    final StyleableToast styleableToast = StyleableToast.makeText(context, dishItem.getDishName() + " is removed from favorites!", R.style.love_rm);
                    styleableToast.show();
                    android.os.Handler handler = new android.os.Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            styleableToast.cancel();
                        }
                    }, 1000);
                }
            }
        });

        holder.cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartManager cartManager = CartManager.get(context);
                if (dishItem.getIsCart() == 0) {
                    dishItem.setIsCart(1);
                    dishItem.setQuantity(1);
                    Log.i("cart clicked", "cart clicked on item" + dishItem.getPrice());
                    cartManager.addDishToCart(dishItem);
                    holder.cartBtn.setIconEnabled(true, true);
                    if (activity instanceof CartActivity) {
                        setData(cartManager.getCartItems());
                    }
                    final StyleableToast styleableToast = StyleableToast.makeText(context, dishItem.getDishName() + " is added to cart!", R.style.cart_add);
                    styleableToast.show();
                    android.os.Handler handler = new android.os.Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            styleableToast.cancel();
                        }
                    }, 1000);
                } else {
                    cartManager.removeDishFromCart(dishItem);
                    dishItem.setIsCart(0);
                    dishItem.setQuantity(0);
                    holder.cartBtn.setIconEnabled(false, true);
                    if (activity instanceof CartActivity) {
                        setData(cartManager.getCartItems());
                    }
                    final StyleableToast styleableToast = StyleableToast.makeText(context, dishItem.getDishName() + " is removed from cart!", R.style.cart_rm);
                    styleableToast.show();
                    android.os.Handler handler = new android.os.Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            styleableToast.cancel();
                        }
                    }, 1000);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView dishImage;
        public TextView dishCat, dishTitle, dishCost, dishCount, x, tags;
        public SwitchIconView cartBtn, heartBtn;

        public ViewHolder(final View itemView) {
            super(itemView);
            x = itemView.findViewById(R.id.x);
            dishCount = itemView.findViewById(R.id.dishCount);
            dishImage = itemView.findViewById(R.id.food_image);
            dishTitle = itemView.findViewById(R.id.dish_title);
            dishCost = itemView.findViewById(R.id.dish_cost);
            dishCat = itemView.findViewById(R.id.dish_category);
            heartBtn = itemView.findViewById(R.id.favBtn);
            tags = itemView.findViewById(R.id.tags);
            cartBtn = itemView.findViewById(R.id.cartBtn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.food_description_dialog, null);
            TextView des = view.findViewById(R.id.dishDescription);
            NumberPicker numberPicker = view.findViewById(R.id.dishCount);
            final DishItem dishItem = dishItems.get(getLayoutPosition());
            final CartManager cartManager = CartManager.get(context);

            new MaterialStyledDialog.Builder(context)
                    .setTitle(dishItem.getDishName())
                    .setStyle(Style.HEADER_WITH_TITLE)
                    .setPositiveText("Order")
                    .setCustomView(view, 5, 5, 5, 0)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dishItem.setQuantity(CustomAdapter.chosenQuantity);
                            if (dishItem.getIsCart() == 0) {
                                cartManager.addDishToCart(dishItem);
                                dishItem.setIsCart(1);
                            }
                            cartBtn.setIconEnabled(true);
                            dialog.dismiss();

                        }
                    })
                    .setNegativeText("Cancel")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .show();

            des.setText(dishItem.getDescription());
            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    CustomAdapter.chosenQuantity = newVal;
                }
            });
        }
    }
}
