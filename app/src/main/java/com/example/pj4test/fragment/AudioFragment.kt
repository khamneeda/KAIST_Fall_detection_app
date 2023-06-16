package com.example.pj4test.fragment

import com.example.pj4test.AudioFragmentListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pj4test.ProjectConfiguration
import com.example.pj4test.audioInference.SnapClassifier
import com.example.pj4test.databinding.FragmentAudioBinding

class AudioFragment: Fragment(), SnapClassifier.DetectorListener {
    private val TAG = "AudioFragment"

    private var _fragmentAudioBinding: FragmentAudioBinding? = null

    private val fragmentAudioBinding
        get() = _fragmentAudioBinding!!

    // classifiers
    lateinit var snapClassifier: SnapClassifier

    // views
    lateinit var snapView: TextView

    private var listener: AudioFragmentListener? = null

    fun setAudioFragmentListener(listener: AudioFragmentListener) {
        this.listener = listener
        if(listener==null){Log.d("MainActivity", "Hel111")}
        else{Log.d("MainActivity", "Hel222")}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentAudioBinding = FragmentAudioBinding.inflate(inflater, container, false)

        return fragmentAudioBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        snapView = fragmentAudioBinding.SnapView

        snapClassifier = SnapClassifier()
        snapClassifier.initialize(requireContext())
        snapClassifier.setDetectorListener(this)
        Log.d("MainActivity", "AudioFragment instance ID: ${System.identityHashCode(this)}")
    }

    override fun onPause() {
        super.onPause()
        snapClassifier.stopInferencing()
    }

    override fun onResume() {
        super.onResume()
        snapClassifier.startInferencing()
    }

    override fun onResults(score: Float) {
        activity?.runOnUiThread {
            if (score > SnapClassifier.THRESHOLD) {
                snapView.text = "FALL SOUND"
                snapView.setBackgroundColor(ProjectConfiguration.activeBackgroundColor)
                snapView.setTextColor(ProjectConfiguration.activeTextColor)
                this.listener?.onSnapDetected()
                var i = 0
                if(listener==null) {i =1}
                Log.d("MainActivity", "here:"+i)
                Log.d("MainActivity", "Hello World Hi")
            } else {
                snapView.text = "NO FALL SOUND"
                snapView.setBackgroundColor(ProjectConfiguration.idleBackgroundColor)
                snapView.setTextColor(ProjectConfiguration.idleTextColor)
                this.listener?.nonSnapDetected()
            }
        }
    }
}