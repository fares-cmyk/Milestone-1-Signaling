package com.example.milestone_1_signaling

// Data model saved as JSON in Firebase. Default values are REQUIRED
// so Firebase can rebuild the object from JSON (needs a no-arg constructor).
data class User(
    var uid: String = "",
    var name: String = "",
    var email: String = "",
    var phone: String = "",
    var role: String = ""   // "buyer" or "seller"
)