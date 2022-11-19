package com.on99.mum6thapp.ui.mielma

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.on99.mum6thapp.databinding.FragmentOn99Binding
import kotlinx.coroutines.launch

class MielmaFragment : Fragment(){

    private var  _binding:FragmentOn99Binding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mielmaViewmodel =
            ViewModelProvider(this).get(MielmaViewModel::class.java)

        _binding = FragmentOn99Binding.inflate(inflater, container, false)

        val root: View = binding.root

        val inputTextView: TextView = binding.input0

        val theSwitch: Switch = binding.on99switch
        theSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                Log.e(tag,"open now!")
                if (inputTextView.text.isNullOrEmpty()){
                    Log.e(tag,"isNullOrEmpty")
                }
                else if (inputTextView.text.length == 0){
                    Log.e(tag, "length is zero!")
                }
                else{
                    lifecycleScope.launch {
                        mielmaViewmodel.fetchSocket(inputTextView.text.toString())
                    }
                }
            }
            else{
                Log.e(tag,"close now!")
            }
        }
        mielmaViewmodel.text.observe(viewLifecycleOwner){
            theSwitch.text = it
        }
        val outputTextView:TextView = binding.showOutput0
        mielmaViewmodel.socketData.observe(viewLifecycleOwner){
            outputTextView.text = it
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{
        const val tag:String = "LOG!!!!"
    }
}