package com.example.leotvshowapp.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.leotvshowapp.BaseFragment
import com.example.leotvshowapp.R
import com.example.leotvshowapp.data.model.Episode
import com.example.leotvshowapp.data.model.TvShow
import com.example.leotvshowapp.databinding.TvShowDetailFragmentBinding
import com.example.leotvshowapp.show.adapters.EpisodeBySeasonAdapter
import com.example.leotvshowapp.utils.load
import com.example.leotvshowapp.utils.setHtmlText
import com.example.leotvshowapp.utils.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class TvShowDetailFragment : BaseFragment() {

    private val viewModel: TvShowAppViewModel by viewModels()

    private val args: TvShowDetailFragmentArgs by navArgs()
    private val show: TvShow
        get() = args.show

    private var _binding: TvShowDetailFragmentBinding? = null
    private val binding: TvShowDetailFragmentBinding
        get() = _binding!!

    @Inject
    lateinit var episodeBySeasonAdapter: EpisodeBySeasonAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return TvShowDetailFragmentBinding.inflate(inflater).let {
            _binding = it
            swipeRefresh = it.root
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            itemText.text = show.name
            itemImage.load(show.posterUrl, R.drawable.glide_show_fallback)
            show.schedule?.let {
                if (it.toString().isNotBlank())
                    showSchedule.setHtmlText(getString(R.string.show_detail_original_schedule, it.toString()))
            }
            showGenre.text = show.genres.joinToString()
            show.summary?.let { showSummary.setHtmlText(it) }
            setAdapter()
        }

        setCheckFavoriteCollector()
        setEpisodesCollector()
    }

    private fun setEpisodesCollector() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getEpisodes(show.id).collectLatest {
                it.handleResource(
                    doOnSuccessAction = { episodeBySeason -> updateAdapter(episodeBySeason) },
                    doOnErrorAction = { setEpisodesCollector() }
                )
            }
        }
    }

    private fun setCheckFavoriteCollector() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isFavorite(show.id).collectLatest {
                binding.addToFavoritesTb.apply {
                    isChecked = it
                    setOnCheckedChangeListener { _, isChecked ->
                        viewModel.updateFavorites(isChecked, show.id)
                    }
                }
            }
        }
    }

    private fun TvShowDetailFragmentBinding.setAdapter() {
        showEpisodesBySeasonRv.adapter = episodeBySeasonAdapter.apply {
            onEpisodeClick = {
                findNavController().navigate(TvShowDetailFragmentDirections.showToEpisodeAction(it))
            }
        }
    }

    private fun updateAdapter(episodeBySeason: List<Map.Entry<Int, List<Episode>>>) {
        episodeBySeasonAdapter.episodesBySeason = episodeBySeason
        binding.showEpisodesContainer.setVisible(episodeBySeason.isNotEmpty())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}