package com

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.retrofit.RetrofitInstance
import com.retrofit.UserInfo
import com.tony_fire.signupapp.R
import com.tony_fire.signupapp.databinding.ActivityRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userField()

    }
    private fun userField() {
       binding.regButton.setOnClickListener {
            val ed1stName = binding.ed1stName.text.toString().trim()
            val ed2stName = binding.edSecondName.text.toString().trim()
            val ed_email = binding.edEmail.text.toString().trim()
            val ed_tel = binding.edPhone.text.toString().trim()



            if(ed1stName.isEmpty()){
               binding.ed1stName.error = "Введите имя"
               binding.ed1stName.requestFocus()
               return@setOnClickListener

            }

            if(ed2stName.isEmpty()){
               binding.edSecondName.error = "Введите фамилию"
               binding.edSecondName.requestFocus()
                return@setOnClickListener

           }

            if(ed_email.isEmpty()){
                binding.edEmail.error = "Введите почту"
                binding.edEmail.requestFocus()
                return@setOnClickListener

            }
            if(ed_tel.isEmpty()){
               binding.edPhone.error = "Введите телефон"
                binding.edPhone.requestFocus()
               return@setOnClickListener

           }

            val userinfo = UserInfo("UA",ed_email,ed1stName,"5d1f566d48bc9d0b0d07bba4",ed2stName,ed_tel,"antqwer")
            val call : Call<UserInfo> = RetrofitInstance.api.signUpUser(userinfo)

            call.enqueue(object : Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                    if(response.isSuccessful){
                        FirebaseMessaging.getInstance().token.addOnCompleteListener { task->
                            if(!task.isSuccessful){
                                return@addOnCompleteListener
                            }
                            val token = task.result
                            Log.d("MyLog", "Token: + $token" )
                        }
                        binding.regButton.setBackgroundResource(R.drawable.background_selector_button_color)
                        val i = Intent(this@RegistrationActivity,ThanksActivity::class.java).apply {
                            putExtra("Name",userinfo.first_name)
                            putExtra("Second",userinfo.last_name)

                        }
                        startActivity(i)
                    }
                    else {
                        Toast.makeText(applicationContext,"Произошла ошибка, попробуйте снова", Toast.LENGTH_SHORT).show()
                        return
                    }

                }

                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    Toast.makeText(applicationContext,t.message, Toast.LENGTH_SHORT).show()
                }


            })



        }

    }

}

