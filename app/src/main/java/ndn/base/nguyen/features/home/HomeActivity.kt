package ndn.base.nguyen.features.home

import ndn.base.nguyen.R
import ndn.base.nguyen.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity<HomeViewModel>() {

    override val layoutID: Int
        get() = R.layout.activity_home

    override val viewModelx: HomeViewModel by viewModel()

    override fun initialize() {
    }

    override fun onSubscribeObserver() {

    }

}
