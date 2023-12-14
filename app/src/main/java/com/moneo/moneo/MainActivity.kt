package com.moneo.moneo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.moneo.moneo.databinding.ActivityMainBinding
import com.moneo.moneo.ui.rekening.RekeningFragment
import com.moneo.moneo.ui.transaction.TransactionFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        switchFragment(TransactionFragment())

        binding.bottomNavigation.inflateMenu(R.menu.menu_bot_nav)
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_transaction -> {
                    switchFragment(TransactionFragment())
                    true
                }
                R.id.action_account -> {
                    switchFragment(RekeningFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }

}