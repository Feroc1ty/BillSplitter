package ru.rykunov.billsplitter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import ru.rykunov.billsplitter.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var guestCount: Int = 1
        var billSum: Double = 0.0
        var result: String
        var tips: Int = 0

        fun update(){
            billSum = binding.textBillSum.text.toString().toDouble()
            result = "${(((billSum + ((billSum / 100) * tips.toDouble())) / guestCount) * 100).roundToInt() / 100.0}"
            binding.tvSplitCount.text = guestCount.toString()
            binding.textTotalResult.text = result.toBigDecimal().toString()
        }
        binding.seekBarTips.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tips = progress
                binding.textSeekPercent.text = "$progress %"
                binding.textTipsCosts.text = "${(((billSum / 100) * tips.toDouble()) * 100).roundToInt() / 100.0}"
                update()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        binding.textBillSum.doAfterTextChanged {
            binding.cvPersonsCount.visibility = View.VISIBLE
            binding.cvTipsCount.visibility = View.VISIBLE
            if(it!!.isNotEmpty()){
                update()
            }
            else{
                binding.cvPersonsCount.visibility = View.GONE
                binding.cvTipsCount.visibility = View.GONE
            }
        }

        binding.btnPlusSplitCount.setOnClickListener{
            if (guestCount < 99){
                guestCount++
                update()
            }
        }
        binding.btnMinusSplitCount.setOnClickListener {
            if(guestCount > 1){
                guestCount--
                update()
            }
        }

    }

}