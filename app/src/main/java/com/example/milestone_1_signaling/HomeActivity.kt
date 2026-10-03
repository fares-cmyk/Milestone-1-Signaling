package com.example.milestone_1_signaling

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser == null) {          // safety: not logged in
            goToLogin()
            return
        }

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val tvRole = findViewById<TextView>(R.id.tvRole)
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // Reference to users/<uid>
        userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.uid)

        // ---------- LOAD profile (JSON -> User object) ----------
        userRef.get()
            .addOnSuccessListener { snapshot ->
                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    tvWelcome.text = "Hello, ${user.name}"
                    tvEmail.text = "Email: ${user.email}"
                    tvRole.text = "Role: ${user.role}"
                    etName.setText(user.name)
                    etPhone.setText(user.phone)
                } else {
                    Toast.makeText(this, "Profile not found", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load profile: ${e.message}", Toast.LENGTH_LONG).show()
            }

        // ---------- SAVE edited profile ----------
        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()

            if (name.isEmpty()) { etName.error = "Name is required"; return@setOnClickListener }
            if (phone.length < 8) { etPhone.error = "Enter a valid phone number"; return@setOnClickListener }

            val updates = mapOf<String, Any>("name" to name, "phone" to phone)
            userRef.updateChildren(updates)
                .addOnSuccessListener {
                    tvWelcome.text = "Hello, $name"
                    Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Update failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            goToLogin()
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}