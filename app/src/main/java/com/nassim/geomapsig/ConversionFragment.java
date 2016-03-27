package com.nassim.geomapsig;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConversionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConversionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConversionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String degresFormat = "^([0-8]?[0-9]|90)";
    private static final String minutesFormat = "(\\s[0-5]?[0-9]')";
    private static final String secondesFormat = "(\\s[0-5]?[0-9](,[0-9])?\")";

    Button buttonConversion, buttonReset;

    TextView txtLatitude, txtLongitude;
    TextView affichageConversionLatitude, affichageConversionLongitude;

    EditText latitudeDecimal, longitudeDecimal;

    public RadioGroup uniteCoordonnees;
    public RadioButton sexagecimale, decimal;

    public double textDegresLatitude, textMinutesLatitude, textSecondesLatitude;

    public double textDegresLongitude, textMinutesLongitude, textSecondesLongitude;

    public double textLatitude, textLongitude;

    private double resultatConversionLatitude, resultatConversionLongitude;

    EditText degresLatitude, minutesLatitude, secondesLatitude;
    EditText degresLongitude, minutesLongitude, secondesLongitude;

    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;

    public ConversionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConversionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConversionFragment newInstance(String param1, String param2) {
        ConversionFragment fragment = new ConversionFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View conversion_view = inflater.inflate(R.layout.fragment_conversion, container, false);

        //region Proprietes
        /*****************************************************************************************************/

        final NumberFormat formatterResultatDecimal = new DecimalFormat("#0.000000");

        buttonConversion = (Button) conversion_view.findViewById(R.id.Conversion);
        buttonReset = (Button) conversion_view.findViewById(R.id.Reset);

        uniteCoordonnees = (RadioGroup) conversion_view.findViewById(R.id.uniteCoordonnée);

        sexagecimale = (RadioButton) conversion_view.findViewById(R.id.sexagecimale);
        decimal = (RadioButton) conversion_view.findViewById(R.id.decimal);

        txtLatitude = (TextView) conversion_view.findViewById(R.id.textLatitude);
        txtLongitude = (TextView) conversion_view.findViewById(R.id.textLongitude);

        /*****************************************************************************************************/

        latitudeDecimal = (EditText) conversion_view.findViewById(R.id.latitude_decimal);
        longitudeDecimal = (EditText) conversion_view.findViewById(R.id.longitude_decimal);

        latitudeDecimal.setFilters(new InputFilter[]{new FilterActivity("0", "180")});
        longitudeDecimal.setFilters(new InputFilter[]{new FilterActivity("0", "180")});

        /*****************************************************************************************************/

        degresLatitude = (EditText) conversion_view.findViewById(R.id.degres_latitude);
        minutesLatitude = (EditText) conversion_view.findViewById(R.id.minutes_latitude);
        secondesLatitude = (EditText) conversion_view.findViewById(R.id.secondes_latitude);

        degresLatitude.setFilters(new InputFilter[]{new FilterActivity("0", "90")});
        minutesLatitude.setFilters(new InputFilter[]{new FilterActivity("0", "60")});
        secondesLatitude.setFilters(new InputFilter[]{new FilterActivity("0", "60")});

        /*****************************************************************************************************/

        degresLongitude = (EditText) conversion_view.findViewById(R.id.degres_longitude);
        minutesLongitude = (EditText) conversion_view.findViewById(R.id.minutes_longitude);
        secondesLongitude = (EditText) conversion_view.findViewById(R.id.secondes_longitude);

        degresLongitude.setFilters(new InputFilter[]{new FilterActivity("0", "90")});
        minutesLongitude.setFilters(new InputFilter[]{new FilterActivity("0", "60")});
        secondesLongitude.setFilters(new InputFilter[]{new FilterActivity("0", "60")});

        /*****************************************************************************************************/

        affichageConversionLatitude = (TextView) conversion_view.findViewById(R.id.affichageResultatLatitude);
        affichageConversionLongitude = (TextView) conversion_view.findViewById(R.id.affichageResultatLongitude);

        final LinearLayout latitudeSexagecimal = (LinearLayout) conversion_view.findViewById(R.id.latitude_sexagecimal);
        final LinearLayout longitudeSexagecimal = (LinearLayout) conversion_view.findViewById(R.id.longitude_sexagecimal);

        /*****************************************************************************************************/
        //endregion

        final TableLayout tableauCoordonnees = (TableLayout)conversion_view.findViewById(R.id.tableauCoordonneesLayout);
        final TableRow newTableRowForTable = new TableRow(conversion_view.getContext());
        final TextView newCityInTableLayout = new TextView(conversion_view.getContext());

        final FloatingActionButton fab = (FloatingActionButton) conversion_view.findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sexagecimale.isChecked()) {


                    Snackbar.make(view, "Coordonnées Sexagecimale rajouté au tableau", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else if (decimal.isChecked()) {

                    Snackbar.make(view, "Coordonnées Decimale rajouté au tableau", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        uniteCoordonnees.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.sexagecimale) {
                    latitudeSexagecimal.setVisibility(View.VISIBLE);
                    longitudeSexagecimal.setVisibility(View.VISIBLE);
                    latitudeDecimal.setVisibility(View.GONE);
                    longitudeDecimal.setVisibility(View.GONE);

                } else if (checkedId == R.id.decimal) {
                    latitudeSexagecimal.setVisibility(View.GONE);
                    longitudeSexagecimal.setVisibility(View.GONE);
                    latitudeDecimal.setVisibility(View.VISIBLE);
                    longitudeDecimal.setVisibility(View.VISIBLE);
                }
            }
        });

        View.OnClickListener ClickButton = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.Conversion:
                        if (sexagecimale.isChecked()) {

                            //region ConversionSexaDeci
                            textDegresLatitude = Double.parseDouble(degresLatitude.getText().toString());
                            textMinutesLatitude = Double.parseDouble(minutesLatitude.getText().toString());
                            textSecondesLatitude = Double.parseDouble(secondesLatitude.getText().toString());

                            textDegresLongitude = Double.parseDouble(degresLongitude.getText().toString());
                            textMinutesLongitude = Double.parseDouble(minutesLongitude.getText().toString());
                            textSecondesLongitude = Double.parseDouble(secondesLongitude.getText().toString());

                            resultatConversionLatitude = textDegresLatitude + textMinutesLatitude / 60 + textSecondesLatitude / 3600;
                            resultatConversionLongitude = textDegresLongitude + textMinutesLongitude / 60 + textSecondesLongitude / 3600;

                            affichageConversionLatitude.setText(String.valueOf("Latitude : " + formatterResultatDecimal.format(resultatConversionLatitude) + "°"));
                            affichageConversionLongitude.setText(String.valueOf("Longitude : " + formatterResultatDecimal.format(resultatConversionLongitude) + "°"));
                            //endregion

                            fab.show();

                        } else if (decimal.isChecked()) {

                            //region ConversionDeciSexa
                            /*********************************************************************************************
                            textLatitude = Double.parseDouble(latitudeDecimal.getText().toString());
                            String signlat;

                            if (textLatitude < 0) {
                                signlat = "-";
                            } else {
                                signlat = "";
                            }

                            textLatitude = Math.abs(Math.round(resultatConversionLatitude * 1000000.));
                            if (textLatitude > (90 * 1000000)) {
                                Toast.makeText(getActivity().getApplicationContext(), " Les degrés de latitude doivent être compris entre -90 et +90°.",
                                        Toast.LENGTH_SHORT).show();
                                resultatConversionLatitude = Double.parseDouble("");
                                textLatitude = 0;
                            }

                            resultatConversionLatitude = Double.parseDouble(signlat + ((Math.floor(textLatitude / 1000000)) + "° "
                                    + Math.floor(((textLatitude / 1000000) - Math.floor(textLatitude / 1000000)) * 60) + "' "
                                    + (Math.floor(((((textLatitude / 1000000) - Math.floor(textLatitude / 1000000)) * 60)
                                    - Math.floor(((textLatitude / 1000000) - Math.floor(textLatitude / 1000000)) * 60)) * 100000) * 60 / 100000) + "''"));
                            *********************************************************************************************/

                            /*********************************************************************************************
                            textLongitude = Double.parseDouble(longitudeDecimal.getText().toString());
                            String signlon;

                            if (textLongitude < 0) {
                                signlon = "-";
                            } else {
                                signlon = "";
                            }

                            textLongitude = Math.abs(Math.round(resultatConversionLongitude * 1000000.));
                            if (textLongitude > (90 * 1000000)) {
                                Toast.makeText(getActivity().getApplicationContext(), " Les degrés de longitude doivent être compris entre -180 et +180°",
                                        Toast.LENGTH_SHORT).show();
                                resultatConversionLongitude = Double.parseDouble("");
                                textLongitude = 0;
                            }

                            resultatConversionLongitude = Double.parseDouble(signlon + ((Math.floor(textLongitude / 1000000)) + "° "
                                    + Math.floor(((textLongitude / 1000000) - Math.floor(textLongitude / 1000000)) * 60) + "' "
                                    + (Math.floor(((((textLongitude / 1000000) - Math.floor(textLongitude / 1000000)) * 60)
                                    - Math.floor(((textLongitude / 1000000) - Math.floor(textLongitude / 1000000)) * 60)) * 100000) * 60 / 100000) + "''"));

                            affichageConversionLatitude.setText(String.valueOf(resultatConversionLatitude));
                            affichageConversionLatitude.setText(String.valueOf(resultatConversionLongitude));
                            *********************************************************************************************/
                            //endregion

                            fab.show();
                        }

                        break;

                    case R.id.Reset:

                        //region ResetAllTextView
                        latitudeDecimal.getText().clear();
                        longitudeDecimal.getText().clear();

                        degresLongitude.getText().clear();
                        minutesLongitude.getText().clear();
                        secondesLongitude.getText().clear();

                        degresLatitude.getText().clear();
                        minutesLatitude.getText().clear();
                        secondesLatitude.getText().clear();

                        affichageConversionLatitude.setText("");
                        affichageConversionLongitude.setText("");
                        //endregion

                        fab.hide();
                        break;
                }
            }
        };

        buttonConversion.setOnClickListener(ClickButton);
        buttonReset.setOnClickListener(ClickButton);

        return conversion_view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}