package com.bridgelabz.retrofitpractice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataRecyclerView.layoutManager = LinearLayoutManager(this)

        getMyData()
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataClass>?> {

            override fun onResponse(
                call: Call<List<MyDataClass>?>,
                response: Response<List<MyDataClass>?>
            ) {
                Log.e(TAG, "onResponse: response $response")

                val responseBody = response.body()!!

                val adapter = MyAdapter(baseContext, responseBody)
                adapter.notifyDataSetChanged()
                dataRecyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<List<MyDataClass>?>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}