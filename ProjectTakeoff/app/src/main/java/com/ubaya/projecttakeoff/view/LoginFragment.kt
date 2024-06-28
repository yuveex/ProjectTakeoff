package com.ubaya.projecttakeoff.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ubaya.projecttakeoff.R
import com.ubaya.projecttakeoff.databinding.FragmentLoginBinding
import com.ubaya.projecttakeoff.viewmodel.UserViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment(), LoginButtonClickListener {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: UserViewModel

    private var loginStatus: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login, container, false)
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginListener = this

        viewModel = (activity as MainActivity).getUserViewModel()

        observeViewModel()

//        with(binding){
//            btnLogin.setOnClickListener {
//                viewModel.login(txtInputUsername.text.toString(), txtInputPassword.text.toString())
//            }
//
//            btnRegister.setOnClickListener {
//                val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
//                Navigation.findNavController(it).navigate(action)
//            }
//        }

    }

    override fun onLoginClick(view: View) {
        val username = binding.username
        val password = binding.password

        if (username != null && password != null) {
            viewModel.login(username, password)
        }
    }

    override fun onRegisterClick(view: View) {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        Navigation.findNavController(view).navigate(action)
    }

    override fun onResume() {
        super.onResume()
        loginStatus = false
        viewModel = (activity as MainActivity).getUserViewModel()
    }

    fun observeViewModel(){
        viewModel.loginStatusLD.observe(viewLifecycleOwner, Observer {
            loginStatus = it

            if(loginStatus == true){
                Toast.makeText(requireContext(), "Login successful",
                    Toast.LENGTH_SHORT).show()
                val action = LoginFragmentDirections.actionLoginFragmentToArticleListFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }
            else{
                Toast.makeText(requireContext(), "Login Failed! Please check your username and password",
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

}