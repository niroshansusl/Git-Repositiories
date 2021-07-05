package com.niroshan.repositories.ui.repos.adapter

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.niroshan.repositories.data.model.Repo
import com.niroshan.repositories.databinding.ItemTrendingRepoBinding
import com.niroshan.repositories.ui.repos.ReposFragmentDirections
import javax.annotation.Nullable

class ReposAdapter: PagingDataAdapter<Repo, ReposAdapter.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem == newItem

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrendingRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { repo ->
            with(holder as? ViewHolder) {
                this!!.itemView.tag = repo
                if (repo != null) {
                    bind(createOnClickListener(binding, repo), repo)
                }
            }
        }
    }


    private fun createOnClickListener(binding : ItemTrendingRepoBinding, repo: Repo): View.OnClickListener {
        return View.OnClickListener {
            val directions = ReposFragmentDirections.actionReposToDetails(repo)
            val extras = FragmentNavigatorExtras(
                binding.avatar to "avatar_${repo.id}")
            it.findNavController().navigate(directions, extras)
        }
    }

    /*class DoggoImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            //get instance of the DoggoImageViewHolder
            fun getInstance(parent: ViewGroup): DoggoImageViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.item_doggo_image_view, parent, false)
                return DoggoImageViewHolder(view)
            }
        }

        var ivDoggoMain: ImageView = view.findViewById(R.id.ivDoggoMain)

        fun bind(item: String?) {
            //loads image from network using coil extension function
            ivDoggoMain.load(item) {
                placeholder(R.drawable.doggo_placeholder)
            }
        }

    }*/


    class ViewHolder(val binding: ItemTrendingRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, repo: Repo) {

            binding.apply {

                Glide.with(itemView)
                    .load(repo.owner.avatar_url)
                    .centerCrop()
                    .error(android.R.drawable.stat_notify_error)
                    .into(avatar)

                val str = SpannableString( repo.owner.login + " / " + repo.name)
                str.setSpan(
                    StyleSpan(Typeface.BOLD),
                    repo.owner.login.length,
                    str.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                name.text = str

                description.text = repo.description

                language.text = repo.language

                ViewCompat.setTransitionName(this.avatar, "avatar_${repo.id}")

                root.setOnClickListener(listener)
            }

        }
    }
}