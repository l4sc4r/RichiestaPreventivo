package it.bancaprivataleasing.richiestapreventivo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.R.attr.visible;

public class StepActivity extends AppCompatActivity {

    private static final Class[] activityFlow = {
            it.bancaprivataleasing.richiestapreventivo.ImportoActivity.class,
            it.bancaprivataleasing.richiestapreventivo.AnticipoActivity.class,
            it.bancaprivataleasing.richiestapreventivo.RiscattoActivity.class,
            it.bancaprivataleasing.richiestapreventivo.DurataActivity.class,
            it.bancaprivataleasing.richiestapreventivo.AssicurazioneActivity.class,
            ClienteActivity.class};

    protected RichiestaPreventivo preventivo;

    protected int ACTION_CLOSE = -1;
    protected int ACTION_NONE = 0;
    protected String ACTION_EXTRA = "ACTION_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preventivo = RichiestaPreventivo.createRichiestaPreventivoFromIntent(getIntent());

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setBackButtonVisible(getCurrentStepIndex() != 0);

        // TODO cose da fare all'avvio
        // Al primo login controlla se è impostato il parametro USERNAME
        // se non lo è occorre accertarsi del collegamento a internet
        // senza collegamento non si può proseguire
        // Ora mostriamo una pagina di Login che chieda solo lo username
        // Cerchiamo lo username su un file di properties presente in internet
        // Se lo username esiste ne carichiamo le informazioni di default nei nostri parametri interni
        // Se non esiste avvisiamo l'utente dell'errore e lo facciamo riprovare. Da qui può anche uscire

    }

    /**
     * Since the order of the activity steps in the series may be changed, the back button may
     * not be visibile in the firse step. Hence, no layout defines a back button but places
     * a placeholder in its place instead. When creating, each step activity must call this
     * method to set the back button visibile according to its position in the series.
     *
     * @param visible whether the back button must be visibile or not
     */
    protected void setBackButtonVisible(boolean visible) {
        View backButton= findViewById(R.id.back_button);
        if ( visible ) {
            backButton.setVisibility(View.VISIBLE);
        } else {
            backButton.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * Performed when the back button is clicked.
     * Does nothing but call back(). Conveniency method to be attached to the xml layout file
     *
     * @param v the button view
     */
    public void back(View v) {
        back(ACTION_NONE);
    }

    /**
     * Exits the current activity returning the intent it originated from as a result value.
     */
    public void back(int action) {
        getIntent().putExtra(ACTION_EXTRA, action);

        setResult(Activity.RESULT_CANCELED, getIntent());
        finish();
    }


    @Override
    public void onBackPressed() {
        back(ACTION_CLOSE);
    }

    /**
     * Performed when the next button is clicked.
     * Override this method to perform any check before proceeding to the next step.
     * This is where all changes made by the activity must be committed.
     *
     *
     * @param v the button view
     */
    public void next(View v) {
        // Check if this is the last step in the series.
        int currentStepIndex = getCurrentStepIndex();
        if (currentStepIndex < activityFlow.length - 1) {

            setResult(Activity.RESULT_OK, getIntent());
            Class nextStep = activityFlow[getCurrentStepIndex() + 1];

            int request_code = 1;
            Intent intent = new Intent(this, nextStep);
            RichiestaPreventivo.setExtrasToIntent(intent, preventivo);
            startActivityForResult(intent, request_code);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( data.getIntExtra(ACTION_EXTRA, ACTION_NONE) == ACTION_CLOSE ) {
            back(ACTION_CLOSE);
        }

        if ( resultCode == Activity.RESULT_CANCELED ) {
            RichiestaPreventivo.setExtrasFromIntent(preventivo, data);
        }
    }

    private int getCurrentStepIndex() {
        Class currentStep = this.getClass();
        int step = 0;
        while ( step < activityFlow.length -1 ) {
            if ( currentStep.toString().equals(activityFlow[step].toString())) {
                break;
            }
            step = step + 1;
        }
        return step;
    }

}
