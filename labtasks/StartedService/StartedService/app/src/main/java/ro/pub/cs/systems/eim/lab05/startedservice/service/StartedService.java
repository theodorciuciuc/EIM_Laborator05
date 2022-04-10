package ro.pub.cs.systems.eim.lab05.startedservice.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import ro.pub.cs.systems.eim.lab05.startedservice.general.Constants;

public class StartedService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.TAG, "onCreate() method was invoked");
        Log.d("ceva", "altceva1");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Constants.TAG, "onBind() method was invoked");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(Constants.TAG, "onUnbind() method was invoked");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(Constants.TAG, "onRebind() method was invoked");
    }

    @Override
    public void onDestroy() {
        Log.d(Constants.TAG, "onDestroy() method was invoked");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Constants.TAG, "onStartCommand() method was invoked");
        // TODO: exercise 5 - implement and start the ProcessingThread
        ProcessingThread thread = new ProcessingThread(this);
        thread.start();
        return START_REDELIVER_INTENT;
    }

    private class ProcessingThread extends Thread {

        private Context context;

        public ProcessingThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            while(true){
                sendMessage(Constants.MESSAGE_STRING);
                sleep();
                sendMessage(Constants.MESSAGE_INTEGER);
                sleep();
                sendMessage(Constants.MESSAGE_ARRAY_LIST);
                sleep();
            }
        }

        private void sleep() {
            try {
                Thread.sleep(Constants.SLEEP_TIME);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

        private void sendMessage(int messageType) {
            Intent intent = new Intent();
            switch(messageType) {
                case Constants.MESSAGE_STRING:
                    intent.setAction(Constants.ACTION_STRING);
                    intent.putExtra(Constants.DATA, Constants.STRING_DATA);
                    break;
                case Constants.MESSAGE_INTEGER:
                    intent.setAction(Constants.ACTION_INTEGER);
                    intent.putExtra(Constants.DATA,Constants.INTEGER_DATA);
                    break;
                case Constants.MESSAGE_ARRAY_LIST:
                    intent.setAction(Constants.ACTION_ARRAY_LIST);
                    intent.putExtra(Constants.DATA, Constants.ARRAY_LIST_DATA);
                    break;
            }
            context.sendBroadcast(intent);
        }
    }

}
