package deb.androiddevchallenge

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentificationOptions

import kotlinx.android.synthetic.main.activity_main.*
import android.text.method.ScrollingMovementMethod
import android.widget.Scroller


class MainActivity : AppCompatActivity() {

    private lateinit var languageIdentifier : FirebaseLanguageIdentification

//    val smartReply = FirebaseNaturalLanguage.getInstance().smartReply

    private  val TAG = "My Listener"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var voiceMessage = findViewById<EditText>(R.id.editText)
        voiceMessage.setScroller(Scroller(applicationContext))
        voiceMessage.maxLines = 10
        voiceMessage.isVerticalScrollBarEnabled = true
        voiceMessage.movementMethod = ScrollingMovementMethod()

        FirebaseApp.initializeApp(applicationContext);
        val options = FirebaseLanguageIdentificationOptions.Builder()
            .setConfidenceThreshold(0.1F)
            .build()
        val naturalLanguageInstance = FirebaseNaturalLanguage.getInstance()
        val languageIdentification =
            naturalLanguageInstance.languageIdentification.identifyLanguage("I am fine with ML")
        if (languageIdentification.isSuccessful) {
            Log.d(TAG, languageIdentification.result.toString())
        } else {
            Log.e(TAG, "Failed")
        }
         languageIdentifier = FirebaseNaturalLanguage.getInstance().getLanguageIdentification(options)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
