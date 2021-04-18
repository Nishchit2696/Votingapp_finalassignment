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
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.voting.R
import com.example.voting.UpdateLicense
import com.example.voting.UpdatePassport
import com.example.voting.api.ServiceBuilder
import com.example.voting.entity.License
import com.example.voting.entity.Passport
import com.example.voting.repository.LicenseRepository
import com.example.voting.repository.PassportRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LicenseAdapter (
    private val context: Context,
    private val lstlicense: MutableList<License>

): RecyclerView.Adapter<LicenseAdapter.LicenseViewHolder>(){
    class LicenseViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val simage: ImageView = view.findViewById(R.id.simage)
        val Firstname: TextView = view.findViewById(R.id.FirstN)
        val Lastname: TextView = view.findViewById(R.id.LastN)
        val BloodGroup: TextView = view.findViewById(R.id.fatherN)
        val Occupation: TextView = view.findViewById(R.id.Occupa)
        val Education: TextView = view.findViewById(R.id.Edu)
        val Province: TextView = view.findViewById(R.id.CitizenNo)
        val City: TextView = view.findViewById(R.id.cty)
        val Phonenumber: TextView = view.findViewById(R.id.PhoneNo)
        val delete : ImageView = view.findViewById(R.id.delete)
        val edit : ImageView = view.findViewById(R.id.edit)

}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LicenseAdapter.LicenseViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewlicense, parent, false)
        return LicenseAdapter.LicenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: LicenseAdapter.LicenseViewHolder, position: Int) {
        val license = lstlicense[position]
        holder.Firstname.text = license.Firstname.toString()
        holder.Lastname.text = license.Lastname.toString()
        holder.BloodGroup.text = license.BloodGroup.toString()
        holder.Occupation.text = license.Ocupation.toString()
        holder.Education.text = license.Education.toString()
        holder.Province.text = license.Province.toString()
        holder.City.text = license.City.toString()
        holder.Phonenumber.text = license.Phonenumber.toString()

        val imagePath = ServiceBuilder.loadImagePath() + license.simage
        if (!license.simage.equals("no-photo.jpg")) {
            Glide.with(context)
                .load(imagePath)
                .fitCenter()
                .into(holder.simage)
        }

        holder.edit.setOnClickListener{
            val intent = Intent(context, UpdateLicense::class.java)
                .putExtra("license", license)
            startActivity(context,intent,null)
        }

        holder.delete.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete License")
            builder.setMessage("Are You Sure You Want To Delete  ${license.Firstname} ?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes") { _, _ ->

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val licenseRepository = LicenseRepository()
                        val response = licenseRepository.Deletelicense(license._id!!)
                        if (response.success == true) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "License Deleted",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                        withContext(Dispatchers.Main) {
                            lstlicense.remove(license)
                            notifyDataSetChanged()
                        }

                    }catch (ex: Exception){


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
        return lstlicense.size
    }



}
