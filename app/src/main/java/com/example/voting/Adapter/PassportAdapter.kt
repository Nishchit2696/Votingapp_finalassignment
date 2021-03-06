package com.example.voting.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.voting.R
import com.example.voting.UpdatePassport
import com.example.voting.api.ServiceBuilder
import com.example.voting.entity.Passport
import com.example.voting.passport
import com.example.voting.repository.PassportRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class PassportAdapter(
        private val context: Context,
        private val lstpassport: MutableList<Passport>

): RecyclerView.Adapter<PassportAdapter.PassportViewHolder>(){
    class PassportViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val simage: ImageView = view.findViewById(R.id.simage)
        val Firstname: TextView = view.findViewById(R.id.FirstN)
        val Lastname: TextView = view.findViewById(R.id.LastN)
        val Fathername: TextView = view.findViewById(R.id.fatherN)
        val citizenshipNo: TextView = view.findViewById(R.id.CitizenNo)
        val Occupation: TextView = view.findViewById(R.id.Occupa)
        val Education: TextView = view.findViewById(R.id.Edu)
        val Phonenumber: TextView = view.findViewById(R.id.PhoneNo)
        val delete : ImageView = view.findViewById(R.id.delete)
        val edit : ImageView = view.findViewById(R.id.edit)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):PassportViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.viewpassport, parent, false)
        return PassportViewHolder(view)
    }

    override fun onBindViewHolder(holder: PassportAdapter.PassportViewHolder, position: Int) {
        val passport = lstpassport[position]
        holder.Firstname.text = passport.Firstname.toString()
        holder.Lastname.text = passport.Lastname.toString()
        holder.Fathername.text = passport.Fathername.toString()
        holder.citizenshipNo.text = passport.CitizenshipNo.toString()
        holder.Occupation.text = passport.Ocupation.toString()
        holder.Education.text = passport.Education.toString()
        holder.Phonenumber.text = passport.Phonenumber.toString()

        val imagePath = ServiceBuilder.loadImagePath() + passport.simage
        if (!passport.simage.equals("no-photo.jpg")) {
            Glide.with(context)
                    .load(imagePath)
                    .fitCenter()
                    .into(holder.simage)
        }

//
//        holder.edit.setOnClickListener {
//            val intent = Intent(context,UpdateActivity::class.java)
//            intent.putExtra("student",student)
//            context.startActivity(intent)
//        }

        holder.edit.setOnClickListener{
            val intent = Intent(context, UpdatePassport::class.java)
                .putExtra("passport", passport)
            startActivity(context,intent,null)
        }

        holder.delete.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete Passport")
            builder.setMessage("Are You Sure You Want To Delete  ${passport.Firstname} ?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes") { _, _ ->

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val passportRepository = PassportRepository()
                        val response = passportRepository.deletepassport(passport._id!!)
                        if (response.success == true) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                        context,
                                        "Passport Deleted",
                                        Toast.LENGTH_SHORT
                                )
                                        .show()
                            }
                        }
                        withContext(Dispatchers.Main) {
                            lstpassport.remove(passport)
                            notifyDataSetChanged()
                        }

                    }catch (ex: Exception){
//                        withContext(Dispatchers.Main){
//                            Toast.makeText(context,
//                                    ex.toString(),
//                                    Toast.LENGTH_SHORT)
//                                    .show()
//                        }
                    }
                }
            }


            builder.setNegativeButton("No") { _, _ ->
                Toast.makeText(context, "Action Cancelled", Toast.LENGTH_SHORT).show()
            }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }
    override fun getItemCount(): Int {
        return lstpassport.size
    }



}