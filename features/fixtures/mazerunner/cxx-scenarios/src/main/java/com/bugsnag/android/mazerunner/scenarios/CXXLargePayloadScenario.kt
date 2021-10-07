package com.bugsnag.android.mazerunner.scenarios

import android.content.Context
import com.bugsnag.android.Bugsnag
import com.bugsnag.android.Configuration

/**
 * Sends a native crash to Bugsnag which has a large amount of metadata + breadcrumbs
 * added at runtime.
 */
internal class CXXLargePayloadScenario(
    config: Configuration,
    context: Context,
    eventMetadata: String?
) : Scenario(config, context, eventMetadata) {

    init {
        config.enabledBreadcrumbTypes = emptySet()
    }

    private external fun crash()

    override fun startScenario() {
        super.startScenario()

        repeat(1000) { count ->
            Bugsnag.leaveBreadcrumb("Breadcrumb $count")
            Bugsnag.addMetadata("test", "key_$count", "$count")
        }
        crash()
    }
}
