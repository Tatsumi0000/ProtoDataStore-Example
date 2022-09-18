package com.example.protodatastore_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.protodatastore_example.viewmodel.ExampleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ExampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val writeButton: Button = findViewById(R.id.button1)
        val readButton: Button = findViewById(R.id.button2)
        val textView: TextView = findViewById(R.id.textView)

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest { uiState ->
                textView.text = uiState.result.toString()
            }
        }

        writeButton.setOnClickListener {
            val randomInt = (0..100).random()
            viewModel.write(randomInt)
        }

        readButton.setOnClickListener {
            viewModel.read()
        }
    }
}
