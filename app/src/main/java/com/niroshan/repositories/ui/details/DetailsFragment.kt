package com.niroshan.repositories.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.niroshan.repositories.R
import com.niroshan.repositories.databinding.FragmentDetailsBinding
import com.niroshan.repositories.internal.AppUtils
import com.niroshan.repositories.internal.DateUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    private var mInterstitialAd: InterstitialAd? = null
    private var TAG = "DetailsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        _binding = FragmentDetailsBinding.bind(view)

        //initInterstitialAd()

        binding.apply {
            name.text = args.repo.name
            username.text = args.repo.owner.login
            language.text = args.repo.language
            description.text = args.repo.description

            avatar.apply {
                transitionName = args.repo.owner.avatar_url
                Glide.with(view)
                    .load(args.repo.owner.avatar_url)
                    .error(android.R.drawable.stat_notify_error)
                    .into(this)
            }

            adView.apply {
                this.loadAd(AdRequest.Builder().build())
                this.visibility = AdapterView.VISIBLE
            }

            stars.text = args.repo.stars.toString()
            forks.text = args.repo.forks.toString()
            watchers.text = args.repo.watchers.toString()
            issuesOpened.text = args.repo.openIssues.toString()
            createDate.text = DateUtils.formatDate(args.repo.createDate)
            updateDate.text = DateUtils.formatDate(args.repo.updateDate)
            btnBrowse.setOnClickListener {
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(activity)
                }
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(args.repo.url))
                startActivity(browserIntent)
            }

        }

        ViewCompat.setTransitionName(binding.avatar, "avatar_${args.repo.id}")

        setHasOptionsMenu(true)
    }

    private fun initInterstitialAd() {

        InterstitialAd.load(activity,
            AppUtils.DETAIL_PAGE_PROMO_AD_UNIT_ID, AdRequest.Builder().build(), object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {

            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {

            }

            override fun onAdShowedFullScreenContent() {
                mInterstitialAd = null;
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                view?.let { Navigation.findNavController(it).navigateUp() }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}