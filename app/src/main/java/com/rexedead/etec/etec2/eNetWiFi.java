package com.rexedead.etec.etec2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;


public class eNetWiFi extends AppCompatActivity {

    private static final String TAG = "ExceptionWifi";
    private static String ssid;
    private static String password;

    public static void setSsid(String ssid) {
        eNetWiFi.ssid = ssid;
    }

    public static void setPassword(String password) {
        eNetWiFi.password = password;
    }

    boolean roznWiFiTurnOn(final Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        wifiManager.startScan();
        Toast.makeText(context, "Подождите 10 секунд", Toast.LENGTH_LONG).show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
               wifiAtLaunch(context);
            }
        }, 6500);

        return wifiAtLaunch(context);
    }


    private boolean wifiAtLaunch(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration wifiConfig1 = new WifiConfiguration();
        wifiConfig1.status = WifiConfiguration.Status.ENABLED;
        wifiConfig1.SSID = String.format("\"%s\"", ssid);
        wifiConfig1.preSharedKey = String.format("\"%s\"", password);

        WifiInfo wifiinfo = wifiManager.getConnectionInfo();
        if (isConnected(context) && (wifiinfo.getSSID().contains(ssid))) {
            Toast.makeText(context, "Подключено к рознице", Toast.LENGTH_SHORT).show();//Back to MainActivity
        return true;

        } else {
            Toast.makeText(context, "Сканируем сеть", Toast.LENGTH_SHORT).show();
            try {
                List<ScanResult> list = wifiManager.getScanResults();
                for (ScanResult i : list) {
                    if (i.SSID != null && (i.SSID.equals(ssid))) {
                        Toast.makeText(context, "Подключаемся к рознице", Toast.LENGTH_SHORT).show();
                        if (i.SSID.equals(ssid)) {
                            int netId1 = wifiManager.addNetwork(wifiConfig1);
                            wifiManager.disconnect();
                            wifiManager.enableNetwork(netId1, true);
                            wifiManager.reconnect();
                            Toast.makeText(context, "Готово! Страница будет перезагружена", Toast.LENGTH_SHORT).show();
                            int countdown = 1;
                            while (countdown < 10) {
                                if (isConnected(context)) {
                                    return true;
                                }
                                ++countdown;
                                Thread.sleep(1000);
                            }
                        } else
                            return false;
                            break;

                    }
                }


            } catch (NullPointerException e) {
                Log.e(TAG, "WiFi exception", e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            Toast.makeText(context, "Текущее соединение = "+ wifiinfo.getSSID(), Toast.LENGTH_SHORT).show();

        }
        return false;
    }


    void rozncheck(Context context) {

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(eNetWiFi.this);
//        String ssid = prefs.getString("ssid", "DEFAULT");
//        String pw = prefs.getString("ssid", "DEFAULT");

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiinfo = wifiManager.getConnectionInfo();

        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        if (isConnected(context) && (wifiinfo.getSSID().contains(ssid))) {
            Toast.makeText(context, "ok!", Toast.LENGTH_SHORT).show();
        } else {
            wifiAtLaunch(context);
        }
    }


    private static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED;
    }

}

