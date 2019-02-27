package com.angler.broadcastexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.angler.broadcastexample.helper.NetworkManager;
import com.angler.broadcastexample.helper.TIHelper;
import com.angler.broadcastexample.receiver.NetworkChangeReceiver;

public class MainActivity extends AppCompatActivity {

   private BroadcastReceiver mNetworkReceiver;
   public static TextView tv_check_connection;
   NetworkManager mNetworkManager;
   static Context myContext;

   @Override
   protected void onCreate( Bundle savedInstanceState ) {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_main );

      myContext = this;
      tv_check_connection = ( TextView ) findViewById( R.id.tv_check_connection );
      mNetworkReceiver = new NetworkChangeReceiver();

      TIHelper.checkInternet( myContext );

      registerNetworkBroadcastForNougat();
   }


   private void registerNetworkBroadcastForNougat() {
      if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
         //registerReceiver( mNetworkReceiver, new IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION ) );
         registerReceiver( mNetworkReceiver, new IntentFilter( "android.net.conn.CONNECTIVITY_CHANGE" ) );

      } else if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
         registerReceiver( mNetworkReceiver, new IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION ) );
      }


   }


   protected void unregisterNetworkChanges() {
      try {
         unregisterReceiver( mNetworkReceiver );
      } catch( IllegalArgumentException e ) {
         e.printStackTrace();
      }
   }


   public static void dialog( boolean value ) {

      if( value ) {
         tv_check_connection.setText( "We are back !!!" );
         tv_check_connection.setBackgroundColor( Color.GREEN );
         tv_check_connection.setTextColor( Color.WHITE );

         Handler handler = new Handler();
         Runnable delayrunnable = new Runnable() {
            @Override
            public void run() {
               tv_check_connection.setVisibility( View.GONE );
            }
         };
         handler.postDelayed( delayrunnable, 3000 );

      } else {
         tv_check_connection.setVisibility( View.VISIBLE );
         tv_check_connection.setText( "Could not Connect to internet" );
         tv_check_connection.setBackgroundColor( Color.RED );
         tv_check_connection.setTextColor( Color.WHITE );
      }
   }


   @Override
   public void onDestroy() {
      super.onDestroy();
     // unregisterNetworkChanges();
   }
}
