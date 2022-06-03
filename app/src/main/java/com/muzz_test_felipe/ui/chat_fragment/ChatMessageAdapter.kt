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
import java.text.SimpleDateFormat
import java.util.*

class ChatMessageAdapter(
    val selectedUserId: String,
    val toUserId: String
) : ListAdapter<ChatMessageModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    var messagesList: List<ChatMessageModel>? = null
    var groupedMessages: List<Pair<Long, ChatMessageModel>>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        groupedMessages = createMessagesGrouping(messagesList)

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)

        return ViewHolder(selectedUserId, toUserId, view, groupedMessages)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(
            getItem(position),
            if (position != 0) getItem(position - 1)
            else getItem(position)
        )
    }

    class ViewHolder(
        private val selectedUserId: String,
        private val fromUserId: String,
        private val view: View,
        private val groupedMessages: List<Pair<Long, ChatMessageModel>>?
    ) : RecyclerView.ViewHolder(view) {

        private val clSent: ConstraintLayout = view.findViewById(R.id.cl_send)
        private val tvSent: TextView = view.findViewById(R.id.tv_message_send)
        private val clReceived: ConstraintLayout = view.findViewById(R.id.cl_received)
        private val tvReceived: TextView = view.findViewById(R.id.tv_message_received)

        private val clGroup: ConstraintLayout = view.findViewById(R.id.cl_time_group)
        private val tvGroupTimestamp: TextView = view.findViewById(R.id.tv_group_timestamp)

        private val icCheck: ImageView = view.findViewById(R.id.iv_check)

        fun bind(
            message: ChatMessageModel,
            lastMessage: ChatMessageModel
        ) {

            val calendar = Calendar.getInstance()

            val diff: Long = calendar.timeInMillis - message.date
            val seconds = diff / 1000
            val minutes = seconds / 60

            if (message == lastMessage) {
                clGroup.toVisibility = minutes >= 60
                tvGroupTimestamp.text = convertTimestampInDayFormat(message.date)
            } else {
                val tempFilter = groupedMessages?.filter {
                    it.first == message.date && minutes >= 60
                }

                clGroup.toVisibility = (tempFilter?.size ?: 0) > 0
                if ((tempFilter?.size ?: 0) > 0) {
                    tvGroupTimestamp.text = convertTimestampInDayFormat(tempFilter!!.first().second.date)
                }
            }

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

        fun convertTimestampInDayFormat(timestamp: Long): String {
            val timeD = Date(timestamp)
            val sdf = SimpleDateFormat("EE  HH:mm")

            return sdf.format(timeD)
        }
    }

    private fun createMessagesGrouping(
        messagesList: List<ChatMessageModel>?,
    ): List<Pair<Long, ChatMessageModel>> {
        var maxDate: Long = 0L
        var firstValueGroup: Long = 0L
        val groupedMessagesInstance = mutableListOf<Pair<Long, ChatMessageModel>>()

        messagesList?.forEachIndexed { index, value ->
            if (index == 0) {
                maxDate = value.date.plus(60 * 60 * 60)
                firstValueGroup = value.date
                groupedMessagesInstance.add(Pair(value.date, value))
            } else {
                if (value.date in firstValueGroup..maxDate) {
                    groupedMessagesInstance.add(Pair(firstValueGroup, value))
                } else {
                    maxDate = value.date.plus(60 * 60 * 60)
                    firstValueGroup = value.date
                    groupedMessagesInstance.add(Pair(firstValueGroup, value))
                }
            }
        }

        return groupedMessagesInstance.toList().orEmpty()
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