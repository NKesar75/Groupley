package domain.teamgroupley.groupleyapp;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Hector on 8/10/2017.
 */

public class myFirebaseInstanceIdIDService extends FirebaseInstanceIdService {
    private static final String Reg_Token = "Reg_Token";
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String Recenttokent = FirebaseInstanceId.getInstance().getToken();
        Log.d(Reg_Token, Recenttokent);
        sendRegistrationToServer(Recenttokent);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}

