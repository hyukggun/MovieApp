package com.example.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.setContent
import com.example.movieapp.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityThirdBinding
    private val textView: TextView get() = _binding.thTextView
    private val toastBtn: Button get() = _binding.thBtn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // View Binding Setting
        // Step 1 : init binding using ActivityThirdBinding.inflate
        // Step 2:  set root
        // Step 3:  setContentView
        _binding = ActivityThirdBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)
        textView.text = "Hello third view"

        toastBtn.setOnClickListener {
            Log.d("Th Button Click listener", "Button Clicked")
            Toast.makeText(this, "Test", Toast.LENGTH_LONG).show()
        }
    }
}