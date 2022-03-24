package com.example.leotvshowapp.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.leotvshowapp.BaseFragment
import com.example.leotvshowapp.R
import com.example.leotvshowapp.data.model.Episode
import com.example.leotvshowapp.databinding.TvShowEpisodeFragmentBinding
import com.example.leotvshowapp.utils.load
import com.example.leotvshowapp.utils.setHtmlText
import com.example.leotvshowapp.utils.setVisible

class TvShowEpisodeFragment : BaseFragment() {

    private val args: TvShowEpisodeFragmentArgs by navArgs()
    private val episode: Episode
        get() = args.episode

    private var _binding: TvShowEpisodeFragmentBinding? = null
    private val binding: TvShowEpisodeFragmentBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return TvShowEpisodeFragmentBinding.inflate(inflater).let {
            _binding = it
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            episode.image?.let {
                episodeImage.load(episode.image)
            } ?: kotlin.run { episodeImage.setVisible(false) }
            episodeName.text = episode.name
            episodeSeason.setHtmlText(getString(R.string.show_episode_season_number, episode.number, episode.season))
            episodeSummary.setHtmlText(episode.summary)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}