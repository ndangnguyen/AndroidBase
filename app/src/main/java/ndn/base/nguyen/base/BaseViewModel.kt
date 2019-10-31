package ndn.base.nguyen.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ndn.base.nguyen.utils.liveData.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val isLoading = SingleLiveEvent<Boolean>()
    val onError = SingleLiveEvent<Throwable>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun launchRx(job: () -> Disposable) {
        compositeDisposable.add(job())
    }
}