package com.muzz_test_felipe.ui.chat_user_select_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muzz_test_felipe.Injection
import com.muzz_test_felipe.R
import com.muzz_test_felipe.databinding.FragmentChatUserSelectBinding
import com.muzz_test_felipe.extensions.toVisibility

class ChatUserSelectFragment() : Fragment() {

    private var _binding: FragmentChatUserSelectBinding? = null
    private val binding get() = _binding!!

    lateinit var adapter: UsersToChatAdapter

    private val viewModel: ChatUserSelectViewModel by viewModels {
        ChatUserSelectViewModel.Factory(
            Injection.provideUserRepository(requireContext())
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChatUserSelectBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.rvUsersToChat
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = UsersToChatAdapter(viewModel::userToChatClicked)
        recyclerView.adapter = adapter

        binding.llSelectUser.setOnClickListener {
            viewModel.selectUsersClicked()
        }

        binding.clSelectUserSecond.setOnClickListener {
            viewModel.updateSelectedUser(0)
        }

        binding.clSelectUserThird.setOnClickListener {
            viewModel.updateSelectedUser(1)
        }

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it.message.orEmpty(), Toast.LENGTH_SHORT).show()
        })

        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ChatUserSelectContract.ViewInstructions.NavigateToChat -> {
                    val bundle = Bundle()
                    bundle.putParcelable("user", it.user)
                    bundle.putString("selectedUserId", it.selectedUserId)

                    findNavController().navigate(R.id.action_chatUserSelectFragment_to_mainChatFragment, bundle)
                }
                else -> {}
            }
        })

        viewModel.isSelectUsersClicked.observe(viewLifecycleOwner, Observer {
            binding.clSelectUsers.toVisibility = it

            if (it) {
                binding.icArrow.rotation = 180F
            } else {
                binding.icArrow.rotation = 0F
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

        viewModel.usersToSelect.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)

            it.first().let {
                binding.tvUsernameSecond.text = it.name

                val androidDrawable = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_android)
                val imageId = resources.getIdentifier(it.photoUrl, "mipmap", requireContext().packageName)

                if (imageId > 0) {
                    Glide.with(binding.ivUserPictureSecond)
                        .load(imageId)
                        .error(androidDrawable)
                        .placeholder(androidDrawable)
                        .into(binding.ivUserPictureSecond)
                }
            }

            it.last().let {
                binding.tvUsernameThird.text = it.name

                val androidDrawable = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_android)
                val imageId = resources.getIdentifier(it.photoUrl, "mipmap", requireContext().packageName)

                if (imageId > 0) {
                    Glide.with(binding.ivUserPictureThird)
                        .load(imageId)
                        .error(androidDrawable)
                        .placeholder(androidDrawable)
                        .into(binding.ivUserPictureThird)
                }
            }
        })

        return root
    }
}