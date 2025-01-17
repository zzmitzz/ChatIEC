package com.example.iec.ui.feature.main.tools

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.iec.R
import com.example.iec.databinding.ActivityToolsHostBinding
import com.example.iec.ui.feature.main.tools.components.DocsViewFragment


enum class ToolsFragmentScreen(
    name: String
){
    SCANNER("scanner"),
    DOCUMENT("document"),
    NOTE("note")
}
class HostActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityToolsHostBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d("HostActivity", "onCreate entered")
    }

    override fun onStart() {
        super.onStart()
        val screen = intent.getStringExtra(SCREEN_CHOOSE) ?: ToolsFragmentScreen.SCANNER.name
        showToolsFragmentScreen(ToolsFragmentScreen.valueOf(screen))
    }
    private fun showToolsFragmentScreen(screenChosen: ToolsFragmentScreen ) {
        when (screenChosen) {
            ToolsFragmentScreen.SCANNER -> {
                replaceFragment(DocsViewFragment.newInstance())
            }

            ToolsFragmentScreen.DOCUMENT -> {
                replaceFragment(DocsViewFragment.newInstance())
            }

            ToolsFragmentScreen.NOTE -> {
                replaceFragment(DocsViewFragment.newInstance())
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.fragment_host, fragment)
        fragmentTransaction.commit()
    }

    companion object{
        const val SCREEN_CHOOSE = "ScreenChoose"
    }
}