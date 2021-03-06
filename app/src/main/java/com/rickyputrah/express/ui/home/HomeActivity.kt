package com.rickyputrah.express.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyputrah.express.R
import com.rickyputrah.express.databinding.BestLocationDialogBinding
import com.rickyputrah.express.databinding.ErrorDialogBinding
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
        setupObserver()

        //Start Rquest Location List
        viewModel.requestLocationList()
    }


    private fun setupObserver() {
        viewModel.state.observe(this, Observer {
            renderState(it)
        })
    }

    private fun renderState(state: State?) {
        when (state) {
            is State.SuccessGetLocationList -> setupData(state.data)
            is State.ErrorConnectionTimeout -> handleErrorState(resources.getString(R.string.text_connection_timeout_error))
            is State.UnknownError -> handleErrorState(resources.getString(R.string.text_unknown_error))
            is State.RequestForbidden -> handleErrorState(resources.getString(R.string.text_request_forbidden_error))
            is State.Loading -> binding.loadingWidget.showLoading(true)
        }
    }

    private fun handleErrorState(message: String) {
        binding.loadingWidget.showLoading(false)
        showErrorDialog(message)
    }

    private fun setupData(data: LocationListModel) {
        binding.loadingWidget.showLoading(false)
        binding.buttonRefresh.text = data.buttonText
        adapter.dataset = data.listOfLocation
        adapter.notifyDataSetChanged()
        binding.loadingWidget.showLoading(false)
    }

    private fun setupAdapter() {
        adapter = LocationListAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupListener() {
        binding.buttonShowBest.setOnClickListener {
            showBestLocationDialog()
        }
        binding.buttonRefresh.setOnClickListener {
            viewModel.requestLocationList()
        }
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

    private fun showErrorDialog(message: String) {
        val dialogActivity = Dialog(this)
        dialogActivity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = ErrorDialogBinding.inflate(layoutInflater, null, false)
        dialogActivity.setContentView(dialogBinding.root)
        dialogBinding.textErrorMessage.text = message
        dialogActivity.show()
    }
}