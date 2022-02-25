package com.software96.demoapp.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.software96.demoapp.R
import com.software96.demoapp.data.model.Languages
import com.software96.demoapp.databinding.FragmentListBinding
import com.software96.demoapp.ui.adapter.TrendListAdapter
import com.software96.demoapp.ui.adapter.TrendLoadStateAdapter
import com.software96.demoapp.ui.vm.ListViewModel
import com.software96.demoapp.utils.viewBindings
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private val _binding by viewBindings(FragmentListBinding::bind)
    private val binding get() = _binding

    private val viewModel by viewModels<ListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TrendListAdapter()
        binding.apply {
            recycler.apply {
                setHasFixedSize(true)
                itemAnimator = null
                this.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = TrendLoadStateAdapter { adapter.retry() },
                    footer = TrendLoadStateAdapter { adapter.retry() }
                )
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
            }

            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.trendListResponse.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progress.isVisible = loadState.source.refresh is LoadState.Loading
                recycler.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                error.isVisible = loadState.source.refresh is LoadState.Error

                // No results found
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recycler.isVisible = false
                    emptyTv.isVisible = true
                } else {
                    emptyTv.isVisible = false
                }
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        val searchAutoComplete: SearchView.SearchAutoComplete =
            searchView.findViewById(R.id.search_src_text)

        // Get SearchView Autocomplete Object
        searchAutoComplete.setTextColor(Color.WHITE)
        searchAutoComplete.setDropDownBackgroundResource(R.color.color_primary)

        val newsAdapter: ArrayAdapter<String> = ArrayAdapter(
            this.requireContext(),
            R.layout.item_dropdown,
            Languages.data
        )
        searchAutoComplete.setAdapter(newsAdapter)

        searchAutoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, itemIndex, _ ->
                val queryString = adapterView.getItemAtPosition(itemIndex) as String
                searchAutoComplete.setText(
                    String.format(
                        getString(R.string.search_query),
                        queryString
                    )
                )
                binding.recycler.scrollToPosition(0)
                val languageQuery = String.format(getString(R.string.query), queryString)
                viewModel.searchRepos(languageQuery)
                searchView.clearFocus()
                (activity as AppCompatActivity).supportActionBar?.title = queryString.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
            }
    }
}