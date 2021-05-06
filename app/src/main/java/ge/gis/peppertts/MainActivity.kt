package ge.gis.peppertts

import android.os.Bundle
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import ge.gis.peppertts.databinding.ActivityMainBinding

class MainActivity : RobotActivity() , RobotLifecycleCallbacks {
    private lateinit var binding: ActivityMainBinding
    private var qiContext:QiContext? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        QiSDK.register(this,this)
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {
        this.qiContext=qiContext

    }

    override fun onRobotFocusLost() {
        this.qiContext=null
        QiSDK.unregister(this,this)
    }

    override fun onRobotFocusRefused(reason: String?) {
    }
}