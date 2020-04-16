package fr.epf.ratpnav.ui.gallery

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.github.chrisbanes.photoview.PhotoView
import fr.epf.ratpnav.R


class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.carte, container, false)

        val photoView = root.findViewById(R.id.photo_view) as PhotoView
        photoView.setImageResource(R.drawable.plan_metro_paris)
        return root
    }
}
