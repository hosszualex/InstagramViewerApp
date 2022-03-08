package com.example.instagramviewerapp.ui.activities


import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.instagramviewerapp.Constants
import com.example.instagramviewerapp.R
import com.example.instagramviewerapp.databinding.ActivityMainBinding
import com.example.instagramviewerapp.ui.fragments.PostListFragment

class MainActivity : FragmentActivity(){
    val networks = mutableListOf<Network>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        addFragmentOnTop(PostListFragment(), Constants.POST_LIST_SCREEN_TAG)

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    override fun onResume() {
        super.onResume()
        shouldDisplayNoNetworkError(networks.isEmpty())
    }
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            networks.add(network)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            networks.remove(network)
            shouldDisplayNoNetworkError(networks.isEmpty())
        }
    }

    override fun onBackPressed() {
        val lastFragment = this.lastFragment()
        if (lastFragment is PostListFragment) {
            finish()
        }else {
            super.onBackPressed()
        }
    }

    private fun shouldDisplayNoNetworkError(shouldDisplay: Boolean) {
        if (shouldDisplay) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.no_internet_connection_title))
            builder.setMessage(getString(R.string.no_internet_connection_message))
            builder.setPositiveButton(android.R.string.ok) { dialog: DialogInterface?, which: Int ->
                dialog?.cancel()
            }
            builder.show()
        }
    }
}