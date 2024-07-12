package com.example.auth2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.auth2.databinding.ActivityRegBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var binding:ActivityRegBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        buttonCadastrar()
    }

    private fun buttonCadastrar() {
        binding.btCadstrar.setOnClickListener {
            val email: String = binding.emailETreg.text.toString()
            val senha: String = binding.senhaETreg.text.toString()
            val confSenha: String = binding.confsenhaETreg.text.toString()
            if (email.isNotEmpty() && senha.isNotEmpty() && confSenha.isNotEmpty()) {
                if (senha == confSenha) {
                    if (isValidEmailFormat(email)) {
                        createUserWithEmailAndPassword(email, senha)
                    } else {
                        Toast.makeText(
                            this@RegActivity,
                            "Digite um email válido no formato exemplo@example.com",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(this@RegActivity, "Senhas diferentes!!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(
                    this@RegActivity,
                    "Campos vazios!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun isValidEmailFormat(email: String): Boolean {
        // Padrão simples de verificação de email
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun createUserWithEmailAndPassword(email:String, password:String){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                task->
            if(task.isSuccessful){
                Log.d(TAG, "createUserWithEmailAndPassword: sucesso")
                Toast.makeText(baseContext, "Usuario cadastrado com sucesso", Toast.LENGTH_LONG).show()
                val user = auth.currentUser
                finish()
            }else{
                Log.d(TAG,"createUserWithEmailAndPassword: falhou",task.exception)
                Toast.makeText(baseContext, "Criação falhou, digite um email@example.com ", Toast.LENGTH_LONG).show()
            }
        }
    }
    companion object{
        private var TAG="emailEsenha"
    }
}