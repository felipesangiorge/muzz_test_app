package com.muzz_test_felipe.ui.chat_user_select_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.muzz_test_felipe.databinding.FragmentChatUserSelectBinding

class ChatUserSelectFragment() : Fragment() {

    private var _binding: FragmentChatUserSelectBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatUserSelectViewModel by viewModels {
        ChatUserSelectViewModel.Factory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChatUserSelectBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }
}