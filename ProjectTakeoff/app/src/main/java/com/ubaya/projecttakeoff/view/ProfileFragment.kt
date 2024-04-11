package com.ubaya.projecttakeoff.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.projecttakeoff.R
import com.ubaya.projecttakeoff.databinding.FragmentProfileBinding
import com.ubaya.projecttakeoff.viewmodel.UserViewModel
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: UserViewModel

    private var userId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).getUserViewModel()

        observeViewModel()

        with(binding){
            btnUpdate.setOnClickListener {
                viewModel.updateUser(
                    txtInputEmail.text.toString(), txtInputUsername.text.toString(), txtInputFName.text.toString(),
                    txtInputLName.text.toString(), txtInputProfilePicUrl.text.toString(), userId
                )
            }
        }

        binding.btnLogout.setOnClickListener {
            viewModel.clearLiveData()

            val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            Navigation.findNavController(requireView()).navigate(action)

//            Navigation.findNavController(view).popBackStack(R.id.loginFragment, false)
        }
    }

    private fun observeViewModel(){
        viewModel.userLD.observe(viewLifecycleOwner, Observer {
            with(binding){
                Log.d("userprofiledetail", it.toString())
                txtInputEmail.setText(it.email)
                txtInputFName.setText(it.first_name)
                txtInputLName.setText(it.last_name)
                txtInputUsername.setText(it.username)
                txtInputProfilePicUrl.setText(it.profile_picture)
                userId = it.id

                val picasso = Picasso.Builder(requireContext())
                picasso.listener{picasso, uri, exception ->
                    exception.printStackTrace()
                }

                var imgUrl = it.profile_picture
                picasso.build().load(imgUrl).into(imgProfilePic, object: Callback {
                    override fun onSuccess(){

                    }

                    override fun onError(e: Exception?) {
                        Log.e("picasso_error", e.toString() + imgUrl)
                    }
                })
            }
        })
    }
}