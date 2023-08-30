package com.example.movieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieResponse
import com.example.movieapp.util.decode

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val adapter = MovieAdapter()

    private var currentPage = 1
    private var totalPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ViewBinding 연결하기
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val recyclerView = binding.rvMovies
        val layoutManager = LinearLayoutManager(binding.root.context)
        recyclerView.layoutManager = layoutManager
        adapter.movieClickListener = { movie ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val rvPosition = (recyclerView.layoutManager as? LinearLayoutManager)!!.findLastVisibleItemPosition()

                val totalCount = recyclerView.adapter?.itemCount?.minus(1)

                if (rvPosition == totalCount) {
                    currentPage += 1
                    updateRecyclerView()
                }
            }
        })

        val fabBtn = binding.mainFab
        fabBtn.setOnClickListener {
            Toast.makeText(this, "This is info button",  Toast.LENGTH_LONG).show()
        }

        updateRecyclerView()
    }

    private fun fetchMovies(): List<Movie> {
        var header = mapOf(
            "accept" to "application/json",
            "Authorization" to "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmZmE3NGM2NmE4MTViYTI3NzAzNjdmMTc1ZTgxODhlOSIsInN1YiI6IjY0M2JhYTFjNzE5YWViMDRjYmViZWVkOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.3XqlRAnhTm9oIAPb_4zT8dWLyySVIfOl-mA8UZqgrzg"
        )
        Log.d("fetchMovies called", "currentPage is $currentPage")
        val result = request("get", "https://api.themoviedb.org/3/movie/now_playing?page=${currentPage}", header = header)
        val response = result?.decode<MovieResponse>()
        totalPage = response?.totalPages ?: 1
        currentPage = response?.page ?: 1
        return response?.results ?: listOf()
    }

    private fun updateRecyclerView() {
        Thread {
            val movies = fetchMovies()
            runOnUiThread {
                adapter.addMovies(movies)
            }
        }.start()
    }
}