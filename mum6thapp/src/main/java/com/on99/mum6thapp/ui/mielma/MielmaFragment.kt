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
import com.on99.mum6thapp.databinding.FragmentOn99Binding

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

        val theSwitch: Switch = binding.on99switch
        theSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                Log.e(tag,"open now!")
            }
            else{
                Log.e(tag,"close now!")
            }
        }
        mielmaViewmodel.text.observe(viewLifecycleOwner){
            theSwitch.text = it
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