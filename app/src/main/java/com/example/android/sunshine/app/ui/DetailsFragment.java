package com.example.android.sunshine.app.ui; /**
 * Created by Administrador on 08/04/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.sunshine.app.R;

public class DetailsFragment extends Fragment {

    private static final String TAG = DetailsFragment.class.getSimpleName();
    private String forecastInfo = "";
    private String FORECAST_SHARE_HASTAG =" #SunShineIB";
    private ShareActionProvider mSharedActionProvider;

    public DetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);

        Bundle b = getActivity().getIntent().getExtras();
        forecastInfo = b.getString("forecast");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail,menu);

        MenuItem menuItem = menu.findItem(R.id.action_share_social);

        mSharedActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        if(mSharedActionProvider != null){
            mSharedActionProvider.setShareIntent(createShareForecastIntent());
        }else{
            Log.i(TAG, "Share Action Provider is null?");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_settings){
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            Log.i(TAG, "onOptionsItemSelected: you have clicked on " + R.id.action_settings);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView txtForecastInfo = (TextView)rootView.findViewById(R.id.txt_forecast_info);
        txtForecastInfo.setText(forecastInfo);
        return rootView;
    }

    private Intent createShareForecastIntent(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, forecastInfo + FORECAST_SHARE_HASTAG);
        return shareIntent;
    }


}
