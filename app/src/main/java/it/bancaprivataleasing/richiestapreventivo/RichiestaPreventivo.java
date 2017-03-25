package it.bancaprivataleasing.richiestapreventivo;

import android.content.Intent;
import android.util.Log;

/**
 * An instance of this class represents a quotation request.
 * The aim of the object is to collect all information needed to ask for a quotation.
 * It also provides static methods to check the validity of the data collected by the activities
 * so that the logic is stored in a single place.
 *
 * Created by Lavoro on 21/03/2017.
 */

public class RichiestaPreventivo {

    public final static String INTENT_REQUEST = "INTENT_REQUEST";

    private final static String CUSTOMER_EXTRA = "CUSTOMER_EXTRA";
    private final static String VAT_NUMBER_EXTRA = "VAT_NUMBER_EXTRA";
    private final static String SUBJECT_EXTRA = "SUBJET_EXTRA";

    private String customer;
    private String vatNumber;
    private String subject;

    public RichiestaPreventivo() {

    }

    /**
     * Clear all fields of the instance.
     *
     */
    public void clear() {
        customer = null;
        vatNumber = null;
        subject = null;
    }

    /**
     * Sets the customer name requesting the quotation
     *
     * @param customer the customer name or description
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     * Returns the customer requesting the quotation
     *
     * @return the name or description of the customer
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * Sets the customer's VAT number
     *
     * @param vatNumber of the customer
     */
    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber ;
    }

    /**
     * Returns the customer's VAT number
     *
     * @return the customer's VAT number
     */
    public String getVatNumber() {
        return vatNumber;
    }


    /**
     * Sets the subject of the current quotation request
     *
     * @param subject of the quotation
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Returns the subject of the current quotation request
     *
     * @return the subject of the current quotation request
     */
    public String getSubject() {
        return subject;
    }


    /**
     * Checks the validity of a String to be used as VAT number.
     * The length of the argument must be either 16 ot 11.
     * When 16, must be composed both of numbers and letters.
     * When 11, must be composed of numbers.
     *
     * @TODO more sophisticated checks can be performed
     *
     * @return whether the argument is valid or not as a VAT number
     */
    public static boolean isValidVatNumber(String v) {
        if (v.length() != 11 && v.length() != 16) return false;

        if (v.length() == 11 && !isInteger(v)) return false;

        return true;

    }

    /**
     * Checks if a String only contains numbers.
     * @param s
     * @return true if the String can be represented as an int.
     */
    private static boolean isInteger(String s) {
        for (int a = 0; a < s.length(); a++) {
            if (a == 0 && s.charAt(a) == '-') continue;
            if (!Character.isDigit(s.charAt(a))) return false;
        }
        return true;
    }

    /**
     * Passes all fields of a given RichiestaPreventivo instance to an Intent as EXTRAS to
     * be shared by multiple activities.
     *
     * @param intent whose extras must be set
     * @param richiestaPreventivo whose fields must be used as extras
     */
    public static void setExtrasToIntent(Intent intent, RichiestaPreventivo richiestaPreventivo) {
        Log.v("RichiestaPreventivo", "setExtrasTOIntent");
        intent.putExtra(CUSTOMER_EXTRA, richiestaPreventivo.getCustomer());
        intent.putExtra(VAT_NUMBER_EXTRA, richiestaPreventivo.getVatNumber());
        intent.putExtra(SUBJECT_EXTRA, richiestaPreventivo.getSubject());
    }

    /**
     * Passes all fields of a given RichiestaPreventivo instance to an Intent as EXTRAS to
     * be shared by multiple activities.
     *
     * @param intent whose extras must be set
     * @param richiestaPreventivo whose fields must be used as extras
     */
    public static void setExtrasFromIntent(RichiestaPreventivo richiestaPreventivo, Intent intent) {
        Log.v("RichiestaPreventivo", "setExtrasFROMIntent");
        richiestaPreventivo.setCustomer(intent.getStringExtra(CUSTOMER_EXTRA));
        richiestaPreventivo.setVatNumber(intent.getStringExtra(VAT_NUMBER_EXTRA));
        richiestaPreventivo.setSubject(intent.getStringExtra(SUBJECT_EXTRA));
    }

    public static RichiestaPreventivo createRichiestaPreventivoFromIntent(Intent intent) {
        Log.v("RichiestaPreventivo", "createRichiestaPreventivo");
        RichiestaPreventivo preventivo = new RichiestaPreventivo();

        if ( intent.getStringExtra(CUSTOMER_EXTRA) != null ) preventivo.setCustomer(intent.getStringExtra(CUSTOMER_EXTRA));
        if ( intent.getStringExtra(VAT_NUMBER_EXTRA) != null ) preventivo.setVatNumber(intent.getStringExtra(VAT_NUMBER_EXTRA));
        if ( intent.getStringExtra(SUBJECT_EXTRA) != null ) preventivo.setSubject(intent.getStringExtra(SUBJECT_EXTRA));

        return preventivo;
    }

}
