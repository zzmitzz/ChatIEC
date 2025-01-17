package com.example.iec.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.iec.ui.feature.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

abstract class BaseFragment<T : ViewBinding, VM: BaseViewModel>: Fragment() {

    abstract val binding: T

    lateinit var viewModel: VM

    private val loadingDialog: LoadingDialogView by lazy {
        LoadingDialogView(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initDefaultState()
        initLogicState()
    }
    abstract fun initLogicState(): Unit
    abstract fun initViewModel(): Unit
    private fun initDefaultState() {
        viewModel.isLoading.onEach {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.hide()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun getParentActivity(): FragmentActivity = requireActivity()
}