package com.example.voting

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.voting.entity.License
import com.example.voting.repository.LicenseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class license : AppCompatActivity() {

    private lateinit var simage: ImageView
    private lateinit var Firstname: EditText
    private lateinit var Lastname: EditText
    private lateinit var BloodGroup: EditText
    private lateinit var Ocupation: EditText
    private lateinit var Education: EditText
    private lateinit var Province: EditText
    private lateinit var City: EditText
    private lateinit var Phonenumber: EditText
    private lateinit var btnsave: Button
    private lateinit var btnshow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)

        simage = findViewById(R.id.simage)
        Firstname = findViewById(R.id.Firstname)
        Lastname = findViewById(R.id.Lastname)
        BloodGroup = findViewById(R.id.BloodGroup)
        Ocupation = findViewById(R.id.Ocupation)
        Education = findViewById(R.id.Education)
        Province = findViewById(R.id.Province)
        City = findViewById(R.id.City)
        Phonenumber = findViewById(R.id.Phonenumber)
        btnsave = findViewById(R.id.btnsave)
        btnshow = findViewById(R.id.btnshow)

        simage.setOnClickListener{
            loadPopUpMenu()
        }
        btnsave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    if (imageUrl!=null) {
                        val Firstname = Firstname.text.toString()
                        val Lastname = Lastname.text.toString()
                        val BloodGroup = BloodGroup.text.toString()
                        val Ocupation = Ocupation.text.toString()
                        val Education = Education.text.toString()
                        val Province = Province.text.toString()
                        val City = City.text.toString()
                        val Phonenumber = Phonenumber.text.toString()
                        val license = License(Firstname = Firstname, Lastname = Lastname, BloodGroup = BloodGroup, Ocupation = Ocupation, Education = Education, Province = Province, City = City, Phonenumber = Phonenumber)

                        val licenseRepository = LicenseRepository()
                        val licenseResponse = licenseRepository.insertLicense(license)
                        if(imageUrl!=null){
                            uploadImage(licenseResponse.data!!._id!!)
                            println(licenseResponse.data._id!!)
                        }
                        if(licenseResponse.success==true){
                            //for image process

                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@license,
                                        licenseResponse.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            val builder = AlertDialog.Builder(this@license)
                            builder.setTitle("No Photo")
                            builder.setMessage("PLEASE UPLOAD LICENSE PHOTO TO CONTINUE")
                            builder.setIcon(android.R.drawable.sym_def_app_icon)
                            builder.setPositiveButton("OK") { _, _ ->
                            }
                            val alertDialog: AlertDialog = builder.create()
                            alertDialog.setCancelable(false)
                            alertDialog.show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@license,
                                "Error : $ex", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun uploadImage(licenseId: String) {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                    RequestBody.create(MediaType.parse("image/" + file.extension.toLowerCase().replace("jpg","jpeg")), file)
            val body =
                    MultipartBody.Part.createFormData("simage", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val licenseRepository = LicenseRepository()
                    val response = licenseRepository.uploadImage(licenseId, body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@license, body.toString(), Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("Mero Error ", ex.localizedMessage)
                        Toast.makeText(
                                this@license,
                                ex.localizedMessage,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
    private fun loadPopUpMenu() {
        val popupMenu = PopupMenu(this, simage)
        popupMenu.menuInflater.inflate(R.menu.gallery_camera, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuCamera ->
                    openCamera()
                R.id.menuGallery ->
                    openGallery()
            }
            true
        }
        popupMenu.show()
    }
    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CAMERA_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = this.contentResolver
                val cursor =
                        contentResolver?.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                simage.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                simage.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }
    }
    private fun bitmapToFile(
            bitmap: Bitmap,
            fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                    this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }
}