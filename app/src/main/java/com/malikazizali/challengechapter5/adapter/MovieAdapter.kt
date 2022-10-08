package com.malikazizali.challengechapter5.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.malikazizali.challengechapter5.R
import com.malikazizali.challengechapter5.model.Result
import com.malikazizali.challengechapter5.databinding.CardMovieBinding

class MovieAdapter (var listMovie : List<Result>): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    class ViewHolder(var binding : CardMovieBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CardMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load("https://image.tmdb.org/t/p/w185"+listMovie[position].posterPath).into(holder.binding.moviePoster)
        holder.binding.movieTitle.text = listMovie[position].originalTitle
        holder.binding.movieLang.text = listMovie[position].originalLanguage
        holder.binding.movieReleaseDate.text = listMovie[position].releaseDate
        holder.binding.movieRating.text = listMovie[position].voteAverage.toString()

        holder.binding.cardMovie.setOnClickListener{
            val arg = Bundle()
            arg.putString("gambar", listMovie[position].posterPath)
            arg.putString("judul", listMovie[position].originalTitle)
            arg.putString("rating", listMovie[position].voteAverage.toString())
            arg.putString("tanggal", listMovie[position].releaseDate)
            arg.putString("bahasa", listMovie[position].originalLanguage)
            arg.putString("detail", listMovie[position].overview)

            Navigation.findNavController(holder.itemView).navigate(R.id.action_homeFragment_to_detailFragment,arg)
        }

    }

    override fun getItemCount(): Int {
        return listMovie.size
    }
}