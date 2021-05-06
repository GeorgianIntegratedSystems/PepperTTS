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
        if (status == TextToSpeech.SUCCESS) {
            if (status == TextToSpeech.LANG_MISSING_DATA) {
                Log.e("TTS", "The Language specified is not supported!")
            } else {
                Log.e("TTS", "Initialization Failed !")
            }
        }
    }


    fun speakOut(text: String){

        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")

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
    }

    fun stopSpeaking() {

        tts!!.stop()

    }

}