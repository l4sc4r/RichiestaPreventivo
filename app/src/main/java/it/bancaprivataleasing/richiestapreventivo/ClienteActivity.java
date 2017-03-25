package it.bancaprivataleasing.richiestapreventivo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ClienteActivity extends StepActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        // set an onKeyListener to the description View so that when ENTER is pressed
        // the focus shifts to the next button
        EditText description_view = (EditText) findViewById(R.id.description_view);

        description_view.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                Log.v("ClienteActivity", "ActionInfo: " + actionId);
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        preventivo = null;
    }

    @Override
    public void next(View v) {

        // Check for all mandatory fields before proceeding to the next step
        String warnings = "";

        TextView customerView = (TextView) findViewById(R.id.customer_view);
        if (customerView.getText().toString().equals("")) {
            warnings += getResources().getString(R.string.customer_name_needed);
        }

        TextView vatNumberView = (TextView) findViewById(R.id.vat_number_view);
        // Checks if the VAT number is valid (blank or null is fine); if not, give warning and get out
        if (vatNumberView.getText() != null &&
                !vatNumberView.getText().toString().trim().equals("") &&
                !RichiestaPreventivo.isValidVatNumber(vatNumberView.getText().toString())) {
            if ( !warnings.equals("") ) warnings += "\n";
            warnings += getResources().getString(R.string.invalid_vat_number);
        }

        TextView descriptionView = (TextView) findViewById(R.id.description_view);
        if (descriptionView.getText().toString().equals("")) {
            if ( !warnings.equals("") ) warnings += "\n";
            warnings += getResources().getString(R.string.description_needed);
        }

        if ( !warnings.equals("")) {
            Toast.makeText(this, warnings , Toast.LENGTH_SHORT).show();
            return;
        }

        // Great! All fields have been filled. Commit the changes and proceed to the next step
        preventivo.setCustomer(customerView.getText().toString());
        preventivo.setVatNumber(vatNumberView.getText().toString());
        preventivo.setSubject(descriptionView.getText().toString());

        super.next(v);
    }
}
