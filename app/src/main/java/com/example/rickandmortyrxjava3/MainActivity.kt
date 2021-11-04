package com.example.rickandmortyrxjava3

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyrxjava3.adapters.CharacterAdapter
import com.example.rickandmortyrxjava3.di.App
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CharacterViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var checkListEmptyOrNot = false
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        recyclerView = findViewById(R.id.RecyclerViewItem)
        progressBar = findViewById(R.id.progressBar)
        button = findViewById(R.id.buttonInternet)
        (application as App).appComponent.inject(this)
        viewModelInit()
    }

    private fun viewModelInit() {
        viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]
        val adapter = CharacterAdapter()
        recyclerView.adapter = adapter
        viewModel.allCharacter()
            .observe(this, {
                adapter.submitList(it)
                if (adapter.currentList.isEmpty()) {
                    checkListEmptyOrNot = true
                }
                buttonRetryLoad()
                internetCheck()
            })
    }

    private fun internetCheck() {
        if (!isNetworkConnected(applicationContext) && checkListEmptyOrNot) {
            Snackbar.make(recyclerView.rootView, getString(R.string.not_internet_connection), Snackbar.LENGTH_LONG)
                .show()
            progressBar.visibility = View.VISIBLE
            button.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
            button.visibility = View.INVISIBLE
        }
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        capabilities.also {
            if (it != null) {
                if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                    return true
                else if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                }
            }
        }
        return false
    }

    private fun buttonRetryLoad() {
        button.setOnClickListener {
            internetCheck()
        }
    }
}