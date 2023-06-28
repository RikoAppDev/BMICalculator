package riko.dev.bmicalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import riko.dev.bmicalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.calculate.setOnClickListener {
            if (checkInputs())
                binding.result.text = bmiResult().toString()
            else
                Toast.makeText(this, "Incorrect input!", Toast.LENGTH_SHORT).show()
        }

        binding.height.setOnEditorActionListener { textView, _, _ ->

            if (checkInputs()) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(textView.windowToken, 0)
                binding.result.text = bmiResult().toString()
            } else
                Toast.makeText(this, "Incorrect input!", Toast.LENGTH_SHORT).show()

            true
        }

        binding.reset.setOnClickListener {
            binding.height.setText("")
            binding.weight.setText("")
            binding.result.text = ""
        }
    }

    private fun bmiResult(): Double {
        val weight = binding.weight.text.toString().toDouble()
        val height = binding.height.text.toString().toDouble()
        return (weight / (height / 100) / (height / 100))
    }

    private fun checkInputs(): Boolean {
        return binding.height.text.toString() != "" && binding.weight.text.toString() != "" && binding.height.text.toString()
            .toDouble() > 0 && binding.weight.text.toString().toDouble() > 0
    }
}