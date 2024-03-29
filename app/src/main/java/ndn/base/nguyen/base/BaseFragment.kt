package ndn.base.nguyen.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ndn.base.nguyen.utils.widget.dialogManager.DialogAlert
import ndn.base.nguyen.utils.widget.dialogManager.DialogConfirm

abstract class BaseFragment<VM : BaseViewModel> : Fragment(), BaseView {

    protected abstract val layoutID: Int
    val compositeDisposable = CompositeDisposable()

    protected abstract val viewModelx: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutID, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        onSubscribeObserver()
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    override fun showLoading(isShow: Boolean) {
        if (isShow) showLoading() else hideLoading()
    }

    override fun showLoading() {
        if (activity is BaseActivity<*>) (activity as BaseActivity<*>).showLoading()
    }

    override fun hideLoading() {
        if (activity is BaseActivity<*>) (activity as BaseActivity<*>).hideLoading()
    }

    override fun handleApiError(apiError: Throwable) {
        if (activity is BaseActivity<*>) (activity as BaseActivity<*>).handleApiError(
                apiError)
    }

    override fun showAlertDialog(title: String, message: String,
            titleButton: String, listener: DialogAlert.Companion.OnButtonClickedListener?) {
        if (activity is BaseActivity<*>) (activity as BaseActivity<*>).showAlertDialog(title,
                message, titleButton, listener)
    }

    override fun showAlertDialog(title: String, message: String,
            titleButton: String, buttonBgColor: Int, buttonColor: Int,
            listener: DialogAlert.Companion.OnButtonClickedListener?) {
        if (activity is BaseActivity<*>) (activity as BaseActivity<*>).showAlertDialog(title,
                message, titleButton, buttonBgColor, buttonColor, listener)
    }

    override fun showConfirmDialog(title: String?, message: String?,
            titleButtonPositive: String, titleButtonNegative: String,
            listener: DialogConfirm.OnButtonClickedListener?) {
        if (activity is BaseActivity<*>) (activity as BaseActivity<*>).showConfirmDialog(
                title,
                message, titleButtonPositive, titleButtonNegative, listener)
    }

    override fun launchRx(job: () -> Disposable) {
        compositeDisposable.add(job())
    }

    protected abstract fun initialize()

    protected abstract fun onSubscribeObserver()
}