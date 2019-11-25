package com.bugsnag.android

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameter
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
internal class ThreadSerializationTest {

    companion object {
        @JvmStatic
        @Parameters
        fun testCases(): Collection<Pair<JsonStream.Streamable, String>> {
            val stacktrace = arrayOf(
                StackTraceElement("", "run_func", "librunner.so", 5038),
                StackTraceElement("Runner", "runFunc", "Runner.java", 14),
                StackTraceElement("App", "launch", "App.java", 70)
            )

            val thread = Thread(24, "main-one", "ando", true, Stacktrace(
                stacktrace,
                emptySet(),
                NoopLogger
            ))

            val stacktrace1 = arrayOf(
                StackTraceElement("", "run_func", "librunner.so", 5038),
                StackTraceElement("Runner", "runFunc", "Runner.java", 14),
                StackTraceElement("App", "launch", "App.java", 70)
            )

            val thread1 = Thread(24, "main-one", "ando", false, Stacktrace(
                stacktrace1,
                emptySet(),
                NoopLogger
            ))
            return generateSerializationTestCases("thread", thread, thread1)
        }
    }

    @Parameter
    lateinit var testCase: Pair<Thread, String>

    @Test
    fun testJsonSerialisation() = verifyJsonMatches(testCase.first, testCase.second)
}
