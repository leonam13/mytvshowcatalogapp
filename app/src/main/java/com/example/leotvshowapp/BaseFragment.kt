package com.example.leotvshowapp

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.leotvshowapp.data.repository.Result
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {

    private var mainActivity: MainActivity? = null

    protected var swipeRefresh: SwipeRefreshLayout? = null

    private var snackBar: Snackbar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = (context as? MainActivity)
    }

    fun setLoading(isLoading: Boolean) {
        swipeRefresh?.isEnabled = isLoading
        swipeRefresh?.isRefreshing = isLoading
    }

    suspend fun <T> Result<T>.handleResourceWithSuspendAction(
        doOnSuccessAction: (suspend (data: T) -> Unit)? = null,
        doOnErrorAction: (() -> Unit)? = null
    ) {
        when (this) {
            Result.Loading -> return
            is Result.Error -> handleError(exception = exception, doOnErrorAction = doOnErrorAction)
            is Result.Success -> {
                snackBar?.dismiss()
                doOnSuccessAction?.invoke(data)
            }
        }
    }

    fun <T> Result<T>.handleResource(
        doOnSuccessAction: ((data: T) -> Unit)? = null,
        doOnErrorAction: (() -> Unit)? = null
    ) {
        when (this) {
            Result.Loading -> setLoading(true)
            is Result.Error -> {
                setLoading(false)
                handleError(exception = exception, doOnErrorAction = doOnErrorAction)
            }
            is Result.Success -> {
                setLoading(false)
                snackBar?.dismiss()
                doOnSuccessAction?.invoke(data)
            }
        }
    }

    fun handleError(exception: Throwable? = null, doOnErrorAction: (() -> Unit)?) {
        setLoading(false)
        exception?.printStackTrace()
        if (exception is NoMorePagesException) return
        snackBar = Snackbar.make(requireView(), getString(R.string.error_handler_default_message), Snackbar.LENGTH_INDEFINITE).apply {
            setAction(getString(R.string.error_handler_default_button)) { doOnErrorAction?.invoke() }
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        snackBar?.dismiss()
    }
}