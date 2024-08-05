package com.example.appy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.appy.R
import com.example.appy.model.CampingData
import com.example.appy.model.ApiResponse
import com.example.appy.viewModel.DataViewModel
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class Sub1Fragment : Fragment() {

    private lateinit var dataViewModel: DataViewModel
    private lateinit var fetchDataButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sub1, container, false)
        fetchDataButton = view.findViewById(R.id.fetchDataButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataViewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        fetchDataButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val apiResponse = fetchDataFromApi()
                    Log.d("Sub1Fragment", "API Response: $apiResponse")
                    withContext(Dispatchers.Main) {
                        dataViewModel.setApiData(apiResponse)
                        Log.d("Sub1Fragment", "Data set in ViewModel: ${dataViewModel.apiData.value}")
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Failed to fetch data: ${e.message}", Toast.LENGTH_SHORT).show()
                        Log.e("Sub1Fragment", "Error fetching data", e)
                    }
                }
            }
        }
    }

    fun fetchDataFromApi(): List<CampingData> {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://apis.data.go.kr/B551011/GoCamping/basedList?serviceKey=pf76kk16RM4ps9DOsBUE99VQN8KYknf8W2kMxyHAgIBahr1d0IhrQ48cOWW2ArYkTGnE%2F0oaQ4%2BaVLpa5pwyrA%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json")
            .build()
        val response = client.newCall(request).execute()
        val jsonResponse = response.body?.string()
        Log.d("Sub1Fragment", "JSON response: $jsonResponse")

        return if (jsonResponse != null) {
            val gson = Gson()
            try {
                val apiResponse = gson.fromJson(jsonResponse, ApiResponse::class.java)
                apiResponse.response.body.items
            } catch (e: JsonSyntaxException) {
                Log.e("Sub1Fragment", "JSON parsing error", e)
                emptyList()
            }
        } else {
            emptyList()
        }
    }
}