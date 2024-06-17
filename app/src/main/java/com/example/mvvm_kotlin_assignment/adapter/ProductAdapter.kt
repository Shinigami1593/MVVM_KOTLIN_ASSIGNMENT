package com.example.mvvm_kotlin_assignment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_kotlin_assignment.R
import com.example.mvvm_kotlin_assignment.model.ProductModel
import com.example.mvvm_kotlin_assignment.ui.activity.UpdateActivity

class ProductAdapter(var context: Context, var data:ArrayList<ProductModel>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var productName: TextView = view.findViewById(R.id.labelName)
        var productPrice: TextView = view.findViewById(R.id.labelPrice)
        var productDescription: TextView = view.findViewById(R.id.labelDescription)
        var btnEdit: TextView = view.findViewById(R.id.btnEdit)
        var progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        var imgView: ImageView = view.findViewById(R.id.imageViewDisplay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.sample_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productName.text = data[position].name
        holder.productPrice.text = data[position].price.toString()

        var image = data[position].url

        Picasso.get().load(image).into(holder.imgView, object : Callback {
            override fun onSuccess() {
                holder.progressBar.visibility = View.INVISIBLE
            }

            override fun onError(e: Exception?) {
                Toast.makeText(context, e?.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })

        holder.productDescription.text = data[position].description
        holder.btnEdit.setOnClickListener {
            var intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("products", data[position])
            context.startActivity(intent)
        }
    }

    fun getProductId(position: Int): String {
        return data[position].id
    }

    fun getIName(position: Int): String {
        return data[position].imageName
    }

    fun updateData(products: List<ProductModel>) {
        data.clear()
        data.addAll(products)
        notifyDataSetChanged()
    }
}