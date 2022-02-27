package com.nyautech.gemingapps

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status

class MainActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    lateinit var signOutButton : Button
    lateinit var signInButton : SignInButton
    lateinit var statusTextView :TextView
    lateinit var mGoogleSignInClient : GoogleApiClient
    val TAG:String = "SignInActivity"
    val RC_SIGN_IN:Int = 9001

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)
        mGoogleSignInClient = GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API).build()
        statusTextView = findViewById(R.id.status_textview)

        signInButton = findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener(this)

        signOutButton = findViewById(R.id.signOutButton)
        signOutButton.setOnClickListener(this)

    }

    override fun onClick(v:View) {
        when(v.getId()){
            R.id.sign_in_button -> {
                signIn()
            }
            R.id.signOutButton -> {
                signOut()
            }
        }
    }
    fun signIn(){
        val signInIntent : Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    fun on_Activity_Result (requestCode:Int, resultCode:Int, data:Intent){
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            var result : GoogleSignInResult? = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
        }
    }

    @SuppressLint("SetTextI18n")
    fun handleSignInResult(result: GoogleSignInResult){
        Log.d(TAG, "HandleSignInResult: " + result.isSuccess)
//        if (result.isSuccess){
//            var acct:GoogleSignInResult = result.signInAccount()
//        }
        if(result.isSuccess){
            val acct : GoogleSignInAccount? = result.signInAccount
            if (acct != null) {
                statusTextView.setText("Selamat Datang, " + acct.displayName)
            }
        }
    }

//    private fun updateUI(account: GoogleSignInAccount?) {
//
//    }
    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d(TAG, "OnConnectFailed:" +connectionResult)
    }

    fun signOut(){

    }

}