package com.example.avishkar2021.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import com.example.avishkar2021.R;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InternetConnection extends AsyncTask<Void, Void, Boolean> {


    Context context;
    public InternetConnection(Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return isInternetAvailable() && isNetworkAvailable(context);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        // do something with result
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("No Internet !!!");
        alertDialog.setIcon(R.drawable.internet_connection);
        alertDialog.setMessage("Switch on your internet or wifi...");
        if (result) {
            alertDialog.cancel();
        } else {
            alertDialog.show();
        }
    }
}
