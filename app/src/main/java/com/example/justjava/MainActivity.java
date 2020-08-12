package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void increments(View view) {
        if(quantity == 100) {
            Toast.makeText(this, "You Cant go more that 100 Cups at once.", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity+1;
        display(quantity);
    }
    public void decrements(View view) {
        if(quantity == 1) {
            Toast.makeText(this, "You Cant go less that 1 Cup.", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity-1;
        display(quantity);
    }
    public void submitOrder(View view) {
        EditText text = (EditText) findViewById(R.id.name_field);
        String value = text.getText().toString();

        CheckBox WhippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = WhippedCreamCheckBox.isChecked();

        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = ChocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String orderSumary = createOrderSummary(price,value,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + value);
        intent.putExtra(Intent.EXTRA_TEXT, orderSumary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
    private String createOrderSummary(int price,String name, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd Whipped Cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price + "\nThank You!";
        return priceMessage;

    }
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if(addWhippedCream) {
            basePrice += 1;
        }
        if(addChocolate) {
            basePrice += 2;
        }
        return (quantity * basePrice);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
