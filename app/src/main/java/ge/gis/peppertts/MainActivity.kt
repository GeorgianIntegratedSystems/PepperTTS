package ge.gis.peppertts

import android.os.Bundle
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import ge.android.gis.pepperttslibrary.TTSUtils
import ge.gis.peppertts.databinding.ActivityMainBinding

class MainActivity : RobotActivity() , RobotLifecycleCallbacks {
    private lateinit var binding: ActivityMainBinding
    private var qiContext:QiContext? = null
    var tts: TTSUtils? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        QiSDK.register(this,this)

        binding.button.setOnClickListener {
            tts!!.speakOut("გამარჯობა, მე ვარ რობოტი პეპერი")
        }
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {
        this.qiContext=qiContext
        tts = TTSUtils(this, qiContext!!)

    }

    override fun onRobotFocusLost() {
        this.qiContext=null
        QiSDK.unregister(this,this)
    }

    override fun onRobotFocusRefused(reason: String?) {
    }
}