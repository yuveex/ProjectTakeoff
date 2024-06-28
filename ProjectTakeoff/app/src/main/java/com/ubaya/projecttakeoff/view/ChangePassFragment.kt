package com.ubaya.projecttakeoff.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.projecttakeoff.R
import com.ubaya.projecttakeoff.databinding.FragmentChangePassBinding
import com.ubaya.projecttakeoff.viewmodel.UserViewModel
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 * Use the [ChangePassFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChangePassFragment : Fragment(), ChangePassButtonClickListener {
    private lateinit var binding: FragmentChangePassBinding
    private lateinit var viewModel: UserViewModel

    private var userId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_change_pass, container, false)
        binding = FragmentChangePassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changePassListener = this

        viewModel = (activity as  MainActivity).getUserViewModel()

        observeViewModel()

//        with(binding){
//            btnConfirmChangePass.setOnClickListener {
//                viewModel.updatePassword(userId, txtInputOldPassword.text.toString(), txtInputNewPassword.text.toString())
//            }
//        }
    }

    override fun onChangePassClick(view: View) {

        val oldPass = binding.oldPassword
        val newPass = binding.newPassword

        if (oldPass != null && newPass != null) {
            viewModel.updatePassword(userId, oldPass, newPass)
        }
    }

    fun observeViewModel(){
        viewModel.userLD.observe(viewLifecycleOwner, Observer {
            binding.user = it
            userId = it.id

//            val picasso = Picasso.Builder(requireContext())
//            picasso.listener{picasso, uri, exception ->
//                exception.printStackTrace()
//            }
//
//            var imgUrl = it.profile_picture
//            picasso.build().load(imgUrl).into(binding.imgProfilePicPass, object: Callback {
//                override fun onSuccess(){
//
//                }
//
//                override fun onError(e: Exception?) {
//                    Log.e("picasso_error", e.toString() + imgUrl)
//                }
//            })
        })

        viewModel.passUpdateStatusLD.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Toast.makeText(requireContext(), "Password change successful! Please relogin",
                    Toast.LENGTH_SHORT).show()
                viewModel.clearLiveData()
                val action = ChangePassFragmentDirections.actionChangePassFragmentToLoginFragment()
                Navigation.findNavController(requireView()).navigate(action)
//                findNavController()
//                    .navigate(R.id.loginFragment,
//                        null,
//                        NavOptions.Builder()
//                            .setPopUpTo(R.id.changePassFragment,
//                                false).build()
//                    )

            }
            else{
                Toast.makeText(requireContext(), "Password change failed! Please check your inputted password.",
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

}