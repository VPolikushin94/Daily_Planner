package com.example.simbirsoft.core.root

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.simbirsoft.R
import com.example.simbirsoft.notes.ui.NotesFragment
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, NotesFragment())
                .commit()
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(setRussianLocal(newBase))
    }

    private fun setRussianLocal(context: Context?): Context? {
        return if (context != null) {
            val local = Locale("ru", "RU")
            Locale.setDefault(local)
            val config = context.resources.configuration
            config.setLocale(local)
            config.setLayoutDirection(local)
            context.createConfigurationContext(config)
        } else {
            null
        }
    }
}