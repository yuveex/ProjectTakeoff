package com.ubaya.projecttakeoff.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.projecttakeoff.R
import com.ubaya.projecttakeoff.databinding.FragmentRegisterBinding
import com.ubaya.projecttakeoff.viewmodel.UserViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_register, container, false)
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        with(binding){
            btnRegisterUser.setOnClickListener {
                viewModel.createUser(txtInputEmail.text.toString(), txtInputUsername.text.toString(), txtInputFName.text.toString(),
                    txtInputLName.text.toString(), txtInputPassword.text.toString(), txtInputProfilePicUrl.text.toString())

                viewModel.registerStatusLD.observe(viewLifecycleOwner, Observer {
                    if(it == true){
                        Toast.makeText(requireContext(), "Account successfully created!",
                            Toast.LENGTH_SHORT).show()
                        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                        Navigation.findNavController(view).navigate(action)
                    }
                    else{
                        Toast.makeText(requireContext(), "Registration Failed! Please again later",
                            Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}