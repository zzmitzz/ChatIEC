package com.example.iec.ui.feature.main.tools.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.example.iec.R
import com.example.iec.core.BaseFragment
import com.example.iec.databinding.FragmentDocsViewBinding
import com.example.iec.ui.feature.main.tools.ToolVM

class DocsViewFragment : BaseFragment<FragmentDocsViewBinding, ToolVM>() {


    override val binding: FragmentDocsViewBinding
        get() = FragmentDocsViewBinding.inflate(layoutInflater)

    private lateinit var mAdapter: DocumentListRecyclerView

    override fun initLogicState() {

        viewModel.fakeTimeJob()
        binding.arrowBack.setOnClickListener{
            requireActivity().finish()
        }
        mAdapter = DocumentListRecyclerView()
        mAdapter.updateListData(listOf("Hướng dẫn check-in", "Sơ đồ hội trường", "Tài liệu"))
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = mAdapter
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }
    override fun initViewModel() {
        viewModel = ToolVM()
    }
    companion object {
        private var instance: DocsViewFragment? = null
        fun newInstance(): DocsViewFragment{
            return if(instance == null){
                DocsViewFragment()
            }else{
                instance!!
            }
        }
    }
}