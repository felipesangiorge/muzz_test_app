package com.muzz_test_felipe.ui.chat_user_select_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muzz_test_felipe.R
import com.muzz_test_felipe.domain.model_domain.UserModel

class UsersToChatAdapter(
    val userClicked: (user: UserModel) -> Unit
) : ListAdapter<UserModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_to_chat, parent, false)

        return ViewHolder(userClicked, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    class ViewHolder(
        private val userClicked: (user: UserModel) -> Unit,
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        private val topParent: ConstraintLayout = view.findViewById(R.id.top_parent)
        private val name: TextView = view.findViewById(R.id.tv_username)
        private val picture: ImageView = view.findViewById(R.id.iv_user_picture)

        private val context = topParent.context

        fun bind(
            user: UserModel
        ) {

            val androidDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_android)
            val imageId = context.resources.getIdentifier(user.photoUrl, "mipmap", context.packageName)

            topParent.setOnClickListener {
                userClicked.invoke(user)
            }

            Glide.with(picture)
                .load(imageId)
                .error(androidDrawable)
                .placeholder(androidDrawable)
                .into(picture)

            name.text = user.name
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}