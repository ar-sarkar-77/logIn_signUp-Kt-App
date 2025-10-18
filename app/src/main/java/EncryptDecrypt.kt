package com.anondo.login_signup_inpractice

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object EncryptDecrypt {

    private const val SECRET_KEY = "7fQ@Lp2!vC9#rXen"

    open fun encryptData(texta : String) : String {

        var plainText : String = texta
        var plainTextByte = plainText.toByteArray(Charsets.UTF_8)

        var password = SECRET_KEY
        var passwordByte = password.toByteArray(Charsets.UTF_8)

        var secretKey = SecretKeySpec(passwordByte , "AES")

        var ciper : Cipher = Cipher.getInstance("AES")
        ciper.init(Cipher.ENCRYPT_MODE , secretKey)
        var finalByte = ciper.doFinal(plainTextByte)

        var encodStr = Base64.encodeToString(finalByte , Base64.DEFAULT)

        return encodStr
    }

    fun decryptData(texts: String) : String{

        var encData : String = texts
        var decodeData = Base64.decode(encData , Base64.DEFAULT)

        var password = SECRET_KEY
        var passwordByte = password.toByteArray(Charsets.UTF_8)

        var secretKey = SecretKeySpec(passwordByte , "AES")

        var ciper : Cipher = Cipher.getInstance("AES")
        ciper.init(Cipher.DECRYPT_MODE , secretKey)
        var finalByte = ciper.doFinal(decodeData)

        var plainDatas = String(finalByte , Charsets.UTF_8)

        return plainDatas
    }

}
