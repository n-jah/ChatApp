package com.nja7.chatapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.nja7.chatapp.databinding.ActivityLogInBinding

class LogIn : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding


    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnlogin: Button
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        edtEmail = binding.editTextText2!!
        edtPassword = binding.editTextTextPassword2!!
        btnlogin = binding.buttonSignIn!!
        btnSignUp = binding.buttonSignUp!!

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        btnlogin.setOnClickListener {
            var email = edtEmail.text.toString()
            var password = edtPassword.text.toString()

            login(email, password)

        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(this@LogIn, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

        private fun login(email: String, password: String) {
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return
            }

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this@LogIn, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        val message = task.exception.toString()
                        val error = message.substringAfterLast(":")
                        showDialog(this,error.toString() )
                    }
                }


        }
    fun showDialog(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Message")
        builder.setMessage(message)
        val dialog = builder.create()
        dialog.show()
    }

}