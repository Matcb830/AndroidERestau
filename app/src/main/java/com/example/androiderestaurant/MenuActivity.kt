package com.example.fr.isen.ORSO.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.fr.isen.ORSO.androiderestaurant.databinding.ActivityMenuBinding
import com.example.fr.isen.ORSO.androiderestaurant.network.MenuResult
import com.example.fr.isen.ORSO.androiderestaurant.network.NetworkConstant
import com.example.fr.isen.ORSO.androiderestaurant.R
import com.google.gson.GsonBuilder
import org.json.JSONObject

enum class Category { ENTREE, PLAT, DESSERT }

class MenuActivity : AppCompatActivity() {
    companion object {
        const val extraKey = "extraKey"
    }

    lateinit var binding: ActivityMenuBinding
    lateinit var currentCategory: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val category = intent.getSerializableExtra(extraKey) as? Category
        currentCategory = category ?: Category.ENTREE
        supportActionBar?.title = categoryName()
        //si la category est null {category=Starter}
        makeRequest()
    }

    private fun showDatas(category: com.example.fr.isen.ORSO.androiderestaurant.network.Category) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter =CustomAdapter(category.items) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.PLATE_EXTRA, it)
            startActivity(intent)
        }
    }

    private fun categoryName(): String {
        return when (currentCategory) {
            Category.ENTREE -> getString(R.string.buttonStart)
            Category.PLAT -> getString(R.string.buttonMain)
            Category.DESSERT -> getString(R.string.buttonFinish)
        }
    }

    private fun makeRequest(){
        val queue = Volley.newRequestQueue(this)
        val params = JSONObject()
        params.put(NetworkConstant.idShopKey, 1)
        val request =  JsonObjectRequest(
        com.android.volley.Request.Method.POST,
        NetworkConstant.url,
        params,
            { result ->
                Log.d("request", result.toString(2))
                parseData(result.toString())
            },
            { error ->
                Log.e("request", error.toString())
            }
        )
        queue.add(request)
    }
    private fun parseData(data: String){
        val result = GsonBuilder().create().fromJson(data, MenuResult::class.java)
        val category = result.data.first {it.name == categoryFilterKey()}
        showDatas(category)
    }
    private fun categoryFilterKey():String{
        return when (currentCategory){
            Category.ENTREE -> "Entrées"
            Category.PLAT -> "Plats"
            Category.DESSERT -> "Desserts"
        }
    }
}