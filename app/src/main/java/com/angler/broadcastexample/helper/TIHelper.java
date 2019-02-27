package com.angler.broadcastexample.helper;

import android.content.Context;

public class TIHelper {

   public static boolean checkInternet( Context aContext ) {
      return NetworkManager.isInternetOnCheck( aContext );
   }
}
