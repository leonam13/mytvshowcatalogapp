package com.example.leotvshowapp.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.leotvshowapp.BaseFragment
import com.example.leotvshowapp.databinding.PeopleSearchFragmentBinding
import com.example.leotvshowapp.people.adapters.PeopleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PeopleSearchFragment : BaseFragment() {

    private val viewModel: PeopleViewModel by viewModels()

    @Inject
    lateinit var peopleAdapter: PeopleAdapter

    private var _binding: PeopleSearchFragmentBinding? = null
    private val binding: PeopleSearchFragmentBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return PeopleSearchFragmentBinding.inflate(inflater).let {
            _binding = it
            swipeRefresh = it.root
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            searchPeopleRv.adapter = peopleAdapter.apply {
                onItemClickListener = { findNavController().navigate(PeopleSearchFragmentDirections.peopleToDetailAction(it)) }
            }

            searchPeopleEt.doAfterTextChanged { searchPeople() }
        }

        setSearchResultCollector()
        setSearchStateCollector()
    }

    private fun setSearchStateCollector() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            peopleAdapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    LoadState.Loading -> setLoading(true)
                    is LoadState.NotLoading -> setLoading(false)
                    is LoadState.Error -> handleError((it.refresh as LoadState.Error).error) { searchPeople() }
                }
            }
        }
    }

    private fun setSearchResultCollector() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.searchPersonResult.collectLatest {
                it.handleResourceWithSuspendAction(
                    doOnSuccessAction = { items -> peopleAdapter.submitData(items) },
                    doOnErrorAction = { setSearchResultCollector() }
                )
            }
        }
    }

    private fun searchPeople() {
        viewModel.setPersonQuery(binding.searchPeopleEt.text?.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}