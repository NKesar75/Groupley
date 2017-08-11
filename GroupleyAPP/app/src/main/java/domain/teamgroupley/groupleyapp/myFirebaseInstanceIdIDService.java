package domain.teamgroupley.groupleyapp;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Hector on 8/10/2017.
 */

public class myFirebaseInstanceIdIDService extends FirebaseInstanceIdService{
   private static final String Reg_Token ="Reg_Token";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String Recenttokent = FirebaseInstanceId.getInstance().getToken();
        Log.d(Reg_Token,Recenttokent);
    }
}
