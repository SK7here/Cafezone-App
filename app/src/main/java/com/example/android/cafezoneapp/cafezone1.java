package com.example.android.cafezoneapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class cafezone1 extends AppCompatActivity {

    int noOfItems = 0;
    int costOfOne = 20;
    int sumNoOfItems = 0;
    int sumcost = 0;
    int xtraFare = 0;

    TextView quant;
    TextView money;
    TextView summary;

    EditText namefield;
    EditText emailfield;

    CheckBox toppinVan ;
    CheckBox toppinChoco;

    String toppinVanstatus = "Try out delicious Vanilla Topping";
    String toppinChocostatus = "Try out mouth-watering Choco Topping";
    String msg;
    String orderInfo;
    String summsg;
    String pname;
    String emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafezone1);

        quant = (TextView) findViewById(R.id.quantity); /** variable for quantity field **/
        money = (TextView) findViewById(R.id.amt); /** variable for price field **/
        summary = (TextView) findViewById(R.id.textView); /** variable for purchase summary field **/
        toppinVan = (CheckBox) findViewById(R.id.checkBox); /** variable for topping checkbox **/
        toppinChoco = (CheckBox) findViewById(R.id.checkBox2); /** variable for topping checkbox **/
        namefield = (EditText) findViewById(R.id.editText); /** varaible for name field **/
        emailfield = (EditText) findViewById(R.id.editText2); /** variable for email field **/
    }

    public void incrementOrder(View view) {
        noOfItems = noOfItems + 1; /** This will add the order by 1 **/
        displayquantity(noOfItems); /** This will call the display function. **/
        displayamt(noOfItems * costOfOne); /** This will call displayamt function. **/
    }

    public void decrementOrder(View view) {
        if(noOfItems >= 1)
        {
            noOfItems = noOfItems - 1; /** This will decrease the order by 1 **/
            displayquantity(noOfItems); /** This will call the displayquantity function. **/
            displayamt(noOfItems * costOfOne);/** This will call displayamt function. **/
        }
    }

    public void submitOrder(View view) {
        if (toppinVan.isChecked())
        {
            xtraFare += 5;
            toppinVanstatus = "Ordered for Vanilla Topping";
            toppinChocostatus = "Try out mouth-watering Choco Topping";
        }
        if(toppinChoco.isChecked())
        {
            xtraFare += 10;
            toppinChocostatus = "Ordered for Choco Topping";
            toppinVanstatus = "Try out delicious Vanilla Topping";
        }
        if(toppinChoco.isChecked() && toppinVan.isChecked())
        {
            toppinVanstatus = "Ordered for Vanilla Topping";
            toppinChocostatus = "Ordered for Choco Topping";
        }

        msg = "Order: " + noOfItems + "\nCost: " +( (noOfItems * costOfOne) + (noOfItems * xtraFare) ); /** Msg to be displayed on clicking order**/
        sumNoOfItems += noOfItems;  /** Adding the order quantity for summary **/
        sumcost += ( (noOfItems * costOfOne) + (noOfItems * xtraFare) );  /** Adding the order amount for summary **/

        String orderTracker = "OrderTracker: " + "\nOrder: " + sumNoOfItems + "\nCost: " + (sumcost) +"\n"; /** Orderlist cost so far **/
        orderTracker += toppinVanstatus + "\n" +  toppinChocostatus ; /** Toppings status **/
        summary.setText(orderTracker); /** Displaying order in Summary text field **/

        noOfItems = 0; /** Initializing again to 0 for next order of the purchase **/
        xtraFare = 0; /** Initializing again to 0 for next order of the purchase **/
        toppinVan.setChecked(false); /**Unchecking again for next order of the purchase **/
        toppinChoco.setChecked(false); /**Unchecking for next order of the purchase **/
        displayquantity(0);/** Resetting for next order **/
        displayamt(0);/** Resetting for next order **/


    }

    public void submitSummary(View view) {
        pname = namefield.getText().toString();
        emailId = emailfield.getText().toString();

        summsg = "Greetings " + pname;
        summsg += "\nTotal No of items Ordered: " + sumNoOfItems + "\nTotal Cost: " + sumcost;
        summary.setText(summsg);

        Intent mailIntent = new Intent(Intent.ACTION_SENDTO); /**Intent declaration for sending mail **/
        mailIntent.setData(Uri.parse("mailto:" + emailId)); /** To activate the mail intent **/
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Cafepoint order for " + pname); /** Subject of mail **/
        mailIntent.putExtra(Intent.EXTRA_TEXT, summsg ); /** Body for mail **/
        if(( mailIntent.resolveActivity(getPackageManager()) ) != null)/** Ensure presence of target device **/
            startActivity(mailIntent);

        displayamt(0);
        displayquantity(0);
        /** This displays summary of purchase on clicking summary**/
    }

    public void listReset(View view){
        toppinVan.setChecked(false); /**Unchecking again for next order of the purchase **/
        toppinChoco.setChecked(false);
        sumNoOfItems = 0;
        sumcost = 0;
        noOfItems = 0;
        xtraFare = 0;
        displayamt(0);
        displayquantity(0);
        toppinVanstatus = "Try out delicious Vanilla Topping";
        toppinChocostatus = " Try out mouth-watering Choco Topping";
        pname = " ";
        namefield.setText(null);
        emailId = " ";
        emailfield.setText(null);
        orderInfo = "Coffee: Rs.20 \nChocolate Topping: Rs.10 \nVanilla Topping: Rs.5 ";
        orderInfo += "\n\nKindly make your order for all coffee with same toppings in a single order ";
        summary.setText(orderInfo + "\n\nPress RESET for next purchase");

        /** This resets quantity,amount,Strings and checkboxes also displays it **/
    }

    private void displayquantity(int count) {
        quant.setText("" + count);

        /** This method displays the given quantity value on the screen...**/
        /** Private coz it can be called only by display function **/
    }

    private void displayamt(int cash) {
        /** money.setText(NumberFormat.getCurrencyInstance().format(cash)); */
        money.setText("" + cash);

        /** This method displays the given cost value on the screen...**/
        /** Private coz it can be called only by display function **/
    }
    /** String resource for lenght string - in xml (info)**/

}
