package com.ubaya.projecttakeoff.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.projecttakeoff.R
import com.ubaya.projecttakeoff.databinding.FragmentArticleListBinding
import com.ubaya.projecttakeoff.viewmodel.ArticleListViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ArticleListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticleListFragment : Fragment() {
    private lateinit var binding: FragmentArticleListBinding
    private lateinit var viewModel: ArticleListViewModel
    private val articleListAdapter = ArticleListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_article_list, container, false)
        binding = FragmentArticleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ArticleListViewModel::class.java)
        viewModel.refresh()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = articleListAdapter

        observeViewModel()

//        binding.fabUserProfile.setOnClickListener{
//            val action = ArticleListFragmentDirections.actionArticleListFragmentToProfileFragment()
//            Navigation.findNavController(it).navigate(action)
//        }
    }

    private fun observeViewModel() {
        viewModel.articlesLD.observe(viewLifecycleOwner, Observer {
            articleListAdapter.updateArticleList(it)
        })
    }

}