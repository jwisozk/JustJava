package com.example.android.justjava;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("quantity", quantity);
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        outState.putString("price_text", priceTextView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        quantity = savedInstanceState.getInt("quantity", 0);
        String priceMessage = savedInstanceState.getString("price_text", "0");

        display(quantity);
        displayMessage(priceMessage);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        checkChocolate();
        checkWhippedCream();
        getName();
        String message = createOrderSummary(quantity, hasWhippedCream, hasChocolate, name);
        displayMessage(message);
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java");
//        intent.putExtra(Intent.EXTRA_TEXT, message);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
    }


    private void checkWhippedCream() {
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCream = whippedCreamCheckBox.isChecked();
    }

    private void checkChocolate() {
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        hasChocolate = chocolateCheckBox.isChecked();
    }

    private void getName() {
        EditText nameTextView = findViewById(R.id.name_edit_text_view);
        name = nameTextView.getText().toString();
    }

    private String createOrderSummary(int quantity, boolean hasWhippedCream, boolean hasChocolate, String name)
    {
        String s ;
        s = getString(R.string.order_name, name) + "\n";
        s += getString(R.string.add_whipped_cream) + hasWhippedCream + "\n";
        s += getString(R.string.add_chocolate) + hasChocolate + "\n";
        s += getString(R.string.quantity) + ": "+ quantity + "\n";
        s += getString(R.string.total) + calculatePrice(quantity) + "\n";
        s += getString(R.string.thank_you);
        return s;
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity) {
        int toppings = 0;

        if (hasWhippedCream)
            toppings++;
        if (hasChocolate)
            toppings += 2;
        return (5 + toppings) * quantity;
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
            display(quantity);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "You cannot have more than 100 coffee",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity--;
            display(quantity);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "You cannot have less than 1 coffee",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
