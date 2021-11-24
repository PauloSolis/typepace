package `in`.insideandroid.logintemplate

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.et_password
import kotlinx.android.synthetic.main.activity_signup.et_username
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class SignupActivity : AppCompatActivity() {
    var keyDowns1 = mutableListOf<Long>()
    var keyDowns2 = mutableListOf<Long>()
    var keyDowns3 = mutableListOf<Long>()
    var keyDowns4 = mutableListOf<Long>()
    var keyDownHelper = mutableListOf<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { super.onBackPressed() }
        setStatusBarWhite(this@SignupActivity)
    }

    private fun setStatusBarWhite(activity: AppCompatActivity){
        //Make status bar icons color dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity.window.statusBarColor = Color.WHITE
        }
    }
    fun onClick(view: View) {
        var password = et_password
        var email = et_email
        var password2 = et_phone
        var password3 = et_username
        var password4 = et_confirm_password
        val database = MyDatabase.getDatabase(this)
        if (view.id == R.id.button_signin){
            if(password.text.toString() == "" || password2.text.toString() == "" || password3.text.toString() == ""
                || password4.text.toString() == "" || email.text.toString() == ""){
                println("Llena todos los campos")
            }else {
                if(password.text.toString() != password2.text.toString() || password.text.toString() != password3.text.toString()
                    || password.text.toString() != password4.text.toString()){
                    println("Las contraseñas no coinciden")
                }else{
                    val test = calculation(keyDowns1)
                    println("test" + test)
                    val test2 = calculation(keyDowns2)
                    println("test2" + test2)
                    val test3 = calculation(keyDowns3)
                    println("test3" + test3)
                    val test4 = calculation(keyDowns4)
                    println("test4" + test4)
                    val total = (test + test2 + test3 + test4)/4
                    println("test: total" + total)
                    var inputUser = User( id = null, email= email.text.toString(), password = password.text.toString(), SD= total, average =  100.00)
                    if (database != null) {
                        print("entro al databse")
                        database.users().insertAll(inputUser)
                    }
                    startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                }
            }
        }
    }

    fun calculation(keyDowns: MutableList<Long>): Double {
        var comparison = mutableListOf<Long>()
        for (i in 0 until keyDowns.size - 1) {
            comparison.add(keyDowns[i + 1] - keyDowns[i])
        }
        //println(comparison.toString() + "EL resultado")
        val mean = comparison.average()
        var SD: Double = 0.0
        for (i in comparison) {
            SD += (i - mean).pow(2)
        }
        return sqrt(SD / comparison.size)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        et_password.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                keyDowns1.clear()
                keyDownHelper.clear()
                println("erase");
            }
            if (!hasFocus) {
                println("save1")
                keyDowns1.addAll(keyDownHelper)
            }

        }

        et_confirm_password.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                keyDowns2.clear()
                keyDownHelper.clear()
                println("erase2");
            }
            if (!hasFocus) {
                println("save2")
                keyDowns2.addAll(keyDownHelper)
            }
        }
        et_username.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                keyDowns3.clear()
                keyDownHelper.clear()
                println("erase3");
            }
            if (!hasFocus) {
                println("save3")
                keyDowns3.addAll(keyDownHelper)
            }
        }
        et_phone.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                keyDowns4.clear()
                keyDownHelper.clear()
                println("erase4");
            }
            if (!hasFocus) {
                println("save4")
                keyDowns4.addAll(keyDownHelper)
            }

        }

        Log.i("Event", event.toString())
        if (event.action == KeyEvent.ACTION_DOWN) {
            //Log.d("Presion", event.downTime.toString())
            keyDownHelper.add(Date().getTime())
            println(keyDownHelper + "keydowns")

        }

        return super.dispatchKeyEvent(event)
    }
}
