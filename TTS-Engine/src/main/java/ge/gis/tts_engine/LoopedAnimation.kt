package ge.android.gis.pepperttslibrary

import android.util.Log
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.util.FutureUtils
import java.util.concurrent.TimeUnit


class LoopedAnimation(private val qiContext: QiContext, private val delayInSeconds: Int = 60) {

    companion object {
        private const val TAG = "SolitariesLoop"
    }

    private val animationNames = arrayOf(
        "left_hand_to_mouth_b001.qianim"
    )
    private var animationNamesQueue = mutableListOf<String>()
    private var lastAnimationName = ""

    private var animationFuture: Future<Void>? = null

    private fun buildAndRunAnimate(): Future<Void> {
        return FutureUtils.wait(delayInSeconds.toLong(), TimeUnit.SECONDS)
            .andThenCompose {
                lastAnimationName = chooseAnimationName()
                AnimationBuilder.with(qiContext)
                    .withAssets(lastAnimationName)
                    .buildAsync()
            }
            .andThenCompose { animation ->
                AnimateBuilder.with(qiContext)
                    .withAnimation(animation)
                    .buildAsync()
            }
            .andThenCompose { animate ->
                animate.async().run()
            }
            .thenCompose {
                if (!it.isCancelled) {
                    buildAndRunAnimate()
                } else {
                    FutureUtils.wait(0, TimeUnit.NANOSECONDS)
                }
            }
    }

    private fun chooseAnimationName(): String {
        if (animationNamesQueue.isEmpty()) {
            animationNamesQueue = animationNames.toMutableList()
            animationNamesQueue.shuffle()
            if (animationNamesQueue[0] == lastAnimationName) {
                // Swap with the last item
                val lastIndex = animationNamesQueue.size - 1
                animationNamesQueue[0] = animationNamesQueue[lastIndex]
                animationNamesQueue[lastIndex] = lastAnimationName
            }
        }

        return animationNamesQueue.removeAt(0)
    }

    /**
     * Starts periodically playing random animations, until stopped.
     */
    fun start() {
        Log.i(TAG, "SolitariesLoop starting")
        animationFuture = buildAndRunAnimate()
    }

    /**
     * Stops the loop and cancels the current animation (if any); returns a future (that will finish
     * when the animation has effectively been stopped.
     */
    fun stop(): Future<Void>? {
        Log.i(TAG, "SolitariesLoop stopping")
        if (animationFuture == null || animationFuture!!.isDone) {
            Log.e(TAG, "Error: trying to stop a SolitariesLoop that hasn't been started")
        } else {
            animationFuture!!.requestCancellation()
        }
        return animationFuture
    }
}