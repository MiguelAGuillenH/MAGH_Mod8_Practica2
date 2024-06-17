package com.magh.mod8practica2.ui.fragments

import android.os.Bundle
import android.text.InputType
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.magh.mod8practica2.R
import com.magh.mod8practica2.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    //Variables for Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Instaciamos el objecto de Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        //Si el usuario ya esta loggeado
        if (firebaseAuth.currentUser != null) {
            goToMainFragment()
        }

        //On btnLogin click
        binding.btnLogin.setOnClickListener {
            if (!validateFields()) {
                return@setOnClickListener
            }

            //Autenticamos al usuario
            authenticateUser(email, password)
        }

        //On btnRegister click
        binding.btnRegister.setOnClickListener {
            if (!validateFields()) {
                return@setOnClickListener
            }

            //Registramos al usuario
            registerUser(email, password)
        }

        binding.lblResetPassword.setOnClickListener { view ->
            //val resetMail = EditText(view.context)
            val resetMail = EditText(ContextThemeWrapper(view.context, R.style.PlayMent_EditText))
            resetMail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            resetMail.setTextColor(view.context.getColor(R.color.PM_white))

            MaterialAlertDialogBuilder(view.context, R.style.PlayMent_Dialog)
                .setTitle(getString(R.string.label_reset_password))
                .setMessage(getString(R.string.message_reset_password))
                .setView(resetMail)
                .setPositiveButton(getString(R.string.label_send)) { _, _ ->
                    val mail = resetMail.text.toString()
                    if (mail.isNotEmpty()) {
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.message_password_reset_mail_success),
                                Toast.LENGTH_SHORT
                            ).show()
                        }.addOnFailureListener {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.message_password_reset_mail_error, it.message),
                                Toast.LENGTH_SHORT
                            ).show() //it tiene la excepción
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_please_email),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.setNegativeButton(getString(R.string.label_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }

    }

    private fun validateFields(): Boolean{
        email = binding.txtEmail.text.toString().trim() //para que quite espacios en blanco
        password = binding.txtPassword.text.toString().trim()

        if(email.isEmpty()){
            binding.txtEmail.error = getString(R.string.error_no_email)
            binding.txtEmail.requestFocus()
            return false
        }

        if(password.isEmpty() || password.length < 6){
            binding.txtPassword.error = getString(R.string.error_no_password)
            binding.txtPassword.requestFocus()
            return false
        }

        return true
    }

    private fun authenticateUser(usr: String, pwd: String) {
        binding.progressBar.visibility = View.VISIBLE
        firebaseAuth.signInWithEmailAndPassword(usr, pwd)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.message_auth_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.visibility = View.GONE
                    goToMainFragment()
                } else {
                    binding.progressBar.visibility = View.GONE
                    handleErrors(authResult)
                }
            }
    }

    private fun registerUser(usr: String, pwd: String) {
        binding.progressBar.visibility = View.VISIBLE
        firebaseAuth.createUserWithEmailAndPassword(usr, pwd)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.message_register_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    goToMainFragment()
                } else {
                    binding.progressBar.visibility = View.GONE
                    handleErrors(authResult)
                }
            }
    }

    private fun goToMainFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,TracksListFragment.newInstance())
            //.addToBackStack(null)
            .commit()
    }

    private fun handleErrors(task: Task<AuthResult>){
        var errorCode = ""

        try{
            errorCode = (task.exception as FirebaseAuthException).errorCode
        }catch(e: Exception){
            e.printStackTrace()
        }

        when(errorCode){
            "ERROR_INVALID_EMAIL" -> {
                Toast.makeText(requireContext(),
                    getString(R.string.error_invalid_email), Toast.LENGTH_SHORT).show()
                binding.txtEmail.error = getString(R.string.error_invalid_email)
                binding.txtEmail.requestFocus()
            }
            "ERROR_WRONG_PASSWORD" -> {
                Toast.makeText(requireContext(),
                    getString(R.string.error_wrong_password), Toast.LENGTH_SHORT).show()
                binding.txtPassword.error = getString(R.string.error_wrong_password)
                binding.txtPassword.requestFocus()
                binding.txtPassword.setText("")

            }
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                //An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.
                Toast.makeText(requireContext(),
                    getString(R.string.error_account_exists_with_different_credential), Toast.LENGTH_SHORT).show()
            }
            "ERROR_EMAIL_ALREADY_IN_USE" -> {
                Toast.makeText(requireContext(),
                    getString(R.string.error_email_already_in_use), Toast.LENGTH_LONG).show()
                binding.txtEmail.error = getString(R.string.error_email_already_in_use)
                binding.txtEmail.requestFocus()
            }
            "ERROR_USER_TOKEN_EXPIRED" -> {
                Toast.makeText(requireContext(),
                    getString(R.string.error_user_token_expired), Toast.LENGTH_LONG).show()
            }
            "ERROR_USER_NOT_FOUND" -> {
                Toast.makeText(requireContext(),
                    getString(R.string.error_user_not_found), Toast.LENGTH_LONG).show()
            }
            "ERROR_WEAK_PASSWORD" -> {
                Toast.makeText(requireContext(), getString(R.string.error_weak_password), Toast.LENGTH_LONG).show()
                binding.txtPassword.error = getString(R.string.error_weak_password)
                binding.txtPassword.requestFocus()
            }
            "NO_NETWORK" -> {
                Toast.makeText(requireContext(),
                    getString(R.string.error_no_network), Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(requireContext(),
                    getString(R.string.error_no_auth), Toast.LENGTH_SHORT).show()
            }
        }

    }

}