package com.aau.kupper.einzelbeispiel;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class AsyncSendTcpTask extends AsyncTask<Void, Void, String> {
    public interface Callback {
        void onResult(String result);
    }

    private String domain;
    private int port;
    private String input;
    private Callback callback;

    public AsyncSendTcpTask(String _domain, int _port, String _input, Callback _callback) {
        domain = _domain;
        port = _port;
        input = _input;
        callback = _callback;
    }

    @Override
    protected String doInBackground(Void... params) {
        String result = "";
        try {
            Socket socket = new Socket(domain, port);
            OutputStream out = socket.getOutputStream();
            out.write((input + "\n").getBytes());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            result = in.readLine();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        callback.onResult(result);
    }
}