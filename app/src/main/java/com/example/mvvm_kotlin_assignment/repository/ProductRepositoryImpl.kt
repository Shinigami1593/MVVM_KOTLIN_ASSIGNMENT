package com.example.mvvm_kotlin_assignment.repository

import android.net.Uri
import com.example.mvvm_kotlin_assignment.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProductRepositoryImpl:ProductRepository {
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref : DatabaseReference = database.reference.child("products")
    var firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef : StorageReference = firebaseStorage.reference

    override fun uploadImage(imageName: String, imageUrl: Uri, callback: (Boolean, String?) -> Unit) {
//        val imageName = UUID.randomUUID().toString()

    }

    override fun addProduct(productModel: ProductModel, callback: (Boolean, String?) -> Unit) {
        var id = ref.push().key.toString() //creates random unique id for database entities
        productModel.id = id

        ref.child(id).setValue(productModel).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Your data added successfully")
            } else {
                callback(false,"Unable to add the data")
            }

        }
    }

    override fun getAllProduct(callback: (List<ProductModel>?, Boolean, String?) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var productList = mutableListOf<ProductModel>()
                for(eachData in snapshot.children){
                    var product = eachData.getValue(ProductModel::class.java)
//                        Log.d("data from firebase", product?.name.toString())
                    if(product!=null){
                        productList.add(product)
                    }

                }
                callback(productList,true,"Data successfuly retrieved")

            }

            override fun onCancelled(error: DatabaseError) {
                callback(null,false,"Unable to fetch ${error.message}")
            }

        })
    }

    override fun updateProduct(id: String,data:MutableMap<String,Any>?, callback: (Boolean, String?) -> Unit) {
        data?.let {
            ref.child(id).updateChildren(it).addOnCompleteListener {
                if(it.isSuccessful){
                    callback(true, "Your data has been updated")
                }else{
                    callback(false, "Unable to update the data")
                }
            }
        }
    }

    override fun deleteData(id: String, callback: (Boolean, String?) -> Unit) {
        ref.child(id).removeValue().addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Data deleted")
            }else{
                callback(false,"Unable to delete data")
            }
        }
    }

    override fun deleteImage(imageName: String, callback: (Boolean, String?) -> Unit) {
        storageRef.child("products").child(imageName).delete().addOnCompleteListener{
            if(it.isSuccessful){
                callback(true,"Image deleted")
            }else{
                callback(false,"Unable to delete image")
            }
        }
    }
}