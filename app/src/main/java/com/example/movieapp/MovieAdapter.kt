package com.example.movieapp

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.model.Movie
import com.example.movieapp.databinding.MovieRowItemBinding

class MovieAdapter(private val movies: MutableList<Movie>): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    constructor(): this(mutableListOf())

    var movieClickListener: MovieClickLister? = null

    class ViewHolder(private val binding: MovieRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageView: ImageView = binding.ivMovieImage
        private val titleView: TextView = binding.tvMovieTitle
        var movie: Movie? = null
        var movieClickListener: MovieClickLister? = null

        init {
            itemView.setOnClickListener {
                movie?.let {
                    Log.d("Item Tapped", "${it.title} selected")
                    movieClickListener?.invoke(it)
                }
            }
        }

        fun configure(movie: Movie) {
            this.movie = movie
            titleView.text = movie.title
            val uri = Uri.parse("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
            Glide.with(imageView)
                .load(uri)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieRowItemBinding.inflate(inflater, parent, false)
        val holder = ViewHolder(binding)
        holder.movieClickListener = movieClickListener
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.configure(movie)
    }

    override fun getItemCount(): Int = movies.count()

    fun addMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }
}

typealias MovieClickLister = (Movie) -> Unit