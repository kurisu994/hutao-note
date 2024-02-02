package cn.kurisu.hutaonote

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Network
import android.view.KeyEvent
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import cn.kurisu.hutaonote.manager.ActivityManager
import cn.kurisu.hutaonote.other.AppConfig
import cn.kurisu.hutaonote.other.DebugLoggerTree
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.alipay.alipaylogger.Log
import com.alipay.mobile.common.logging.api.LoggerFactory
import com.alipay.mobile.framework.LauncherApplicationAgent
import com.alipay.mobile.h5container.api.H5Page
import com.alipay.mobile.nebula.appcenter.res.H5ResourceManager
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider
import com.alipay.mobile.nebula.provider.H5ErrorPageView
import com.alipay.mobile.nebula.provider.H5ReceivedSslErrorHandler
import com.alipay.mobile.nebula.provider.H5TransStatusBarColorProvider
import com.alipay.mobile.nebula.provider.MPH5OnKeyDownProvider
import com.alipay.mobile.nebula.util.H5Utils
import com.alipay.mobile.nebula.webview.APWebView
import com.hjq.toast.Toaster
import com.mpaas.android.mPaaS
import com.mpaas.mriver.api.init.MriverInitParam
import com.mpaas.mriver.api.integration.Mriver
import com.mpaas.mriver.api.resource.MriverResource
import com.mpaas.tinyappcommonres.TinyAppCenterPresetProvider
import com.ut.device.UTDevice
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider


/**
 * app入口
 * @className : App
 * @author : kurisu
 * @date : 2024-01-31 13:28
 */
private const val TAG = "hutaonote-Application"

@HiltAndroidApp
class App : Application(), ImageLoaderFactory {
    @Inject
    lateinit var imageLoader: Provider<ImageLoader>

    override fun onCreate() {
        super.onCreate()
        initSdk(this)
    }

    override fun newImageLoader(): ImageLoader = imageLoader.get()

    private fun initSdk(application: Application) {
        // 初始化日志打印
        if (AppConfig.isLogEnable()) {
            Timber.plant(DebugLoggerTree())
        }
        Timber.tag(TAG).d("initSdk: 初始化相关sdk---> %s", UTDevice.getUtdid(application))
        // mPaas 初始化
        initMpaas(application)
        // 初始化 Toast 框架
        Toaster.init(application)
        // 设置调试模式
        Toaster.setDebugMode(AppConfig.isDebug())
        // Activity 栈管理初始化
        ActivityManager.getInstance().init(application)
        networkListener(application)
    }

    private fun initMpaas(application: Application) {
        mPaaS(application) {
            mriver {
                isAutoInitMriver = true
                mriverInitCallback = object : MriverInitParam.MriverInitCallback {
                    override fun onInit() {
                        Timber.tag(TAG).d("mPaas初始化成功")
                    }

                    override fun onError(e: Exception?) {
                        Timber.tag(TAG).e(e, "mPaas初始化失败")
                    }
                }
                ucInitCallback = object : MriverInitParam.UCInitCallback {
                    override fun onInit() {
                        Timber.tag(TAG).d("uccore初始化成功")
                        h5Config()
                    }

                    override fun onError(e: Exception?) {
                        Timber.tag(TAG).e(e, "uccore初始化失败")
                    }
                }
            }
            callback {
                // 关闭签名
                MriverResource.disableVerify()
                Mriver.setConfig(
                    "h5_nbmngconfig", "{\"config\":{\"al\":\"3\",\"pr\":{\"4\":\"86400\","
                            + "\"common\":\"864000\"},\"ur\":\"1800\",\"fpr\":{\"common\":\"3888000\"}},"
                            + "\"switch\":\"yes\"}"
                )
                Mriver.setUserId("kurisu")
                // 开启签名，公钥
                MriverResource.enableVerify(
                    MriverResource.VERIFY_TYPE_YES,
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy8dNF8IwVw4Edqle16Zf" +
                            "aw2x5G3GeAsZEaTIFdfIJkhDWyq+1kJuhxlEhbIhma6mk7f8mMVdBvVb+rOkxBdN" +
                            "ioV4GPFJsimdvbjl4OUJ+0DGqV4sKbMclC0EQqlCkC7ZmqNs5Vi/c1WlwWd07rj8" +
                            "hmfrteKiwMjJYzZ/pTe3t3OOMpzWvxkqOx9TWWXKPKncuw1jq0GlLGXBua6ryEV8" +
                            "L2SqYy97WuE8tCcbPq5K1Lrhto5RWzBWFLyAgGEA7/fHPSy4Cc0HYrfE/xEbHuiN" +
                            "lQVffW7AuVsvdcyowU9K+WeKYqR4CjCLLdNUkNcxxSvJSga0ofGtDiJczdP6GoDv" +
                            "XQIDAQAB"
                )
            }
        }
    }


    private fun networkListener(application: Application) {
        ContextCompat.getSystemService(
            application,
            ConnectivityManager::class.java
        )?.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                val topActivity: Activity? = ActivityManager.getInstance().getTopActivity()
                if (topActivity !is LifecycleOwner) {
                    return
                }
                if (topActivity.lifecycle.currentState != Lifecycle.State.RESUMED) {
                    return
                }
                Toaster.show(R.string.common_network_error)
            }
        })
    }

    private fun h5Config() {
        // 初始化小程序公共资源包
        H5Utils.setProvider(
            H5AppCenterPresetProvider::class.java.name,
            TinyAppCenterPresetProvider()
        )
        // H5 容器标题栏
        H5Utils.setProvider(
            H5TransStatusBarColorProvider::class.java.name,
            H5TransStatusBarColorProvider { Color.argb(0, 0, 0, 0) }
        )
        // H5 容器拦截 页面错误
        H5Utils.setProvider(
            H5ErrorPageView::class.java.name,
            object : H5ErrorPageView {
                override fun enableShowErrorPage(): Boolean {
                    // true 表示启动自定义错误页
                    return false
                }

                override fun errorPageCallback(
                    h5Page: H5Page,
                    view: APWebView,
                    errorUrl: String,
                    statusCode: Int,
                    errorMsg: String,
                    subErrorMsg: String
                ) {
                    // 获取错误页的 html，demo 中放到了 raw 中，也可以放在其他地方
                    val html = H5ResourceManager.readRawFromResource(
                        R.raw.erro,
                        LauncherApplicationAgent.getInstance().applicationContext.resources
                    )
                    // 将错误页设置给 webview
                    view.loadDataWithBaseURL(errorUrl, html, "text/html", "utf-8", errorUrl)
                    Log.e("dd", "错误回调：$subErrorMsg")
                }
            }
        )
        // H5 容器拦截 ssl错误
        H5Utils.setProvider(
            H5ReceivedSslErrorHandler::class.java.name,
            H5ReceivedSslErrorHandler { _, apSslErrorHandler, _ ->
                try {
                    // 表示忽略ssl错误
                    apSslErrorHandler.proceed()
                } catch (e: Throwable) {
                    LoggerFactory.getTraceLogger().error("H5ReceivedSslErrorHandler", e)
                }
            }
        )

        //H5 容器拦截物理按键
        H5Utils.setProvider(
            MPH5OnKeyDownProvider::class.java.name,
            object : MPH5OnKeyDownProvider {
                override fun needIntercept(
                    page: H5Page?,
                    keyCode: Int,
                    intent: KeyEvent?
                ): Boolean {
                    return true
                }

                override fun onKeyDown(
                    page: H5Page?,
                    keyCode: Int,
                    intent: KeyEvent?
                ): Boolean {
                    return false
                }

            })
    }
}