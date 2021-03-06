package org.ligi.android.dubwise_mk.balance;

import android.os.Bundle;

import org.ligi.android.dubwise_mk.BaseActivity;
import org.ligi.android.dubwise_mk.app.App;
import org.ligi.ufo.MKCommunicator;

public class BalanceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getMK().user_intent = MKCommunicator.USER_INTENT_3DDATA;
        setContentView(new BalanceView(this));
    }

}
