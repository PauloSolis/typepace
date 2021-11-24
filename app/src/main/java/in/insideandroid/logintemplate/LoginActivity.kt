package `in`.insideandroid.logintemplate

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.et_password
import kotlinx.android.synthetic.main.activity_signup.et_username
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.sqrt

class LoginActivity : AppCompatActivity() {
    lateinit var editText: EditText
    var keyDowns = mutableListOf<Long>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editText = findViewById(R.id.et_password)
        setStatusBarTransparent(this@LoginActivity)
    }

    private fun setStatusBarTransparent(activity: AppCompatActivity) {
        //Make Status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        //Make status bar icons color dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity.window.statusBarColor = Color.WHITE
        }
    }

    fun onClick(view: View) {
        if (view.id == R.id.button_signin) {
            //var usersList = emptyList<User>()
            val database = MyDatabase.getDatabase(this)
            var inputUser = User(id=null, email="paulo_solis97@hotmail.com", password = "paulosolis1", SD= 41.68989423315085875, average =  100.00)
            var comparison = mutableListOf<Long>()
            var password = et_password.text
            var email = et_username.text
            var pass: Boolean
            println(password)
            println(email)

            if (database != null) {
                var usersList: User  =  database.users().get(email.toString())
                println(usersList)
                for (i in 0 until keyDowns.size - 1) {
                    comparison.add(keyDowns[i + 1] - keyDowns[i])
                }
                //println(comparison.toString() + "EL resultado")
                val mean = comparison.average()
                var SD: Double = 0.0
                for (i in comparison) {
                    SD += (i - mean).pow(2)
                }
                var result = sqrt(SD / comparison.size)
                val allowedRange = usersList.SD * .25
                val lowBound = usersList.SD - allowedRange
                val topBound = usersList.SD + allowedRange
                var inconsistency: Boolean = false
                println("El result" + result)
                println("la mean" + mean)
                println("lowBound" + lowBound)
                println("top" + topBound)
                println("SD" + SD)

                if (result < lowBound || result > topBound) {
                    print("entra al if")
                    inconsistency = true
                }
                val nInconsistencies = ceil(keyDowns.size * .20)
                var rate = (100 * (keyDowns.size - nInconsistencies)) / keyDowns.size
                //val allowedInconsistencies = ceil(keyDowns.size * .40)

                //print("Rate: "+ rate)


                println(usersList.email + "observer out" + usersList.SD)
                if (password.toString() == usersList.password && usersList.email == email.toString()) {
                    if (!inconsistency) {
                        println("Login success")

                    } else {
                        println("A lot of inconsistencies on the tempo")
                    }
                    print("Rate: " + rate)
                } else {
                    println("Login failed")
                }

            }

        } else if (view.id == R.id.button_signup) {
            Log.i("Oncliclick", "ForgottenPass");
            editText.clearFocus()
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        editText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                keyDowns.clear()
                println("erase");
            }

        }
        var start = 0

        Log.i("Event", event.toString())
        if (event.action == KeyEvent.ACTION_DOWN) {
            //Log.d("Presion", event.downTime.toString())
            keyDowns.add(Date().getTime())
            println(keyDowns + "keydowns")
            println(Date().getTime())

        } else if (event.action == KeyEvent.ACTION_UP) {
            var delta = Date().getTime()
            //Log.d("Levanta", event.eventTime.toString())
            //Log.i("Tiempo", delta.toString())

        }

        return super.dispatchKeyEvent(event)
    }

}
