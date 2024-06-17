package com.example.mvvm_kotlin_assignment.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_kotlin_assignment.model.ProductModel
import com.example.mvvm_kotlin_assignment.repository.ProductRepository

class ProductViewModel(val repository: ProductRepository): ViewModel() {
    fun uploadImage(imageName:String, imageUrl: Uri, callback:(Boolean, String?) -> Unit){
        repository.uploadImage(imageName,imageUrl){success,imageUrl->
            callback(success,imageUrl)
        }
    }
    fun addProduct(productModel: ProductModel, callback:(Boolean, String?) -> Unit){
        repository.addProduct(productModel,callback)
    }
    var _productList = MutableLiveData<List<ProductModel>?>()
    var productList = MutableLiveData<List<ProductModel>?>()
        get() = _productList
    var _loadingState = MutableLiveData<Boolean>()
    var loadingState = MutableLiveData<Boolean>()
        get() = _loadingState
    fun fetchProduct(){
        _loadingState.value = true
        repository.getAllProduct{productList,success,message ->
            if (productList!=null){
                _productList.value = productList
                _loadingState.value = false
            }
        }
    }

    fun updateProduct(id:String,data:MutableMap<String,Any>?,callback: (Boolean, String?) -> Unit){
        repository.updateProduct(id,data,callback)
    }
    fun deleteData(id:String,callback: (Boolean, String?) -> Unit){
        repository.deleteData(id,callback)
    }
    fun deleteImage(imageName:String,callback: (Boolean, String?) -> Unit){
        repository.deleteImage(imageName,callback)
    }
}