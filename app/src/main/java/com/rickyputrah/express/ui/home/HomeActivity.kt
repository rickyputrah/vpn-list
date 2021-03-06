package com.rickyputrah.express.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyputrah.express.databinding.BestLocationDialogBinding
import com.rickyputrah.express.databinding.HomeActivityBinding
import com.rickyputrah.express.di.getApplicationComponent
import com.rickyputrah.express.model.LocationListModel
import com.rickyputrah.express.ui.home.HomeViewModel.State
import com.rickyputrah.express.ui.home.adapter.LocationListAdapter
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding
    private lateinit var adapter: LocationListAdapter

    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getApplicationComponent().inject(this)

        setupAdapter()
        setupListener()

        //TODO Request Data
        viewModel.requestLocationList()
    }

    private fun setupListener() {
        binding.buttonShowBest.setOnClickListener {
            showBestLocationDialog()
        }
        binding.buttonRefresh.setOnClickListener {
            viewModel.requestLocationList()
        }
        viewModel.state.observe(this, Observer {
            renderState(it)
        })
    }

    private fun renderState(state: State?) {
        when (state) {
            is State.SuccessGetLocationList -> setupData(state.data)
            is State.ErrorConnectionTimeout -> println("")
            is State.UnknownError -> println("")
            is State.RequestForbidden -> println("")
        }
    }

    private fun setupData(data: LocationListModel) {
        binding.buttonRefresh.text = data.buttonText
        adapter.dataset = data.listOfLocation
        adapter.notifyDataSetChanged()
    }

    private fun setupAdapter() {
        adapter = LocationListAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun showBestLocationDialog() {
        val dialogActivity = Dialog(this)
        dialogActivity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = BestLocationDialogBinding.inflate(layoutInflater, null, false)
        dialogActivity.setContentView(dialogBinding.root)

        //TODO Setup View Dialog With Correct Data
        dialogBinding.textIpAddress.text = "Mock IP address"
        dialogBinding.textLocationName.text = "Location Name"

        dialogActivity.show()
    }
}