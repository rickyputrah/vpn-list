package com.rickyputrah.express.util

import android.view.View


/**
 * Simplify boolean checking for visibility
 * The default value for true is VISIBLE and false is GONE
 * the default true and false visibility could be replaced by replacing the default value
 */
fun Boolean.toVisibility(
    trueVisibility: Int = View.VISIBLE,
    falseVisibility: Int = View.GONE
): Int = if (this) trueVisibility else falseVisibility