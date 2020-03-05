package com.free.good.dynamicwallpaper.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.TextView
import com.free.good.dynamicwallpaper.BaseActivity
import com.free.good.dynamicwallpaper.R

class SplashActivity : BaseActivity() {

    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //打印hash
//        try {
//            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//            info.signatures.forEach {
//                val md = MessageDigest.getInstance("SHA")
//                md.update(it.toByteArray())
//                val KeyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT)
//                Log.e("KeyHash->", KeyHash);
//            }
//        } catch (e : Exception) {
//            e.printStackTrace();
//        }

        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView)

        animatorStart()
    }

    fun animatorStart() {
        val set = AnimatorSet()
        val set2 = AnimatorSet()
        set.playTogether(
            ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f)
        )
        set2.playTogether(
            ObjectAnimator.ofFloat(textView, "translationY", 100f, 0f),
            ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f)
        )
        set.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                set2.setDuration(1500).start()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })

        set2.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                Handler().postDelayed(Runnable {
                    startActivity(
                        Intent(
                            this@SplashActivity,
                            MainActivity::class.java
                        )
                    )
                    finish()
                }, 1500)
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })

        set.setDuration(1500).start()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
