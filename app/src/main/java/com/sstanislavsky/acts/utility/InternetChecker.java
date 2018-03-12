package com.sstanislavsky.acts.utility;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by stanislav on 2/10/18.
 */

public class InternetChecker extends AsyncTask<Void, Void, Boolean> {

    private IsConnectedListener isConnectedListener;

    public interface IsConnectedListener {
        void isConnected(Boolean internet);
    }

    public InternetChecker(IsConnectedListener isConnectedListener) {
        this.isConnectedListener = isConnectedListener;
        execute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Socket sock = new Socket();
            sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
            sock.close();
            return true;
        } catch (IOException e) { return false; }
    }

    @Override
    protected void onPostExecute(Boolean internet) {
        isConnectedListener.isConnected(internet);
    }
}
