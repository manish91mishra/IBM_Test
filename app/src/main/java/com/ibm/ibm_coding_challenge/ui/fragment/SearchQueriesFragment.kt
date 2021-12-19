package com.ibm.ibm_coding_challenge.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibm.ibm_coding_challenge.databinding.FragmentSearchQueriesBinding
import com.ibm.ibm_coding_challenge.model.db.LocationEntity
import com.ibm.ibm_coding_challenge.ui.adapter.SearchQueriesAdapter
import com.ibm.ibm_coding_challenge.utils.Utils
import com.ibm.ibm_coding_challenge.viewmodels.SearchQueriesViewModel
import com.ibm.ibm_coding_challenge.viewmodels.SearchQueriesViewModelFactory

class SearchQueriesFragment : Fragment() {

    private lateinit var searchQueriesViewModel: SearchQueriesViewModel
    private var _binding: FragmentSearchQueriesBinding? = null

    private val binding get() = _binding!!
    private val queries=ArrayList<LocationEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        searchQueriesViewModel =
            ViewModelProvider(this, SearchQueriesViewModelFactory(Utils.getRepo(requireActivity().application)))
                .get(SearchQueriesViewModel::class.java)

        _binding = FragmentSearchQueriesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerSearch.apply {
            layoutManager=LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            adapter= SearchQueriesAdapter(requireContext(),queries)
        }

        searchQueriesViewModel.searchList.observe(viewLifecycleOwner){
            queries.clear()
            queries.addAll(it)
            binding.recyclerSearch.adapter?.notifyDataSetChanged()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}