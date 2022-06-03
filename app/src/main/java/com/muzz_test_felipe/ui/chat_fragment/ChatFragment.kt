package com.muzz_test_felipe.ui.chat_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muzz_test_felipe.Injection
import com.muzz_test_felipe.R
import com.muzz_test_felipe.databinding.FragmentMainChatBinding
import com.muzz_test_felipe.domain.model_domain.UserModel

class ChatFragment() : Fragment() {

    private var _binding: FragmentMainChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels {
        ChatViewModel.Factory(
            arguments?.getParcelable("user")!!,
            arguments?.getString("selectedUserId")!!,
            Injection.provideUserRepository(requireContext())
        )
    }

    lateinit var adapter: ChatMessageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainChatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val user: UserModel = arguments?.getParcelable("user")!!
        val selectedUserId: String = arguments?.getString("selectedUserId")!!

        val recyclerView: RecyclerView = binding.rvMessages
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ChatMessageAdapter(user.id, selectedUserId)
        recyclerView.adapter = adapter

        binding.ivSendComment.setOnClickListener {
            viewModel.sendMessage()
        }

        binding.ivNavBack.setOnClickListener {
            viewModel.backClicked()
        }

        binding.etText.addTextChangedListener {
            viewModel.setTextChanged(it.toString())
        }

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it.message.orEmpty(), Toast.LENGTH_SHORT).show()
        })

        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            when (it) {
                ChatContract.ViewInstructions.NavigateBack -> {
                    findNavController().navigateUp()
                }
                else -> {}
            }
        })

        viewModel.selectedUser.observe(viewLifecycleOwner, Observer {
            binding.tvUsername.text = it.name

            val androidDrawable = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_android)
            val imageId = resources.getIdentifier(it.photoUrl, "mipmap", requireContext().packageName)

            if (imageId > 0) {
                Glide.with(binding.ivUserPicture)
                    .load(imageId)
                    .error(androidDrawable)
                    .placeholder(androidDrawable)
                    .into(binding.ivUserPicture)
            }
        })

        viewModel.text.observe(viewLifecycleOwner, Observer {
            if (it.orEmpty() != binding.etText.text.toString()) {
                binding.etText.setText(it)
            }
        })

        viewModel.messagesList.observe(viewLifecycleOwner, Observer {
            binding.rvMessages.scrollToPosition(it.size - 1)
            adapter.submitList(it)
            adapter.messagesList = it
        })

        viewModel.isSendButtonClickable.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.ivSendComment.setColorFilter(ContextCompat.getColor(requireContext(), R.color.base_pink))
            } else {
                binding.ivSendComment.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
            }
        })

        return root
    }
}