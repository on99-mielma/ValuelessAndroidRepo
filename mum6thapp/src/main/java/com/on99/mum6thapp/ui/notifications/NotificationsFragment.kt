package com.on99.mum6thapp.ui.notifications

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.on99.mum6thapp.R
import com.on99.mum6thapp.data.OuAdapter
import com.on99.mum6thapp.databinding.FragmentNotificationsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val listView:ListView = binding.listView
        var arrayList = ArrayList<List<String>>()
        notificationsViewModel.al.observe(viewLifecycleOwner){
            arrayList = it
        }

//        val inputText:TextInputEditText = binding.TIET01
//        notificationsViewModel.text.observe(viewLifecycleOwner){
//            inputText.text = it as Editable
//        }
        val button001:Button = binding.button001
        val buttonShow:Button = binding.buttonShow
        buttonShow.visibility  = View.INVISIBLE
        button001.setOnClickListener {
            lifecycleScope.launch {
                notificationsViewModel.fetchHttp()
                delay(3500)
                val adapter = OuAdapter(context!!,arrayList)
                listView.adapter = adapter
            }
            Toast.makeText(activity, "button be clicked", Toast.LENGTH_LONG)
//            Log.e("ArrayList",arrayList.toString())
            buttonShow.visibility = View.VISIBLE
        }
        buttonShow.setOnClickListener {
//            val adapter = OuAdapter(context!!,arrayList)
//            listView.adapter = adapter
            buttonShow.visibility  = View.INVISIBLE
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}