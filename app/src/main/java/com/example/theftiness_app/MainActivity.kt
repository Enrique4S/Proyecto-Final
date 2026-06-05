package com.example.theftiness_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.theftiness_app.ui.ProgressActivity
import com.example.theftiness_app.ui.VoiceTrainingActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars =
                insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                )

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        val btnRegistrar =
            findViewById<Button>(
                R.id.btnRegistrar
            )

        val btnProgreso =
            findViewById<Button>(
                R.id.btnProgreso
            )

        btnRegistrar.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    VoiceTrainingActivity::class.java
                )
            )
        }

        btnProgreso.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ProgressActivity::class.java
                )
            )
        }
    }
}