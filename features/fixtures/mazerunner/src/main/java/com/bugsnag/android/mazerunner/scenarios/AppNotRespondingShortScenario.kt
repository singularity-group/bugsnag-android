package com.bugsnag.android.mazerunner.scenarios

import android.content.Context
import com.bugsnag.android.Bugsnag
import com.bugsnag.android.Configuration

/**
 * Stops the app from responding for a time period shorter than the default
 */
internal class AppNotRespondingShortScenario(config: Configuration,
                                  context: Context) : Scenario(config, context) {
    init {
        config.setAutoCaptureSessions(false)
        config.detectAnrs = true
    }

    override fun run() {
        super.run()
        Thread.sleep(3000)
    }

}
