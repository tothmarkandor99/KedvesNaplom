package com.webtic.kedvesnaplom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    // TODO: ezt majd törölni kell, csak azért van itt hogy leforduljon az app Jetpack Compose nézetek nélkül is

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}