package com.example.movieapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ActivityMovieDetailBinding
import com.example.movieapp.model.Movie
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    private val imageView: ImageView get() = binding.ivDetailImage
    private val floatingButton: FloatingActionButton get() = binding.fab

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        // View Binding
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        // View Content 생성하기
        setContentView(binding.root)

        // Intent 에서 데이터 추출하기
        val movie = intent.extras?.getSerializable("movie") as? Movie

        movie?.let {
            val uri = Uri.parse("https://image.tmdb.org/t/p/w500/${movie.backdropPath}")
            Glide.with(imageView)
                .load(uri)
                .into(imageView)
            binding.toolbar.title = it.title
            binding.tvDetailMovie.text = it.overview
        }

        setSupportActionBar(binding.toolbar)

        floatingButton.setOnClickListener {
            Log.d("ButtonClicked", "Button clicked!")
            Toast.makeText(it.context, "Test", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }
}