package kz.naimi.rickandmorty.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import kz.naimi.rickandmorty.databinding.ItemRecyclerBinding
import kz.naimi.rickandmorty.network.models.Character

class CharactersRecyclerAdapter(private val characters: List<Character>) :
    RecyclerView.Adapter<CharactersRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(characters[position])

    override fun getItemCount() = characters.size

    inner class ViewHolder(private val binding: ItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.apply {
                Glide.with(root.context).load(character.image).apply(
                    RequestOptions().centerInside().transform(
                        GranularRoundedCorners(16F, 16F, 0F, 0F)
                    )
                ).into(imageView)
                nameTextView.text = character.name
                parameterTextView.text =
                    "Status: ${character.status} \nSpecies: ${character.species}" +
                            "\nGender: ${character.gender} \nLocation: ${character.location.name} "
            }
        }
    }
}