package com.anondo.login_signup_inpractice

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.anondo.login_signup_inpractice.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Sign_Up.sec_Key = EncryptDecrypt.encryptData("266636@Ar")
        
        sharedPreferences = getSharedPreferences("my_app" , MODE_PRIVATE)

        var email  = sharedPreferences.getString("email" , "")

        if (email.isNullOrEmpty()){
            var intent = Intent(this , Log_In::class.java)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this , "Welcome Back $email" , Toast.LENGTH_SHORT).show()
        }

        binding.logOut.setOnClickListener {

            var editor : SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("email" , "")
            editor.apply()

            startActivity(Intent(this , Log_In::class.java))
            finish()

        }

        var url = "https://arsarkar.xyz/Apps/showLogSignData.php"

        var jsonObject  = JSONObject().apply {
            put("key" , Sign_Up.sec_Key)
            put("email" , email)
        }

        var jsonObjectRequest : JsonObjectRequest = JsonObjectRequest(Request.Method.POST , url , jsonObject ,
            {response ->

                var name = response.getString("name")
                var emails = response.getString("email")
                var image = response.getString("image")

                binding.texts.text = "Hi! $name. How are You?\nYour email is $emails"

                Glide.with(this)
                    .load(image)
                    .into(binding.imagess)

            }, {


            })

        val queue = Volley.newRequestQueue(this)
        queue.add(jsonObjectRequest)

    }
}