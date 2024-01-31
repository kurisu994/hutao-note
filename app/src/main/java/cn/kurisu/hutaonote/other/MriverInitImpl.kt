package cn.kurisu.hutaonote.other

import android.content.Context
import com.alibaba.ariver.kernel.common.utils.ProcessUtils
import com.mpaas.core.MPInitParam
import com.mpaas.mriver.api.init.MriverInitParam
import com.mpaas.mriver.api.init.MriverInitParam.MriverInitCallback
import timber.log.Timber


/**
 * MriverInitImpl
 *
 * @author : kurisu
 * @date : 2024-01-19 14:25
 * @version : 1.0
 *
 */
private const val TAG = "MriverInit"

class MriverInitImpl : com.mpaas.MPInitParamManifest {
    override fun initParam(context: Context?): MPInitParam {
        val mriverInitParam = MriverInitParam.getDefault()
        mriverInitParam.setMriverInitCallback(object : MriverInitCallback {
            override fun onInit() {
                if (ProcessUtils.isMainProcess()) {
                    // 小程序相关配置，比如自定义jsapi，titlebar等
                    Timber.tag(TAG).i(" ---- Mriver 初始化完成")
                }
            }

            override fun onError(e: Exception) {
                Timber.tag(TAG).e(e, " ---- Mriver 初始化失败")
            }
        })
        return MPInitParam.obtain().addComponentInitParam(mriverInitParam)
    }
}