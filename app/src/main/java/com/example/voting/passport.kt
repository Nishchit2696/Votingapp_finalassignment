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
import com.example.voting.entity.Passport
import com.example.voting.repository.PassportRepository
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

class passport : AppCompatActivity() {

    private lateinit var simage: ImageView
    private lateinit var Firstname: EditText
    private lateinit var Lastname: EditText
    private lateinit var Fathername: EditText
    private lateinit var CitizenshipNo: EditText
    private lateinit var Ocupation: EditText
    private lateinit var Education: EditText
    private lateinit var Phonenumber: EditText
    private lateinit var btnsave: Button
    private lateinit var btnshow:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passport)

        simage = findViewById(R.id.simage)
        Firstname = findViewById(R.id.Firstname)
        Lastname = findViewById(R.id.Lastname)
        Fathername = findViewById(R.id.Fathername)
        CitizenshipNo = findViewById(R.id.CitizenshipNo)
        Ocupation = findViewById(R.id.Ocupation)
        Education = findViewById(R.id.Education)
        Phonenumber = findViewById(R.id.Phonenumber)
        btnsave = findViewById(R.id.btnsave)
        btnshow = findViewById(R.id.btnshow)

        btnshow.setOnClickListener{
            val intent = Intent(this, com.example.voting.getpassport::class.java)
            startActivity(intent)
        }

        btnsave.setOnClickListener {
            val Firstname = Firstname.text.toString()
            val Lastname = Lastname.text.toString()
            val Fathername = Fathername.text.toString()
            val CitizenshipNo = CitizenshipNo.text.toString()
            val Ocupation = Ocupation.text.toString()
            val Education = Education.text.toString()
            val Phonenumber = Phonenumber.text.toString()
            val passport =
                    Passport(
                        Firstname = Firstname,
                        Lastname = Lastname,
                        Fathername = Fathername,
                        CitizenshipNo = CitizenshipNo,
                        Ocupation = Ocupation,
                        Education = Education,
                        Phonenumber = Phonenumber  )

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val passportRepostory = PassportRepository()
                    val response = passportRepostory.addpassport(passport)
                    if (response.success == true) {
                        if(imageUrl!= null){
                            uploadImage(response.data!!._id!!)
                        }
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    this@passport,
                                    "Passport Request Added",
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                                this@passport,
                                ex.toString(),
                                Toast.LENGTH_SHORT
                        )
                                .show()
                    }
                }

            }

        }
        simage.setOnClickListener {
            loadPopUpMenu()
        }
    }

    private fun uploadImage(passportId: String) {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body =
                MultipartBody.Part.createFormData("file", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val passportRepository = PassportRepository()
                    val response = passportRepository.uploadImage(passportId, body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@passport, "Uploaded", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("Mero Error ", ex.localizedMessage)
                        Toast.makeText(
                            this@passport,
                            ex.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                val cursor =
                    contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
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

    // Load pop up menu
    private fun loadPopUpMenu() {
        val popupMenu = PopupMenu(this@passport, simage)
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
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    private fun bitmapToFile(
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
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
