package com.example.chatroomfire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.chatroomfire.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
//    private const val TAG = "MainActivity"
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var fireAuth : FirebaseAuth

    @Inject
    lateinit var fireDb : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        navController = NavController(this)
        Log.d("TAG", "onCreate: OnCreated Called")

        val navy = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navi = navy?.findNavController()

        NavigationUI.setupWithNavController(mainBinding.bottomNav,navi!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_top,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout){
            fireAuth.signOut()
            finish()
            return true
        }
        return true
    }

}