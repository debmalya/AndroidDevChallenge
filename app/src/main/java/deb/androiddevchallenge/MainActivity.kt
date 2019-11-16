package deb.androiddevchallenge

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentificationOptions
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var languageIdentification: FirebaseLanguageIdentification
    private lateinit var editText: EditText
    private lateinit var translatedTextView: TextView
    private lateinit var translateButton: Button

    override fun onClick(v: View?) {
        if (v == translate) {
            val enteredText = editText.text
            if (enteredText.isNotEmpty() && enteredText.isNotBlank()) {
                val originalText = enteredText.toString()
                /*
                val identifiedLanguage =
                    languageIdentification.identifyLanguage(originalText)
                if (identifiedLanguage.isSuccessful) {


                    Log.d(TAG, identifiedLanguage.result.toString())

                 */
                val options = FirebaseTranslatorOptions.Builder()
                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                    .setTargetLanguage(FirebaseTranslateLanguage.FR)
                    .build()
                val englishGermanTranslator =
                    FirebaseNaturalLanguage.getInstance().getTranslator(options)
                englishGermanTranslator.downloadModelIfNeeded()
                    .addOnSuccessListener {
                        englishGermanTranslator.translate(originalText)
                            .addOnSuccessListener { translatedText ->
                                // Translation successful.
                                translatedTextView.text = translatedText
                                Toast.makeText(
                                    applicationContext, translatedText,
                                    Toast.LENGTH_LONG
                                )
                            }
                            .addOnFailureListener { exception ->
                                // Error.
                                // ...
                                Log.e(TAG, exception.message)
                                Toast.makeText(
                                    applicationContext,
                                    exception.message,
                                    Toast.LENGTH_LONG
                                )
                            }


                    }
                    .addOnFailureListener { exception ->
                        // Model couldn’t be downloaded or other internal error.
                        // ...
                        Log.e(TAG, exception.message)
                    }


                /*
                } else {
                    Log.e(TAG, identifiedLanguage.exception.toString())
                }

                 */
            }
        }
    }


//    val smartReply = FirebaseNaturalLanguage.getInstance().smartReply

    private val TAG = "My Listener"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)




        FirebaseApp.initializeApp(applicationContext);
        val options = FirebaseLanguageIdentificationOptions.Builder()
            .setConfidenceThreshold(0.1F)
            .build()
        languageIdentification =
            FirebaseNaturalLanguage.getInstance().getLanguageIdentification(options)



        editText = findViewById(R.id.editText)
        translatedTextView = findViewById(R.id.translatedText)
        translateButton = findViewById(R.id.translate)
        translateButton.setOnClickListener(this)


        /*
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
         */


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
