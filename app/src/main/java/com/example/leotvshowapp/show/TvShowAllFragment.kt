package com.example.leotvshowapp.show

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
import com.example.leotvshowapp.databinding.TvShowAllFragmentBinding
import com.example.leotvshowapp.show.adapters.TvShowAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TvShowAllFragment : BaseFragment() {

    private val viewModel: TvShowAppViewModel by viewModels()

    @Inject
    lateinit var showAdapter: TvShowAdapter

    private var _binding: TvShowAllFragmentBinding? = null
    private val binding: TvShowAllFragmentBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return TvShowAllFragmentBinding.inflate(inflater).let {
            _binding = it
            swipeRefresh = it.root
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setAdapter()
            showSearchEt.doAfterTextChanged { searchShow() }
        }

        setSearchResultCollector()
        setSearchStateCollector()
    }

    private fun setSearchStateCollector() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            showAdapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    LoadState.Loading -> setLoading(true)
                    is LoadState.NotLoading -> setLoading(false)
                    is LoadState.Error -> handleError((it.refresh as LoadState.Error).error) { searchShow() }
                }
            }
        }
    }

    private fun setSearchResultCollector() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.showResultList.collectLatest {
                it.handleResourceWithSuspendAction(
                    doOnSuccessAction = { items -> showAdapter.submitData(items) },
                    doOnErrorAction = { setSearchResultCollector() }
                )
            }
        }
    }

    private fun searchShow() {
        viewModel.setShowQuery(binding.showSearchEt.text?.toString())
    }

    private fun TvShowAllFragmentBinding.setAdapter() {
        allShowsRv.adapter = showAdapter.apply {
            onItemClickListener = { findNavController().navigate(TvShowAllFragmentDirections.allToDetailAction(it)) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}