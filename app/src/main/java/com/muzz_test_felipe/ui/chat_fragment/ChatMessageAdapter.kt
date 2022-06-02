package com.muzz_test_felipe.ui.chat_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muzz_test_felipe.R
import com.muzz_test_felipe.domain.model_domain.ChatMessageModel
import com.muzz_test_felipe.extensions.toVisibility
import java.util.*

class ChatMessageAdapter(
    val selectedUserId: String,
    val toUserId: String
) : ListAdapter<ChatMessageModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)

        return ViewHolder(selectedUserId, toUserId, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    class ViewHolder(
        private val selectedUserId: String,
        private val fromUserId: String,
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        private val clSent: ConstraintLayout = view.findViewById(R.id.cl_send)
        private val tvSent: TextView = view.findViewById(R.id.tv_message_send)
        private val clReceived: ConstraintLayout = view.findViewById(R.id.cl_received)
        private val tvReceived: TextView = view.findViewById(R.id.tv_message_received)

        private val icCheck: ImageView = view.findViewById(R.id.iv_check)

        fun bind(
            message: ChatMessageModel
        ) {
            val calendar = Calendar.getInstance()

            val diff: Long = calendar.timeInMillis - message.date
            val seconds = diff / 1000

            clSent.toVisibility = message.fromUser != selectedUserId
            if (message.fromUser != selectedUserId) {
                tvSent.text = message.message
                icCheck.toVisibility = seconds >= 20 || message.viewed
            }

            clReceived.toVisibility = message.fromUser == selectedUserId
            if (message.fromUser == selectedUserId) {
                tvReceived.text = message.message
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ChatMessageModel>() {
            override fun areItemsTheSame(oldItem: ChatMessageModel, newItem: ChatMessageModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ChatMessageModel, newItem: ChatMessageModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}