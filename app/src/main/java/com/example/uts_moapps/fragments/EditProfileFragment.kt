package com.example.uts_moapps.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.uts_moapps.R
import com.example.uts_moapps.UserPreferences
import java.io.File

class EditProfileFragment : Fragment() {

    private lateinit var prefs: UserPreferences
    private var cameraPhotoUri: Uri? = null

    private lateinit var imgProfileEdit: ImageView
    private lateinit var btnChangePhoto: TextView
    private lateinit var etUsernameEdit: EditText
    private lateinit var btnSaveProfile: Button
    private lateinit var btnBack: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        prefs = UserPreferences(requireContext())

        // Bind views (IDs sesuai fragment_edit_profile.xml yang kamu kirim)
        imgProfileEdit = view.findViewById(R.id.imgProfileEdit)
        btnChangePhoto = view.findViewById(R.id.btnChangePhoto)
        etUsernameEdit = view.findViewById(R.id.etUsernameEdit)
        btnSaveProfile = view.findViewById(R.id.btnSaveProfile)
        btnBack = view.findViewById(R.id.btnBack)

        // Set username yang tersimpan (jika ada)
        etUsernameEdit.setText(prefs.getUsername())

        // SAFELY load saved profile image (avoid MIUI gallery provider crashes)
        val savedUriString = prefs.getProfileImage()
        if (!savedUriString.isNullOrEmpty()) {
            if (isSafeToLoadUri(savedUriString)) {
                try {
                    imgProfileEdit.setImageURI(Uri.parse(savedUriString))
                } catch (se: SecurityException) {
                    // Device denies access to that provider (MIUI gallery, etc.)
                    Log.w("EditProfile", "SecurityException loading profile image: $savedUriString", se)
                    imgProfileEdit.setImageResource(R.drawable.ic_person)
                    // optional: clear invalid saved uri so next startup won't try again
                    // prefs.saveProfileImage(null) -- but UserPreferences.saveProfileImage expects String, so use empty:
                    // prefs.saveProfileImage("")
                } catch (e: Exception) {
                    Log.w("EditProfile", "Exception loading profile image: $savedUriString", e)
                    imgProfileEdit.setImageResource(R.drawable.ic_person)
                }
            } else {
                // Not safe to load (MIUI or third-party provider) → fallback
                imgProfileEdit.setImageResource(R.drawable.ic_person)
            }
        } else {
            imgProfileEdit.setImageResource(R.drawable.ic_person)
        }

        // Back
        btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // Change Photo → request camera permission & open camera
        btnChangePhoto.setOnClickListener {
            requestCameraPermission()
        }

        // Save
        btnSaveProfile.setOnClickListener {
            prefs.saveUsername(etUsernameEdit.text.toString())
            // Save cameraPhotoUri if exists; otherwise keep existing saved uri (or empty)
            val toSave = cameraPhotoUri?.toString() ?: prefs.getProfileImage().orEmpty()
            if (toSave.isNotEmpty()) prefs.saveProfileImage(toSave)
            Toast.makeText(requireContext(), "Profile Updated", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return view
    }

    private fun isSafeToLoadUri(uriString: String): Boolean {
        // Disallow known problematic authorities (MIUI gallery, other non-exported providers)
        // You can expand this list if you hit more problematic authorities.
        return try {
            val uri = Uri.parse(uriString)
            val authority = uri.authority ?: return false
            if (authority.startsWith("com.miui.gallery")) return false
            // allow file:// and package's own fileprovider and content from media store
            if (uri.scheme == "file") return true
            if (authority == "${requireContext().packageName}.provider") return true
            // allow typical media providers like media, com.android.providers.media.documents
            if (authority.contains("media") || authority.contains("com.android.providers.media")) return true
            // conservative default: false (avoid crash). If you want to allow more, add cases.
            false
        } catch (e: Exception) {
            false
        }
    }

    // Permission launcher
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) openCamera() else Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
        }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            openCamera()
        }
    }

    // camera launcher
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == android.app.Activity.RESULT_OK) {
                // if cameraPhotoUri was set before launching, we can show it
                cameraPhotoUri?.let {
                    imgProfileEdit.setImageURI(it)
                }
            }
        }

    private fun openCamera() {
        // create temporary file in cache dir
        val photoFile = File(requireContext().cacheDir, "profile_photo.jpg")
        cameraPhotoUri = FileProvider.getUriForFile(requireContext(),
            requireContext().packageName + ".provider",
            photoFile)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPhotoUri)
        // Grant URI permission to camera apps (best practice)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        cameraLauncher.launch(intent)
    }
}
