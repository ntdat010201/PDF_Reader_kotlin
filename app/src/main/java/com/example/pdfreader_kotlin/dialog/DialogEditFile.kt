package com.example.pdfreader_kotlin.dialog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.pdfreader_kotlin.R
import com.example.pdfreader_kotlin.databinding.FragmentDialogEditFileBinding
import com.example.pdfreader_kotlin.model.ModelFileItem
import com.example.pdfreader_kotlin.utlis.FileIconUtil
import com.example.pdfreader_kotlin.viewmodel.FileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch


class DialogEditFile(
    private var file: ModelFileItem
) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDialogEditFileBinding
    private lateinit var fileViewModel: FileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogEditFileBinding.inflate(LayoutInflater.from(requireContext()))
        initData()
        initView()
        initListener()
        return binding.root
    }

    private fun initData() {
        fileViewModel = ViewModelProvider(requireActivity())[FileViewModel::class.java]

    }

    private fun initView() {
        binding.fileIcon.setImageResource(FileIconUtil.getFileIcon(file.type))
        binding.fileName.text = file.name
        binding.textPath.text = file.path
    }

    private fun initListener() {
        detailsFile()
        renameFile()
        shareFile()
        deletesFile()
        import()

    }

    private fun deletesFile() {
        binding.delete.setOnClickListener {
            val dialogDeleteFile = DialogDeleteFile(file)
            dialogDeleteFile.show(parentFragmentManager, dialogDeleteFile.tag)
            dismiss()
        }
    }

    private fun shareFile() {
        binding.share.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "file/*"
            shareIntent.putExtra(Intent.EXTRA_STREAM, file)
            startActivity(Intent.createChooser(shareIntent, "Sharing File!!"))
            Toast.makeText(requireContext(), "loading...", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    private fun renameFile() {
        binding.rename.setOnClickListener {
            val dialogRenameFile = DialogRenameFile(file)
            dialogRenameFile.show(parentFragmentManager, dialogRenameFile.tag)
            dismiss()
        }
    }

    private fun detailsFile() {
        binding.details.setOnClickListener {
            val dialogDetailsFile = DialogDetailsFile(file)
            dialogDetailsFile.show(parentFragmentManager, dialogDetailsFile.tag)
            dismiss()
        }
    }


    private fun import() {
       lifecycleScope.launch {
           val isFavorite = fileViewModel.isFileFavorite(file.path)
           if (isFavorite){
               binding.imgTick.setImageResource(R.drawable.ic_tick_yellow)
           } else {
               binding.imgTick.setImageResource(R.drawable.ic_tick)
           }

       }
        binding.imgTick.setOnClickListener {
            lifecycleScope.launch {
                val isFavorite = fileViewModel.isFileFavorite(file.path)
                if (isFavorite) { // nếu path tồn tại ko null
                    Log.d("DAT", "delete: ${file.path}")
                    fileViewModel.removeFavoriteFile(file)
                    binding.imgTick.setImageResource(R.drawable.ic_tick)
                    Toast.makeText(requireActivity(), "đã xóa", Toast.LENGTH_SHORT).show()
                } else { // ko tồn tại
                    Log.d("DAT", "add: ${file.path}")
                    binding.imgTick.setImageResource(R.drawable.ic_tick_yellow)
                    fileViewModel.addFavoriteFile(file)
                    Toast.makeText(requireActivity(), "đã thêm", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }


}