package com.binjar.sample.app.core

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity : AppCompatActivity(), FragmentHost {


    fun navigateTo(clazz: Class<out Activity>, bundle: Bundle? = null) {
        val intent = Intent(this, clazz)
        bundle?.let {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun hideSoftKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, 0)
    }

    override fun showSnackMessage(parentLayout: CoordinatorLayout, message: String) {
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show()
    }

    override fun showToastMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}
