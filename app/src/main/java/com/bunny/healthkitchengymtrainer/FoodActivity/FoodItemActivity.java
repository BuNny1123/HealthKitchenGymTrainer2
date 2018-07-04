package com.bunny.healthkitchengymtrainer.FoodActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bunny.healthkitchengymtrainer.Model.Foods;
import com.bunny.healthkitchengymtrainer.R;

public class FoodItemActivity extends AppCompatActivity {

    Foods foodItem;
    ImageView foodImage;
    TextView foodName, foodDesc, foodPrice;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);

        foodItem  = getIntent().getParcelableExtra("FoodItem");
        Toast.makeText(this, "" + foodItem.getKey(), Toast.LENGTH_SHORT).show();

        foodImage = (ImageView) findViewById(R.id.foodImage_foodItemActivity);
        foodName = (TextView) findViewById(R.id.foodName_foodItmeActivity);
        foodDesc = (TextView) findViewById(R.id.foodDesc_foodItemActivity);
        foodPrice = (TextView) findViewById(R.id.foodPrice_foodItemActivity);
        Glide.with(this)
                .load(foodItem.getImage())
                .asBitmap()
                .centerCrop()
                .into(foodImage);
        foodName.setText(foodItem.getName());
        foodDesc.setText(foodItem.getDescription());
        foodPrice.setText("Rs " + foodItem.getPrice());
    }
}
