package com.example.imagesapp.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.imagesapp.R
import com.example.imagesapp.databinding.DetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.details_fragment.*

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {
    private val args by navArgs<DetailsFragmentArgs>()
    private var binding: DetailsFragmentBinding?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailsFragmentBinding.bind(view)

        binding.apply {
            val image = args.image
            Glide.with(this@DetailsFragment)
                .load(image.urls.full)
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress_circular.isVisible = false
                        return false // otherwise we will not see the image.
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress_circular.isVisible = false
                        description.isVisible = image.description != null
                        owner_txt.isVisible = true
                        return false
                    }
                })
                .error(R.drawable.ic_baseline_error_24)
                .into(image_view)
            description.text = image.description
            owner_txt.text = image.user.username
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}