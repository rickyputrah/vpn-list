package com.rickyputrah.express.ui.home

import android.app.Dialog
import android.os.Bundle
import android.util.Base64
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rickyputrah.express.R
import com.rickyputrah.express.databinding.BestLocationDialogBinding
import com.rickyputrah.express.databinding.ErrorDialogBinding
import com.rickyputrah.express.databinding.HomeActivityBinding
import com.rickyputrah.express.di.getApplicationComponent
import com.rickyputrah.express.model.BestLocationItemModel
import com.rickyputrah.express.model.LocationListModel
import com.rickyputrah.express.ui.home.HomeViewModel.State
import com.rickyputrah.express.ui.home.adapter.LocationListAdapter
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding
    private lateinit var adapter: LocationListAdapter

    @Inject
    lateinit var viewModel: HomeViewModel

    private var bestLocation: BestLocationItemModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getApplicationComponent().inject(this)

        setupAdapter()
        setupListener()
        setupObserver()
        toggleButtonShowBest(false)

        //Start Request Location List
        viewModel.requestLocationList()
    }

    private fun setupObserver() {
        viewModel.state.observe(this, Observer {
            renderState(it)
        })
    }

    private fun renderState(state: State?) {
        when (state) {
            is State.SuccessGetLocationList -> {
                setupLocationListData(state.data)
                findBestLocation(state.data)
            }
            is State.SuccessBestLocation -> {
                bestLocation = state.data
                toggleButtonShowBest(true)
            }
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

    private fun setupLocationListData(data: LocationListModel) {
        binding.buttonRefresh.text = data.buttonText
        adapter.dataset = data.listOfLocation
        adapter.notifyDataSetChanged()
        binding.loadingWidget.showLoading(false)
    }

    private fun findBestLocation(data: LocationListModel) {
        bestLocation = null
        toggleButtonShowBest(false)
        viewModel.startToSearchBestLocation(data.listOfLocation)
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
        bestLocation?.let { location ->
            val dialogBinding = BestLocationDialogBinding.inflate(layoutInflater, null, false)
            dialogBinding.textIpAddress.text = location.ip
            dialogBinding.textLocationName.text = location.name
            Glide.with(this).load(Base64.decode(location.image, Base64.DEFAULT))
                .error(R.color.red_primary)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(dialogBinding.imageCountryFlag)

            Dialog(this).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(dialogBinding.root)
            }.show()
        }
    }

    private fun showErrorDialog(message: String) {
        val dialogBinding = ErrorDialogBinding.inflate(layoutInflater, null, false)
        dialogBinding.textErrorMessage.text = message
        Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(dialogBinding.root)
        }.show()
    }

    private fun toggleButtonShowBest(isEnabled: Boolean) {
        binding.buttonShowBest.isClickable = isEnabled
        binding.buttonShowBest.isEnabled = isEnabled
    }
}