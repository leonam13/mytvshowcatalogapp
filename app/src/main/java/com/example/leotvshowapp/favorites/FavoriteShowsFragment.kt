package com.example.leotvshowapp.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.leotvshowapp.BaseFragment
import com.example.leotvshowapp.databinding.FavoriteShowFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteShowsFragment : BaseFragment() {

    private var _binding: FavoriteShowFragmentBinding? = null
    private val binding: FavoriteShowFragmentBinding
        get() = _binding!!

    private val viewModel: FavoriteShowViewModel by viewModels()

    @Inject
    lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FavoriteShowFragmentBinding.inflate(inflater).let {
            _binding = it
            swipeRefresh = it.root
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setAdapter()
            showSearchEt.doAfterTextChanged { viewModel.setShowQuery(it?.toString()) }
        }
        setSearchResultCollector()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun setSearchResultCollector() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.searchShowResult.collectLatest {
                it.handleResource(
                    doOnSuccessAction = { items -> favoritesAdapter.shows = items },
                    doOnErrorAction = { setSearchResultCollector() }
                )
            }
        }
    }

    private fun FavoriteShowFragmentBinding.setAdapter() {
        favoriteShowRv.adapter = favoritesAdapter.apply {
            onItemClickListener = {
                findNavController().navigate(FavoriteShowsFragmentDirections.favoritesToShowDetailAction(it))
            }

            onRemoveClick = { viewModel.removeFromFavorites(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}