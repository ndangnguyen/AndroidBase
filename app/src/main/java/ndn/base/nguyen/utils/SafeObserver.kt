package ndn.base.nguyen.utils

import androidx.lifecycle.Observer

/**
 * --------------------
 * Created by ThuanPx on 7/9/2019.
 * Screen name:
 * --------------------
 */
class SafeObserver<T>(private val notifier: (T) -> Unit) : Observer<T> {
    override fun onChanged(t: T?) {
        t?.let { notifier(t) }
    }
}