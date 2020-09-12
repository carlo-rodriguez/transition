package com.example.transition

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class TransitionHelper(intent: Intent, private var delegate: TransitionHelperDelegate) {

    val identifier = System.currentTimeMillis()
    private var oldIdentifier : Long = getId(intent)

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = getId(intent)
            if (id == identifier)
                delegate.stopActivity()
        }
    }

    fun register(context: Context){
        val filter = IntentFilter(EVENT_NAME)
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter)
    }

    fun unregister(context: Context){
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver)
    }

    fun clean(context: Context){
        if (oldIdentifier != 0L) {
            Handler(Looper.getMainLooper()).postDelayed({
                run {
                    val intent2 = Intent(EVENT_NAME)
                    intent2.putExtra(ID_NAME, oldIdentifier)
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent2)
                }
            }, 1000)
        }
    }

    companion object {

        const val EVENT_NAME = "custom-event-name"
        const val ID_NAME = "identifier"

        fun getId(intent: Intent) : Long {
            val idOpt = intent.extras?.get(ID_NAME) as Long?
            idOpt?.let {
                return it
            }
            return 0
        }
    }

}

interface TransitionHelperDelegate {
    fun stopActivity()
}