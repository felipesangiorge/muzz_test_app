package com.muzz_test_felipe

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.muzz_test_felipe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModel.Factory(
            Injection.provideUserRepository(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it.message.orEmpty(), Toast.LENGTH_SHORT).show()
        })
    }

    override fun onNavigateUp(): Boolean {
        findNavController(R.id.nav_host_fragment_activity_main).navigateUp();
        return super.onNavigateUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.nav_host_fragment_activity_main).navigateUp()
        return super.onSupportNavigateUp()
    }

}