package ge.android.gis.pepperttslibrary

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.aldebaran.qi.sdk.QiContext

class TTSUtils(context: Context, qiContext: QiContext) : TextToSpeech.OnInitListener {

        var tts: TextToSpeech? = TextToSpeech(context, this)
        var loopedAnimation: LoopedAnimation? = LoopedAnimation(qiContext, 0)


    override fun onInit(status: Int) {
            when(status){
                TextToSpeech.LANG_MISSING_DATA -> Log.e("TTS", "The Language specified is not supported!")
                TextToSpeech.ERROR_INVALID_REQUEST -> Log.e("TTS", "ERROR INVALI REQUEST")
                TextToSpeech.ERROR_NETWORK -> Log.e("TTS", "ERROR NETWORK")
                TextToSpeech.ERROR_NETWORK_TIMEOUT -> Log.e("TTS", "ERROR NETWORK TIMEOUT")
                TextToSpeech.ERROR_NOT_INSTALLED_YET -> Log.e("TTS", "ERROR NOT INSTALLED YET")
                TextToSpeech.ERROR_OUTPUT -> Log.e("TTS", "ERROR OUTPUT")
                TextToSpeech.ERROR_SERVICE -> Log.e("TTS", "ERROR SERVICE")
                TextToSpeech.ERROR_SYNTHESIS -> Log.e("TTS", "ERROR SYNTHESIS")
                TextToSpeech.SUCCESS -> Log.i("TTS", "TTS IS INITIALIZED")
            }
    }


    fun speakOut(text: String){

        tts!!.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String) {
                Log.i("TAG", "onStart: ")
                loopedAnimation!!.start()

            }

            override fun onError(utteranceId: String) {
                Log.i("TAG", "onError: $utteranceId ")

                loopedAnimation!!.stop()


            }

            override fun onDone(utteranceId: String) {
                Log.i("TAG", "onDone: ")

                loopedAnimation!!.stop()


            }
        })

        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")

    }

    fun stopSpeaking() {
        tts!!.stop()
    }
}