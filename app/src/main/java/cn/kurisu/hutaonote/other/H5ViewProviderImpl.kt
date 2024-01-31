package cn.kurisu.hutaonote.other

import android.content.Context
import android.view.ViewGroup
import com.alipay.mobile.nebula.provider.H5ViewProvider
import com.alipay.mobile.nebula.view.H5NavMenuView
import com.alipay.mobile.nebula.view.H5PullHeaderView
import com.alipay.mobile.nebula.view.H5TitleView
import com.alipay.mobile.nebula.view.H5WebContentView

/**
 * 自定义h5容器
 *
 * @author : kurisu
 * @date : 2024-01-31 15:00
 * @version : 1.0
 *
 */
class H5ViewProviderImpl: H5ViewProvider {
    override fun createTitleView(context: Context?): H5TitleView {
        val titleBar = NewTitleBar(context)
        titleBar.showBackButton(false)
        titleBar.showCloseButton(false)
        titleBar.setOptionMenu(false, false, false, null)
        return titleBar
    }

    override fun createNavMenu(): H5NavMenuView {
        TODO("Not yet implemented")
    }

    override fun createPullHeaderView(context: Context?, vg: ViewGroup?): H5PullHeaderView {
        TODO("Not yet implemented")
    }

    override fun createWebContentView(context: Context?): H5WebContentView {
        TODO("Not yet implemented")
    }
}