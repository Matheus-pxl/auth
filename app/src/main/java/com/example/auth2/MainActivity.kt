package com.example.auth2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.auth2.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        btLogin()
        btRegistrar()
    }

    private fun btRegistrar() {
        binding.btRegistrar.setOnClickListener {
            navegarPaginaRegistro()
        }
    }

    private fun btLogin() {
        binding.btLogin.setOnClickListener {
            val email: String = binding.emailET.text.toString()
            val senha: String = binding.senhaET.text.toString()

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                signinWithEmailAndPassword(email, senha)
            } else {
                Toast.makeText(this@MainActivity, "Preencha os campos", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun navegarPaginaHome(){
        val intent = Intent(this@MainActivity, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun navegarPaginaRegistro() {
        val intent = Intent(this@MainActivity, RegActivity::class.java)
        startActivity(intent)
    }

    private fun signinWithEmailAndPassword(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                task->
            if(task.isSuccessful){
                Log.d(TAG,"signinWithEmailAndPassword: Sucesso")
                val user = auth.currentUser
                if(user != null && isValidEmail(user.email)){
                    if (user.email?.isNotEmpty() == true) {
                        Toast.makeText(baseContext, "Acesso liberado", Toast.LENGTH_LONG).show()
                        navegarPaginaHome()
                    }
                }
            }else{
                Log.d(TAG,"signinWithEmailAndPassword: falhou",task.exception)
                Toast.makeText(baseContext, "Acesso falhou", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun isValidEmail(email: String?): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.orEmpty()).matches()
    }
    companion object{
        private var TAG="emailEsenha"
    }
}