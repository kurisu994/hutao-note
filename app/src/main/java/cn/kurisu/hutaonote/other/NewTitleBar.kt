package cn.kurisu.hutaonote.other

import android.content.Context
import android.view.View
import android.widget.TextView
import com.alipay.mobile.nebula.view.AbsTitleView

/**
 * 标题栏
 *
 * @author : kurisu
 * @date : 2024-01-19 15:01
 * @version : 1.0
 *
 */
class NewTitleBar(context: Context?) : AbsTitleView() {

    override fun getBackgroundColor(): Int {
        return 0
    }

    override fun setBackgroundAlphaValue(i: Int) {}

    override fun setBackgroundColor(i: Int) {}

    override fun resetTitle() {}

    override fun getTitle(): String? {
        return null
    }

    override fun setTitle(s: String?) {}

    override fun setSubTitle(s: String?) {}

    override fun getMainTitleView(): TextView? {
        return null
    }

    override fun getSubTitleView(): TextView? {
        return null
    }

    override fun showCloseButton(b: Boolean) {}

    override fun getContentView(): View? {
        return null
    }

    override fun showBackButton(b: Boolean) {}

    override fun showBackHome(b: Boolean) {}

    override fun showTitleLoading(b: Boolean) {}

    override fun showOptionMenu(b: Boolean) {}

    override fun getOptionMenuContainer(i: Int): View? {
        return null
    }

    override fun setOptionMenu(b: Boolean, b1: Boolean, b2: Boolean, list: List<MenuData?>?) {}

    override fun getPopAnchor(): View? {
        return null
    }
}