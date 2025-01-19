package com.miassolutions.rentingtools.ui.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.miassolutions.rentingtools.R
import com.miassolutions.rentingtools.data.models.Customer
import com.miassolutions.rentingtools.databinding.FragmentAddEditCustomerBinding
import com.miassolutions.rentingtools.myapp.MyApplication
import com.miassolutions.rentingtools.ui.viewmodel.SharedViewModel
import com.miassolutions.rentingtools.ui.viewmodel.SharedViewModelFactory
import com.miassolutions.rentingtools.utils.clearInputs
import com.miassolutions.rentingtools.utils.isPermissionGranted
import com.miassolutions.rentingtools.utils.requestPermission
import com.miassolutions.rentingtools.utils.showToast
import java.io.File


class AddEditCustomerFragment : Fragment(R.layout.fragment_add_edit_customer) {

    private var _binding: FragmentAddEditCustomerBinding? = null
    private val binding get() = _binding!!

    private val rentalViewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory((requireActivity().application as MyApplication).repository)
    }

    private var customerPicUri: Uri? = null // Holds the URI of the selected image
    private val REQUEST_CODE_CAMERA_PERMISSION = 101

    private val args: AddEditCustomerFragmentArgs by navArgs()

    private var isEditing = false
    private var customerId : Long? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddEditCustomerBinding.bind(view)

        customerId = args.customerId

        if (customerId != -1L) {
            isEditing = true
            loadCustomerData(customerId!!)
            binding.btnSubmit.text = "Update"
            binding.title.text = "Updated the customer"
        }


        setupSubmitBtn()
        setupSelectPicBtn()

    }

    private fun loadCustomerData(customerId: Long) {
        rentalViewModel.getCustomerById(customerId).observe(viewLifecycleOwner) { customer ->
            customer?.let {
                populateFields(customer)
            }
        }

    }

    private fun populateFields(customer: Customer) {
        binding.apply {
            etCustomerName.setText(customer.customerName)
            etCnic.setText(customer.cnicNumber)
            etCustomerPhone.setText(customer.customerPhone)
            etConstructionPlace.setText(customer.constructionPlace)
            etContractorName.setText(customer.contractorName)
            etContractorPhone.setText(customer.contractorPhone)
            etOwnerName.setText(customer.ownerName)
            etOwnerPhone.setText(customer.ownerPhone)
            customerPicUri = Uri.parse(customer.customerPic)
            customerImage.setImageURI(customerPicUri)
        }

    }

    private fun setupSubmitBtn() {
        binding.btnSubmit.setOnClickListener {
            val customer = collectCustomer()
            if (customer != null) {
                if (isEditing){
                    rentalViewModel.updateCustomer(customer)
                    showToast(requireContext(),
                        getString(R.string.is_updated_successfully, customer.customerName))
                } else {
                    rentalViewModel.insertCustomer(customer)
                    showToast(
                        requireContext(),
                        getString(R.string.is_saved_successfully, customer.customerName)
                    )
                }
                clearInputFields()
                findNavController().popBackStack()

            }

        }
    }

    private fun collectCustomer(): Customer? {
        binding.apply {

            val customerName = etCustomerName.text.toString()
            val customerCnic = etCnic.text.toString()
            val customerPhone = etCustomerPhone.text.toString()
            val constructionPlace = etConstructionPlace.text.toString()
            val contractorName = etContractorName.text.toString()
            val contractorPhone = etContractorPhone.text.toString()
            val ownerName = etOwnerName.text.toString()
            val ownerPhone = etOwnerPhone.text.toString()

            if (validateInputs()) {
                return Customer(
                    customerPic = customerPicUri?.toString() ?: "",

                    customerName = customerName,
                    cnicNumber = customerCnic,
                    customerPhone = customerPhone,
                    constructionPlace = constructionPlace,
                    contractorName = contractorName,
                    contractorPhone = contractorPhone,
                    ownerName = ownerName,
                    ownerPhone = ownerPhone
                )
            }
        }
        return null
    }

    private fun validateInputs(): Boolean {
        return when {
            binding.etCustomerName.text.isNullOrEmpty() -> {
                showToast(requireContext(), getString(R.string.please_enter_customer_name))
                false
            }

            binding.etCnic.text.isNullOrEmpty() -> {
                showToast(requireContext(), getString(R.string.please_enter_customer_cnic))
                false
            }

            binding.etCustomerPhone.text.isNullOrEmpty() -> {
                showToast(requireContext(), getString(R.string.please_enter_customer_phone_no))
                false
            }

            binding.etConstructionPlace.text.isNullOrEmpty() -> {
                showToast(requireContext(), getString(R.string.please_enter_construction_place))

                false
            }

            binding.etContractorName.text.isNullOrEmpty() -> {
                showToast(requireContext(), getString(R.string.please_enter_contractor_name))
                false
            }

            binding.etContractorPhone.text.isNullOrEmpty() -> {
                showToast(requireContext(), getString(R.string.please_enter_contractor_phone_no))
                false
            }

            binding.etOwnerName.text.isNullOrEmpty() -> {
                showToast(requireContext(), getString(R.string.please_enter_owner_name))
                false
            }

            binding.etOwnerPhone.text.isNullOrEmpty() -> {
                showToast(requireContext(), getString(R.string.please_enter_owner_phone))
                false
            }

            else -> true
        }
    }

    private fun clearInputFields() {
        binding.apply {
            clearInputs(
                etCustomerName,
                etCnic,
                etCustomerPhone,
                etConstructionPlace,
                etContractorName,
                etContractorPhone,
                etOwnerName,
                etOwnerPhone
            )
        }
    }

    private fun setupSelectPicBtn() {
        binding.customerImage.setOnClickListener {
            showImageSourceOptions()
        }
    }

    private fun showImageSourceOptions() {
        // Open a dialog to let the user choose between Camera and Gallery
        val options = arrayOf("Take Photo", "Choose from Gallery")
        val builder = AlertDialog.Builder(requireContext())
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> openCamera()
                1 -> openGallery()
            }
        }
        builder.show()
    }

    private fun openCamera() {
        if (isPermissionGranted(Manifest.permission.CAMERA)) {
            // Open Camera
            val photoFile = createImageFile()
            customerPicUri = FileProvider.getUriForFile(
                requireContext(),
                "com.miassolutions.rentingtools.fileprovider",
                photoFile
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, customerPicUri)
            }
            cameraResultLauncher.launch(intent)
        } else {
            requestPermission(Manifest.permission.CAMERA, REQUEST_CODE_CAMERA_PERMISSION)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryResultLauncher.launch(intent)
    }

    // Camera and Gallery result handlers
    private val cameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                binding.customerImage.setImageURI(customerPicUri)
            }
        }

    private val galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                customerPicUri = result.data?.data
                binding.customerImage.setImageURI(customerPicUri)
            }
        }

    private fun createImageFile(): File {
        val storageDir: File = requireContext().getExternalFilesDir(null)!!
        return File.createTempFile(
            "customer_pic_",  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}