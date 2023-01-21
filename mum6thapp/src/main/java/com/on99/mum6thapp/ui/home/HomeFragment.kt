package com.on99.mum6thapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.on99.mum6thapp.R
import com.on99.mum6thapp.databinding.FragmentHomeBinding
import com.on99.mum6thapp.ui.notifications.NotificationsFragment
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val manufacturer = android.os.Build.MANUFACTURER
        val model = android.os.Build.MODEL
        val authorInfo:String = "${manufacturer} ${model}"
        val editText:EditText = binding.EditTextOu

        val textView: TextView = binding.textHome
        val emptyString:String = ""
        editText.setText(emptyString)
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val button0:Button = binding.button0
        button0.setOnClickListener {
            if (editText.text.isNullOrEmpty()){
                Log.e(TAG_EDIT_TEXT,"isNullOrEmpty")
            }
            else if (editText.text.length == 0){
                Log.e(TAG_EDIT_TEXT,"lengthIsZero")
            }
            else{
                lifecycleScope.launch {
                    homeViewModel.postHttp(editText.text.toString(),authorInfo)
                }
                editText.setText(emptyString)
            }
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.fragment_home_set_layout,NotificationsFragment(),null)
//                ?.commit()
//            findNavController().navigate(R.id.navigation_notifications)//https://developer.android.google.cn/guide/navigation/navigation-navigate?hl=en//导航到其他页面
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{
        const val TAG_EDIT_TEXT:String = "EDIT_TEXT"
    }
}