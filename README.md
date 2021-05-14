# Pepper TTS Library (Georgian Language / ქართული ენა) 
 Library for Georgian Text To Speech in QISDK Pepper
# Add the library as a dependency
You can use Jitpack (https://jitpack.io/) to add the library as a gradle dependency.

Step 1) add JitPack repository to your build file:

Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
Step 2) Add the dependency to your app build.gradle in the dependencies section:

```gradle
dependencies {
...
    implementation 'com.github.GeorgianIntergratedSystems:PepperTTS:0.1.4'
}
  ```

# Usage
 You can look at the sample [activity](https://github.com/GeorgianIntergratedSystems/PepperTTS/blob/master/app/src/main/java/ge/gis/peppertts/MainActivity.kt) for an example of how to use the library.
  ## TTSUntils
  ### Init tts
  ``` kotlin 
    class MainActivity: RobotActivity() , RobotLifecycleCallbacks  {
        private var qiContext:QiContext? = null
        //----------------------
        var tts: TTSUtils? = null
        //----------------------
        ...
        }
   ```
  ### Then 
 ``` kotlin
     override fun onRobotFocusGained(qiContext: QiContext?) {
        this.qiContext=qiContext
        tts = TTSUtils(this, qiContext!!)
   }
 ```
 # Example
 ``` kotlin
      tts!!.speakOut("გამარჯობა, მე ვარ რობოტი პეპერი") // To Make Pepper Speak
      tts!!.stopSpeaking() // To Stop Pepper's speech
 ```
    
