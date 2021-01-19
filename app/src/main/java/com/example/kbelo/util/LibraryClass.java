package com.example.kbelo.util;

import android.content.Context;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class LibraryClass {
    private static Firebase firebase;
    private static FirebaseAuth mAuth;

    private LibraryClass(){}

    public static FirebaseAuth getMAuth(){
        if(mAuth==null){
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }

    public static Firebase getFirebase(){
        if(firebase == null ){
            firebase = new Firebase( "https://kbelo-35be1.firebaseio.com/" );
        }
        return(firebase);
    }
}
