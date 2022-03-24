package com.example.leotvshowapp.people

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
import com.example.leotvshowapp.data.model.Person
import com.example.leotvshowapp.data.model.TvShow
import com.example.leotvshowapp.databinding.PersonDetailFragmentBinding
import com.example.leotvshowapp.people.adapters.CastCreditsAdapter
import com.example.leotvshowapp.utils.load
import com.example.leotvshowapp.utils.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class PersonDetailFragment : BaseFragment() {

    private val viewModel: PeopleViewModel by viewModels()

    private val args: PersonDetailFragmentArgs by navArgs()
    private val person: Person
        get() = args.person

    private var _binding: PersonDetailFragmentBinding? = null
    private val binding: PersonDetailFragmentBinding
        get() = _binding!!

    @Inject
    lateinit var castCreditsAdapter: CastCreditsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return PersonDetailFragmentBinding.inflate(inflater).let {
            _binding = it
            swipeRefresh = it.root
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCastCreditsCollector()

        binding.apply {
            personName.text = person.name
            personPicture.load(person.imageUrl, R.drawable.glide_person_fallback)
            setAdapter()
        }
    }

    private fun setCastCreditsCollector() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getPersonCastCredits(person.id).collectLatest {
                it.handleResource(
                    doOnSuccessAction = { creditShows -> updateAdapter(creditShows) },
                    doOnErrorAction = { setCastCreditsCollector() }
                )
            }
        }
    }

    private fun updateAdapter(creditShows: List<TvShow>) {
        castCreditsAdapter.shows = creditShows
        binding.personCastCreditsContainer.setVisible(creditShows.isNotEmpty())
    }

    private fun setAdapter() {
        if (binding.personCastCreditsRv.adapter == null)
            binding.personCastCreditsRv.adapter = castCreditsAdapter.apply {
                onShowClick = { findNavController().navigate(PersonDetailFragmentDirections.personDetailToShowDetailAction(it)) }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}