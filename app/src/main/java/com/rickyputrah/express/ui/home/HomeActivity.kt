package com.rickyputrah.express.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyputrah.express.databinding.BestLocationDialogBinding
import com.rickyputrah.express.databinding.HomeActivityBinding
import com.rickyputrah.express.model.LocationItemModel
import com.rickyputrah.express.ui.home.adapter.LocationListAdapter

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding
    private lateinit var adapter: LocationListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        setupListener()

        //TODO Request Data
        mockData()
    }

    private fun setupListener() {
        binding.buttonShowBest.setOnClickListener {
            showBestLocationDialog()
        }
    }

    private fun mockData() {
        val list = listOf(
            LocationItemModel(
                "Los Angeles",
                listOf("123", "234"),
                "iVBORw0KGgoAAAANSUhEUgAAABAAAAALCAIAAAD5gJpuAAAABGdBTUEAAK/I NwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAFB SURBVHjaYnz58iUDEvgHBnAGEPz58wfIBZIQLkAAMbK7v5yUJwYU+vsfCBj+ /gMqBZL///79D2T/+fv/D5D9B8j+/+fP/+70GwABxAi0QUxMDKQWDCCM/9gA IyPjtWvXAAKIBa4IWXXfkv///v8vjELRAHEnQACxQFwMVf2fYf7W/zce/+dn /S/B9D9v4n81mf+ZAQgNQJ8ABBATXDVY7H+A9f8/v//nhP338/v/+/f/GGcU K4CmAwQQExDDVQPByv3/f//5v2HD/96l/3///T93G5L6/0B//wEIIBa4Bog9 KX4gduec/1ys/2cUg8IKWRaoGCCAoH5AC5zSJHjIIDRAPA0QQCzyLv9aGoGB zQAMbCAJjKU/fxn+/mUEkiDBv6CYAXKB8fDvP8OKmn8AAcR4+/Zt5IjEZCAD oEqAAAMAKQh5Em/pfi4AAAAASUVORK5CYII="
            ),
            LocationItemModel(
                "Los Angeles",
                listOf("123"),
                "iVBORw0KGgoAAAANSUhEUgAAABAAAAALCAIAAAD5gJpuAAAABGdBTUEAAK/I NwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAFB SURBVHjaYnz58iUDEvgHBnAGEPz58wfIBZIQLkAAMbK7v5yUJwYU+vsfCBj+ /gMqBZL///79D2T/+fv/D5D9B8j+/+fP/+70GwABxAi0QUxMDKQWDCCM/9gA IyPjtWvXAAKIBa4IWXXfkv///v8vjELRAHEnQACxQFwMVf2fYf7W/zce/+dn /S/B9D9v4n81mf+ZAQgNQJ8ABBATXDVY7H+A9f8/v//nhP338/v/+/f/GGcU K4CmAwQQExDDVQPByv3/f//5v2HD/96l/3///T93G5L6/0B//wEIIBa4Bog9 KX4gduec/1ys/2cUg8IKWRaoGCCAoH5AC5zSJHjIIDRAPA0QQCzyLv9aGoGB zQAMbCAJjKU/fxn+/mUEkiDBv6CYAXKB8fDvP8OKmn8AAcR4+/Zt5IjEZCAD oEqAAAMAKQh5Em/pfi4AAAAASUVORK5CYII="
            ),
            LocationItemModel(
                "Los Angeles",
                listOf("123", "234"),
                "iVBORw0KGgoAAAANSUhEUgAAABAAAAALCAIAAAD5gJpuAAAABGdBTUEAAK/I NwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAFB SURBVHjaYnz58iUDEvgHBnAGEPz58wfIBZIQLkAAMbK7v5yUJwYU+vsfCBj+ /gMqBZL///79D2T/+fv/D5D9B8j+/+fP/+70GwABxAi0QUxMDKQWDCCM/9gA IyPjtWvXAAKIBa4IWXXfkv///v8vjELRAHEnQACxQFwMVf2fYf7W/zce/+dn /S/B9D9v4n81mf+ZAQgNQJ8ABBATXDVY7H+A9f8/v//nhP338/v/+/f/GGcU K4CmAwQQExDDVQPByv3/f//5v2HD/96l/3///T93G5L6/0B//wEIIBa4Bog9 KX4gduec/1ys/2cUg8IKWRaoGCCAoH5AC5zSJHjIIDRAPA0QQCzyLv9aGoGB zQAMbCAJjKU/fxn+/mUEkiDBv6CYAXKB8fDvP8OKmn8AAcR4+/Zt5IjEZCAD oEqAAAMAKQh5Em/pfi4AAAAASUVORK5CYII="
            )
        )
        adapter.dataset = list
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