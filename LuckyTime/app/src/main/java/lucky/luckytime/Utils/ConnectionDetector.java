package lucky.luckytime.Utils;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {

    Context mContext;

    public ConnectionDetector(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isConnected()
    {
        ConnectivityManager connectivity =(ConnectivityManager)
                mContext.getSystemService(Service.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.isConnectedOrConnecting())
        {
            NetworkInfo wifi= connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile= connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if(mobile !=null && mobile.isConnectedOrConnecting() || (wifi !=null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        }else return false;
    }
}
