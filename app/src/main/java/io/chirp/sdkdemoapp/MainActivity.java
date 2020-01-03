package io.chirp.sdkdemoapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.chirp.chirpsdk.ChirpSDK;
import io.chirp.chirpsdk.interfaces.ChirpEventListener;
import io.chirp.chirpsdk.models.ChirpSDKState;
import io.chirp.chirpsdk.models.ChirpError;


public class MainActivity extends AppCompatActivity {

    private ChirpSDK chirpSdk;
    private Context context;

    private static final int RESULT_REQUEST_RECORD_AUDIO = 1;

    TextView status;
    TextView lastChirp;
    Button startStopSdkBtn;

    Boolean startStopSdkBtnPressed = false;

    String CHIRP_APP_KEY = "F819ACbbcFf6374bBa9D8CeaB";
    String CHIRP_APP_SECRET = "86D7EEFa957A708DE150DC3C78d0852daa8aef64de1D8906C5";
    String CHIRP_APP_CONFIG = "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";

    String TAG = "ChirpSDKDemoApp";

    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parentLayout = findViewById(android.R.id.content);
        status = (TextView) findViewById(R.id.stateValue);
        lastChirp = (TextView) findViewById(R.id.lastChirp);
        startStopSdkBtn = (Button) findViewById(R.id.startStopSdkBtn);

        startStopSdkBtn.setAlpha(.4f);
        startStopSdkBtn.setClickable(false);

        context = this;

        chirpSdk = new ChirpSDK(this, CHIRP_APP_KEY, CHIRP_APP_SECRET);

        ChirpError setConfigError = chirpSdk.setConfig(CHIRP_APP_CONFIG);
        if (setConfigError.getCode() > 0) {
            Log.e(TAG, setConfigError.getMessage());
        } else {
            startStopSdkBtn.setAlpha(1f);
            startStopSdkBtn.setClickable(true);
        }
        chirpSdk.setListener(chirpEventListener);
    }


    ChirpEventListener chirpEventListener = new ChirpEventListener() {

        @Override
        public void onSending(byte[] data, int channel) {
            /**
             * onSending is called when a send event begins.
             * The data argument contains the payload being sent.
             */
            String hexData = "null";
            if (data != null) {
                hexData = bytesToHex(data);
            }
            Log.v(TAG, "ChirpSDKCallback: onSending: " + hexData + " on channel: " + channel);
            updateLastPayload(hexData);
        }

        @Override
        public void onSent(byte[] data, int channel) {
            /**
             * onSent is called when a send event has completed.
             * The data argument contains the payload that was sent.
             */
            String hexData = "null";
            if (data != null) {
                hexData = bytesToHex(data);
            }
            updateLastPayload(hexData);
            Log.v(TAG, "ChirpSDKCallback: onSent: " + hexData + " on channel: " + channel);
        }

        @Override
        public void onReceiving(int channel) {
            /**
             * onReceiving is called when a receive event begins.
             * No data has yet been received.
             */
            Log.v(TAG, "ChirpSDKCallback: onReceiving on channel: " + channel);
        }

        @Override
        public void onReceived(byte[] data, int channel) {
            /**
             * onReceived is called when a receive event has completed.
             * If the payload was decoded successfully, it is passed in data.
             * Otherwise, data is null.
             */
            String hexData = "null";
            if (data != null) {
                hexData = bytesToHex(data);
            }
            Log.v(TAG, "ChirpSDKCallback: onReceived: " + hexData + " on channel: " + channel);
            updateLastPayload(hexData);
        }

        @Override
        public void onStateChanged(int oldState, int newState) {
            /**
             * onStateChanged is called when the SDK changes state.
             */
            Log.v(TAG, "ChirpSDKCallback: onStateChanged " + oldState + " -> " + newState);
            if (newState == ChirpSDKState.CHIRP_SDK_STATE_NOT_CREATED.getCode()) {
                updateStatus("Bo creado");
            } else if (newState == ChirpSDKState.CHIRP_SDK_STATE_STOPPED.getCode()) {
                updateStatus("Detenido");
            } else if (newState == ChirpSDKState.CHIRP_SDK_STATE_RUNNING.getCode()) {
                updateStatus("Escuchando");
            } else if (newState == ChirpSDKState.CHIRP_SDK_STATE_SENDING.getCode()) {
                updateStatus("Enviando");
            } else if (newState == ChirpSDKState.CHIRP_SDK_STATE_RECEIVING.getCode()) {
                updateStatus("Recibiendo");
            } else {
                updateStatus(newState + "");
            }

        }

        @Override
        public void onSystemVolumeChanged(float oldVolume, float newVolume) {
            /**
             * onSystemVolumeChanged is called when the system volume is changed.
             */
            Snackbar snackbar = Snackbar.make(parentLayout, "System volume has been changed to: " + newVolume, Snackbar.LENGTH_LONG);
            snackbar.setAction("CLOSE", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            })
                    .setActionTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
                    .show();
            Log.v(TAG, "System volume has been changed, notify user to increase the volume when sending data");
        }

    };

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, RESULT_REQUEST_RECORD_AUDIO);
        }
        else {
            if (startStopSdkBtnPressed) startListening();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RESULT_REQUEST_RECORD_AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (startStopSdkBtnPressed) stopListening();
                }
                return;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        chirpSdk.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            chirpSdk.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        stopListening();
    }

    public void updateStatus(final String newStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                status.setText(newStatus);
            }
        });
    }
    public void updateLastPayload(final String newPayload) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lastChirp.setText(newPayload);
            }
        });
    }

    public void stopListening() {
        ChirpError error = chirpSdk.stop();
        if (error.getCode() > 0) {
            Log.e(TAG, error.getMessage());
            return;
        }

        startStopSdkBtn.setText("Iniciar Escucha");
    }

    public void startListening() {
        ChirpError error = chirpSdk.start();
        if (error.getCode() > 0) {
            Log.e(TAG, error.getMessage());
            return;
        }
        startStopSdkBtn.setText("Detener");
    }

    public void startStopSdk(View view) {
        startStopSdkBtnPressed = true;
        if (chirpSdk.getState() == ChirpSDKState.CHIRP_SDK_STATE_STOPPED) {
            startListening();
        } else {
            stopListening();
        }
    }

    private final static char[] hexArray = "0123456789abcdef".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
