package com.ubaya.projecttakeoff.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.projecttakeoff.R
import com.ubaya.projecttakeoff.databinding.FragmentReadArticleBinding
import com.ubaya.projecttakeoff.viewmodel.ArticleDetailViewModel
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReadArticleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReadArticleFragment : Fragment(), PageButtonClickListener {
    private lateinit var binding: FragmentReadArticleBinding
    private lateinit var viewModel: ArticleDetailViewModel
    private var currentPageIndex: Int = 0
    private var articlePageCount: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_read_article, container, false)
        binding = FragmentReadArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var articleId = 0

        binding.pageButtonListener = this

        arguments?.let{
            articleId = ReadArticleFragmentArgs.fromBundle(requireArguments()).articleId
        }

        viewModel = ViewModelProvider(this).get(ArticleDetailViewModel::class.java)
        viewModel.refresh(articleId)

        observeViewModel()
        updateButtonStatus()

//        with(binding){
//
//            updateButtonStatus()
//
//            btnPrev.setOnClickListener{
//                if(currentPageIndex > 0){
//                    currentPageIndex -= 1
//                }
//                observeViewModel()
//                updateButtonStatus()
//            }
//
//            btnNext.setOnClickListener {
//                if(currentPageIndex+1 < articlePageCount){
//                    currentPageIndex += 1
//                }
//                observeViewModel()
//                updateButtonStatus()
//            }
//        }
    }

    override fun onNextClick(view: View) {
        if(currentPageIndex+1 < articlePageCount){
            currentPageIndex += 1
        }
        observeViewModel()
        updateButtonStatus()
    }

    override fun onPreviousClick(view: View) {
        if(currentPageIndex > 0){
            currentPageIndex -= 1
        }
        observeViewModel()
        updateButtonStatus()
    }

    private fun observeViewModel(){
        viewModel.articleLD.observe(viewLifecycleOwner, Observer {
            binding.article = it
            with(binding){
//                txtTitle.text = it.title
//                txtAuthor.text = it.author_name

                val picasso = Picasso.Builder(requireContext())
                picasso.listener{picasso, uri, exception ->
                    exception.printStackTrace()
                }

                var imgUrl = "http://10.0.2.2/ANMP/ProjectTakeoff/images/" + (it.image_url)
                picasso.build().load(imgUrl).into(imgArticleImage, object: Callback {
                    override fun onSuccess(){

                    }

                    override fun onError(e: Exception?) {
                        Log.e("picasso_error", e.toString() + imgUrl)
                    }
                })
            }
        })

        viewModel.articleContentLD.observe(viewLifecycleOwner, Observer {
            binding.currentContent = it[currentPageIndex]
//            with(binding){
//                txtContent.text = it[currentPageIndex]
//            }
            articlePageCount = it.size
            updateButtonStatus()
        })
    }

    private fun updateButtonStatus(){
        with(binding){
            if(currentPageIndex == 0){
                btnPrev.isEnabled = false
            }
            else if(currentPageIndex < 0){
                currentPageIndex = 0
                btnPrev.isEnabled = false
            }
            else{
                btnPrev.isEnabled = true
            }


            if(currentPageIndex+1 == articlePageCount){
                btnNext.isEnabled = false
            }
            else if(currentPageIndex+1 > articlePageCount){
                currentPageIndex = articlePageCount
                btnNext.isEnabled = false
            }
            else{
                btnNext.isEnabled = true
            }
        }
    }

}