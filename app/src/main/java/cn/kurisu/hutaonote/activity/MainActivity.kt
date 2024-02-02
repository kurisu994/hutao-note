package cn.kurisu.hutaonote.activity

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import cn.kurisu.hutaonote.R
import cn.kurisu.hutaonote.ui.theme.HuTheme
import com.alipay.android.phone.scancode.export.ScanRequest
import com.alipay.android.phone.scancode.export.adapter.MPScan
import com.mpaas.mriver.api.debug.MriverDebug
import com.mpaas.mriver.api.integration.Mriver
import com.mpaas.nebula.adapter.api.MPNebula
import timber.log.Timber

private const val TAG = "MainActivity"


class MainActivity : ComponentActivity() {
    private var exitTime = 0L

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backPressedDis()
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            1000
        )

        setContent {
            HuTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("首页")
                            },
                            actions = {
                                IconButton(onClick = { /* do something */ }) {
                                    Icon(
                                        imageVector = Icons.Outlined.QrCodeScanner,
                                        contentDescription = null
                                    )
                                }
                            },
                            modifier = Modifier.shadow(10.dp)
                        )
                    },
                ) { paddingValues ->
                    BodyLayout(paddingValues)
                }
            }
        }
    }

    @Composable
    fun BodyLayout(paddingValues: PaddingValues) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    MPNebula.startApp("7215306148277821")
//                                Mriver.startApp(this@MainActivity, "7215306148277821")
                },
                modifier = Modifier
                    .width(200.dp)
            ) {
                Text(
                    text = "小程序1",
                    fontSize = 13.sp
                )
            }

            Button(
                onClick = {
                    Mriver.startApp(this@MainActivity, "1721530614827782")
                },
                modifier = Modifier
                    .width(200.dp)
            ) {
                Text(
                    text = "小程序2",
                    fontSize = 13.sp
                )
            }

            Button(
                onClick = { handleScan() },
                modifier = Modifier
                    .width(200.dp)
            ) {
                Text(
                    text = "扫码",
                    fontSize = 13.sp
                )
            }

            MyText(
                text = "my text",
                firstBaselineToTop = 30.dp,
                modifier = Modifier.background(
                    Color.Cyan
                )
            )
        }
    }

    @Preview
    @Composable
    fun PreviewLayout() {
        BodyLayout(paddingValues = PaddingValues(0.dp))
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

    private fun handleScan() {
        val request = ScanRequest()
        request.setScanType(ScanRequest.ScanType.QRCODE)
        MPScan.startMPaasScanActivity(
            this@MainActivity, request
        ) { b, intent ->
            intent?.let {
                if (b) {
                    Timber.tag(TAG).i(" ---- 结果 %s", intent.data)
                }
                MriverDebug.debugAppByUri(this@MainActivity, intent.data)
            }
        }
    }
}