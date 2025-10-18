package com.anondo.login_signup_inpractice

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.anondo.login_signup_inpractice.databinding.ActivityMainBinding
import com.anondo.login_signup_inpractice.databinding.ActivitySignUpBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.ByteArrayOutputStream
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class Sign_Up : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    var emailsss = ""
    companion object{
        var sec_Key = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logIn.setOnClickListener {

            var intent = Intent(this , Log_In::class.java)
            startActivity(intent)
            finish()

        }

        binding.signUp.setOnClickListener {

            var name = binding.etName.text.toString().trim()
            emailsss = binding.etEmail.text.toString().trim()
            var password = binding.etPassword.text.toString().trim()

            var bitmapDrawable : BitmapDrawable = binding.imageView.drawable as BitmapDrawable
            var bitmap = bitmapDrawable.bitmap

            var outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG , 40 , outputStream)

            var imageByte = outputStream.toByteArray()
            var image64 = Base64.encodeToString(imageByte , Base64.DEFAULT)

            var names = EncryptDecrypt.encryptData(name)
            var emails = EncryptDecrypt.encryptData(emailsss)
            var passwords = EncryptDecrypt.encryptData(password)

            srequest(image64 , names , emails , passwords)

        }

        var launcher : ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){result ->

            if(result.resultCode == Activity.RESULT_OK){
                var intent = result.data
                var uri = intent?.data

                var bitmap = MediaStore.Images.Media.getBitmap(contentResolver ,uri)
                binding.imageView.setImageBitmap(bitmap)
            }

        }

        binding.imageEdit.setOnClickListener {

            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .createIntent {
                    launcher.launch(it)
                }

        }


    }

    fun srequest(image64 : String , name : String , email : String , password : String){

        val queue = Volley.newRequestQueue(this)
        val url = "https://arsarkar.xyz/Apps/loginsignup.php"

        val stringRequest = object : StringRequest(Request.Method.POST, url,
            { response ->

                Toast.makeText(this , response , Toast.LENGTH_SHORT).show()
                if (response.contains("Upload Success")) {

                    var sharedPreferences = getSharedPreferences("my_app" , MODE_PRIVATE)

                    var editor : SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("email" , emailsss)
                    editor.apply()

                    var intent = Intent(this , MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }
            },
            {
                Toast.makeText(this , "Error" , Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = name
                params["email"] = email
                params["password"] = password
                params["image"] = image64
                params["key"] = sec_Key
                return params
            }
        }

        queue.add(stringRequest)

    }

}