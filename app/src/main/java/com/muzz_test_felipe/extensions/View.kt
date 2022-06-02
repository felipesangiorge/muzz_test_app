package com.muzz_test_felipe.extensions

import android.view.View

var View.toVisibility: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }