package com.example.iec.ui.feature.main.tools.components.documents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.iec.core.BaseFragment
import com.example.iec.databinding.FragmentDocsViewBinding
import com.example.iec.ui.feature.main.tools.ToolVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DocsViewFragment @Inject constructor() : BaseFragment<FragmentDocsViewBinding, ToolVM>() {


    override val binding: FragmentDocsViewBinding
        get() = FragmentDocsViewBinding.inflate(layoutInflater)
    override val viewModel: ToolVM by viewModels()
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

    companion object {
        private var instance: DocsViewFragment? = null
        fun newInstance(): DocsViewFragment {
            return if(instance == null){
                DocsViewFragment()
            }else{
                instance!!
            }
        }
    }
}