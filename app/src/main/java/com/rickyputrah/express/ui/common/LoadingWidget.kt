package com.rickyputrah.express.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.rickyputrah.express.R
import com.rickyputrah.express.databinding.LoadingWidgetBinding
import com.rickyputrah.express.util.toVisibility

class LoadingWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var binding: LoadingWidgetBinding

    init {
        if (isInEditMode) {
            LayoutInflater.from(context).inflate(R.layout.loading_widget, this, true)
        } else {
            binding = LoadingWidgetBinding.inflate(LayoutInflater.from(context))
            binding.root.visibility = View.GONE
            addView(binding.root)
        }
    }

    fun showLoading(show: Boolean) {
        binding.root.visibility = show.toVisibility()
    }
}