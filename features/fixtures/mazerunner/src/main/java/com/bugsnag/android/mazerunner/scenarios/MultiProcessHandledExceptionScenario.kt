package com.bugsnag.android.mazerunner.scenarios

import android.content.Context
import com.bugsnag.android.Bugsnag
import com.bugsnag.android.Configuration
import com.bugsnag.android.mazerunner.BugsnagIntentParams

/**
 * Sends handled exceptions to Bugsnag from two different processes
 */
internal class MultiProcessHandledExceptionScenario(
    config: Configuration,
    context: Context,
    eventMetadata: String
) : Scenario(config, context, eventMetadata) {

    init {
        config.autoTrackSessions = false
    }

    override fun startScenario() {
        super.startScenario()
        if (!isRunningFromBackgroundService()) {
            launchMultiProcessService(
                BugsnagIntentParams.fromConfig(
                    config,
                    javaClass.simpleName,
                    eventMetadata
                )
            ) {
                Bugsnag.setUser("123", "jane@example.com", "Jane")
                Bugsnag.notify(generateException())
            }
        } else {
            Bugsnag.setUser("456", "joe@example.com", "Joe")
            Bugsnag.notify(generateException())
        }
    }

}
