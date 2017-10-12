package outquirk.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import outquirk.justjava.R;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity
    {

        @Override
        protected void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
            }

        public int numberOfCoffees = 2;
        public int costOfCoffee = 5;


        boolean addWhippedCream;

        boolean addChocolate;


        public void addCoffee(View view)
            {
                if(numberOfCoffees<100) {
                    numberOfCoffees++;
                    display(numberOfCoffees);
                }
                else
                    Toast.makeText(getApplicationContext(), R.string.max_coffee_toast,Toast.LENGTH_SHORT).show();

            }

        public void removeCoffee(View view)
            {
                if (numberOfCoffees > 1) {
                    numberOfCoffees--;
                    display(numberOfCoffees);
                }

                else
                    Toast.makeText(getApplicationContext(), R.string.min_coffee_toast,Toast.LENGTH_SHORT).show();
            }


        /**
         * This method is called when the order button is clicked.
         */
        public void submitOrder(View view)
            {
                CheckBox hasWhippedCream = (CheckBox) findViewById(R.id.checkbox_whippedCream);
                addWhippedCream = hasWhippedCream.isChecked();
                CheckBox hasChocolate = (CheckBox)findViewById(R.id.checkbox_Choclate);
                addChocolate = hasChocolate.isChecked();

                Intent emailOrder = new Intent(Intent.ACTION_SENDTO);
                emailOrder.setData(Uri.parse("mailto:"));
                emailOrder.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.intent_mail_subject));
                emailOrder.putExtra(Intent.EXTRA_EMAIL, new String [] {getString(R.string.intent_mail_to_field)});
                String priceMessage = orderSummary(numberOfCoffees,addWhippedCream,addChocolate);
                priceMessage +=String.valueOf(NumberFormat.getCurrencyInstance().format(calculatePrice()))+"\n" +getString(R.string.thank_you);

                emailOrder.putExtra(Intent.EXTRA_TEXT,priceMessage);


                if (emailOrder.resolveActivity(getPackageManager()) != null) {

                    startActivity(emailOrder);
                }

            }

        /**
         * This method displays the given quantity value on the screen.
         */
        private void display(int number)
            {
                TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
                quantityTextView.setText(String.format("%d", number));
            }

        private int calculatePrice()
            {
                if(addWhippedCream)
                    costOfCoffee +=1;
                if(addChocolate)
                    costOfCoffee +=2;
                    return costOfCoffee*numberOfCoffees ;
            }

        private String orderSummary(int numberOfCoffees, boolean hasWhippedCream, boolean hasChocolate)
            {

                EditText Name = (EditText) findViewById(R.id.NameField);
                return getString(R.string.name_order_summary) + Name.getText() + "\n" + getString(R.string.quantity_order_summary) + numberOfCoffees +"\n" +getString(R.string.include_whipped_cream_order_summary)+hasWhippedCream+"\n" +getString(R.string.include_chocolate_order_summary)+hasChocolate+"\n";
            }

    }