package com.anondo.login_signup_inpractice

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.anondo.login_signup_inpractice.Sign_Up.Companion.sec_Key
import com.anondo.login_signup_inpractice.databinding.ActivityLogInBinding

class Log_In : AppCompatActivity() {

    lateinit var binding : ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUp.setOnClickListener {

            var intent = Intent(this , Sign_Up::class.java)
            startActivity(intent)
            finish()

        }

        binding.logIn.setOnClickListener {



            var email = EncryptDecrypt.encryptData(binding.etEmail.text.toString().trim())
            var password = EncryptDecrypt.encryptData(binding.etPassword.text.toString().trim())

            request(email , password)

        }

    }

    fun request(email : String , password : String){

        val queue = Volley.newRequestQueue(this)
        val url = "https://arsarkar.xyz/Apps/checkLogIn.php"

        val stringRequest = object : StringRequest(Request.Method.POST, url,
            { response ->

                Toast.makeText(this , response , Toast.LENGTH_SHORT).show()
                if (response.contains("Valid User")) {

                    var sharedPreferences = getSharedPreferences("my_app" , MODE_PRIVATE)

                    var editor : SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("email" , binding.etEmail.text.toString().trim())
                    editor.apply()

                    var intent = Intent(this , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            },
            {error->
                Toast.makeText(this , error.message , Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()

                params["email"] = email
                params["password"] = password
                params["key"] = EncryptDecrypt.encryptData("266636@Ar")
                return params
            }
        }

        queue.add(stringRequest)

    }

}