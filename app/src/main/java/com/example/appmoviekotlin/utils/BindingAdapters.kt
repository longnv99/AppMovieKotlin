package com.example.appmoviekotlin.utils
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class BindingAdapters {

    companion object{
        @BindingAdapter("android:imageURL")
        @JvmStatic fun setImageURL(imageView:ImageView, URL:String?){
            imageView.alpha = 0f
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + URL).noFade().into(imageView, object : Callback{
                override fun onSuccess() {
                    imageView.animate().setDuration(300).alpha(1f).start()
                }

                override fun onError(e: Exception?) {
                    imageView.setImageResource(android.R.color.transparent)
                }

            })
        }

        @BindingAdapter("android:text")
        @JvmStatic
        fun setFloat(tv:TextView, value:Float){
            tv.setText(value.toString())
        }
    }

}