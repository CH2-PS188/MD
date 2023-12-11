package com.moneo.moneo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.moneo.moneo.databinding.ActivityMainBinding
import com.moneo.moneo.ui.rekening.AccountFragment
import com.moneo.moneo.ui.transaction.TransactionFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val transactionFragment = TransactionFragment()
        val accountFragment = AccountFragment()
        val fragment = fragmentManager.findFragmentByTag(TransactionFragment::class.java.simpleName)

        if (fragment !is TransactionFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + TransactionFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.nav_host_fragment, transactionFragment, TransactionFragment::class.java.simpleName)
                .commit()
        }
    }
}