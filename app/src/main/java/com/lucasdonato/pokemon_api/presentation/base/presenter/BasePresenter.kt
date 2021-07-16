package com.lucasdonato.pokemon_api.presentation.base.presenter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.CallSuper
import com.lucasdonato.pokemon_api.controller.executor.ExecutorCoroutineScope
import com.lucasdonato.pokemon_api.controller.executor.getCoroutineScope

abstract class BasePresenter : ExecutorCoroutineScope by getCoroutineScope() {

    @CallSuper
    fun onCreate(savedInstanceState: Bundle?) {
    }

    @CallSuper
    fun onStart() {
    }

    @CallSuper
    fun onResume() {
    }

    @CallSuper
    fun onPause() {
    }

    @CallSuper
    fun onStop() {
    }

    @CallSuper
    fun onSaveInstanceState(outState: Bundle) {
    }

    @CallSuper
    fun onDestroy() {
    }

    @CallSuper
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    companion object {

        @JvmStatic
        fun nullPresenter(context: Context): BasePresenter {
            return object : BasePresenter() {

            }
        }
    }
}