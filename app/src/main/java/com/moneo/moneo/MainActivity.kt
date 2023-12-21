package com.moneo.moneo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.moneo.moneo.databinding.ActivityMainBinding
import com.moneo.moneo.ui.rekap.RekapFragment
import com.moneo.moneo.ui.rekening.RekeningFragment
import com.moneo.moneo.ui.setting.SettingFragment
import com.moneo.moneo.ui.transaction.TransactionFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var selectedFragment: Fragment

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
                R.id.action_report -> {
                    switchFragment(RekapFragment())
                    true
                }
                R.id.action_setting -> {
                    switchFragment(SettingFragment())
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState != null) {
            selectedFragment = supportFragmentManager.getFragment(savedInstanceState, "selectedFragment") ?: TransactionFragment()
        } else {
            selectedFragment = TransactionFragment()
        }

        switchFragment(selectedFragment)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (selectedFragment.isAdded) {
            supportFragmentManager.putFragment(outState, "selectedFragment", selectedFragment)
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }

}