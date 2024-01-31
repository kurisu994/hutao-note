package cn.kurisu.hutaonote.activity

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import cn.kurisu.hutaonote.R
import cn.kurisu.hutaonote.databinding.ActivityMainBinding
import com.alipay.android.phone.scancode.export.ScanRequest
import com.alipay.android.phone.scancode.export.adapter.MPScan
import com.gyf.immersionbar.ktx.immersionBar
import com.mpaas.mriver.api.debug.MriverDebug
import com.mpaas.mriver.api.integration.Mriver
import com.mpaas.nebula.adapter.api.MPNebula
import com.mpaas.nebula.adapter.api.MPTinyHelper
import timber.log.Timber

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var exitTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        backPressedDis()
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            1000
        )

        binding.miniBtn1.setOnClickListener {
            MPNebula.startApp( "7215306148277821");
//            Mriver.startApp(this, "7215306148277821")
        }

        binding.miniBtn2.setOnClickListener {
            Mriver.startApp(this, "1721530614827782")
        }
        binding.miniBtn3.setOnClickListener {
            val activity = this
            val request = ScanRequest()
            request.setScanType(ScanRequest.ScanType.QRCODE)
            MPScan.startMPaasScanActivity(
                activity, request
            ) { b, intent ->
                if (b) {
                    Timber.tag(TAG).i(" ---- 结果 %s", intent.data)
                }
                MriverDebug.debugAppByUri(this,intent.data);
            }
        }
    }

    private fun backPressedDis() {
        //双击返回键回退桌面
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    exitTime = System.currentTimeMillis()
                    val msg = getString(R.string.one_more_press_2_back)
                    Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                } else {
                    moveTaskToBack(true)
                }
            }
        })
    }
}