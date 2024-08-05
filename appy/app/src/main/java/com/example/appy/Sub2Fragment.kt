package com.example.appy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.appy.R
import com.example.appy.viewModel.DataViewModel

class Sub2Fragment : Fragment() {

    private lateinit var dataViewModel: DataViewModel
    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sub2, container, false)
        textView = view.findViewById(R.id.textView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataViewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        dataViewModel.apiData.observe(viewLifecycleOwner) { data ->
            Log.d("Sub2Fragment", "Data received: $data")
            if (data.isNotEmpty()) {
                val displayText = data.joinToString("\n\n") {
                    "Name: ${it.facltNm}\n +" +
                            "Address: ${it.addr1}\n" +
                            "Industry: ${it.induty}\n" +
                            "Telephone: ${it.tel}\n" +
                            "Latitude: ${it.mapY}\n" +
                            "Longitude: ${it.mapX}"
                }
                textView.text = displayText
            } else {
                "No data received.".also { textView.text = it }
            }
        }
    }
}