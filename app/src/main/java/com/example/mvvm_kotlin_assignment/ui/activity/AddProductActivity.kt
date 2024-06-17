package com.example.mvvm_kotlin_assignment.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mvvm_kotlin_assignment.R
import com.example.mvvm_kotlin_assignment.databinding.ActivityAddProductBinding
import com.example.mvvm_kotlin_assignment.model.ProductModel
import com.example.mvvm_kotlin_assignment.repository.ProductRepositoryImpl
import com.example.mvvm_kotlin_assignment.utils.ImageUtils
import com.example.mvvm_kotlin_assignment.utils.LoadingUtils
import com.example.mvvm_kotlin_assignment.viewmodel.ProductViewModel
import com.squareup.picasso.Picasso
import java.util.UUID

class AddProductActivity : AppCompatActivity() {
    lateinit var binding:ActivityAddProductBinding

    lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    var imageUri : Uri? = null
    lateinit var loadingUtils: LoadingUtils
    lateinit var imageUtils: ImageUtils
    lateinit var productModel: ProductViewModel

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activityResultLauncher.launch(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUtils = ImageUtils(this)
        imageUtils.registerActivity {url->
            url.let {
                imageUri = it
                Picasso.get().load(url).into(binding.imageBrowse)
            }
        }
        var repo = ProductRepositoryImpl()
        productModel = ProductViewModel(repo)

        binding.imageBrowse.setOnClickListener{
            imageUtils.launchGallery(this)
        }

        binding.btnProduct.setOnClickListener{

            if(imageUri != null){
                uploadImage()
            }else{
                Toast.makeText(applicationContext,"Please upload image first",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun addProduct(url: String, imageName:String){
        var name:String = binding.name.text.toString()
        var price:Int = binding.price.text.toString().toInt()
        var desc:String = binding.description.text.toString()

        var data = ProductModel("",name,price,desc,url,imageName)
        productModel.addProduct(data){success, message ->
            if(success){
                loadingUtils.dismiss()
                Toast.makeText(applicationContext,message, Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(applicationContext,message, Toast.LENGTH_LONG).show()
            }
        }
    }
    fun uploadImage(){
        loadingUtils.showLoading()
        val imageName = UUID.randomUUID().toString()
        imageUri?.let {
            productModel.uploadImage(imageName,it){
                    success, imageUrl ->
                if(success){
                    addProduct(imageUrl.toString(),imageName.toString())
                }
            }
        }
    }

}