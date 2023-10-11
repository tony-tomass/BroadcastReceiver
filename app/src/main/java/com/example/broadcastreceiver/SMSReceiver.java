package com.example.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();

        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {

            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                String format = bundle.getString("format").toString();

                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage current_message = SmsMessage.createFromPdu((byte[]) pdusObj[i], format);
                    String sender = current_message.getDisplayOriginatingAddress();
                    String message = current_message.getMessageBody();

                    String printMessage = "Sender: " + sender + "\nMessage: " + message;
                    Log.i("SMS", printMessage);
                    Toast.makeText(context, printMessage, Toast.LENGTH_LONG).show();

                    Intent act_intent = new Intent(context, MainActivity.class);
                    act_intent.putExtra("sms", message);
                    act_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(act_intent);

                }
            }
        }
    }
}
