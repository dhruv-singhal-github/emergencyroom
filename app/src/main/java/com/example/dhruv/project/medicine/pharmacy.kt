package com.example.dhruv.project.medicine

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.dhruv.project.R
import kotlinx.android.synthetic.main.activity_pharmacy.*
import android.content.Intent
import android.net.Uri


class pharmacy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy)

        mg.setOnClickListener {
            val url="https://www.1mg.com/"
            val uriUrl = Uri.parse(url)
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
            startActivity(launchBrowser)

        }
        netmeds.setOnClickListener {
            val url="https://www.netmeds.com/"
            val uriUrl = Uri.parse(url)
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
            startActivity(launchBrowser)

        }
        pharmeasy.setOnClickListener {
            val url="https://pharmeasy.in/"
            val uriUrl = Uri.parse(url)
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
            startActivity(launchBrowser)

        }
        aermed.setOnClickListener {
            val url="https://www.aermed.in/"
            val uriUrl = Uri.parse(url)
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
            startActivity(launchBrowser)

        }
        medlife.setOnClickListener {
            val url="https://www.medlife.com/"
            val uriUrl = Uri.parse(url)
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
            startActivity(launchBrowser)

        }
        practo.setOnClickListener {
            val url="https://www.practo.com/"
            val uriUrl = Uri.parse(url)
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
            startActivity(launchBrowser)

        }
    }
}
