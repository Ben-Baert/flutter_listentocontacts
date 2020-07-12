package com.example.listentocontacts

import ContactsObserver
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.EventChannel.StreamHandler
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar


/** ListentocontactsPlugin */
class ListentocontactsPlugin : FlutterPlugin, MethodCallHandler, StreamHandler {
    private var applicationContext: Context? = null
    private lateinit var methodChannel: MethodChannel
    private lateinit var eventChannel: EventChannel
    private lateinit var contactsObserver: ContactsObserver
    private lateinit var handler: Handler

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        onAttachedToEngine(flutterPluginBinding.applicationContext, flutterPluginBinding.binaryMessenger)
    }

    private fun onAttachedToEngine(applicationContext: Context, messenger: BinaryMessenger) {
        this.applicationContext = applicationContext

      methodChannel = MethodChannel(messenger, "listentocontacts")
        methodChannel.setMethodCallHandler(this)


      eventChannel = EventChannel(messenger, "listentocontacts")
      eventChannel.setStreamHandler(this)

      handler = Handler(Looper.getMainLooper())
    }

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val methodChannel = MethodChannel(registrar.messenger(), "listentocontacts")
            methodChannel.setMethodCallHandler(ListentocontactsPlugin())

            val eventChannel = EventChannel(registrar.messenger(), "listentocontacts")
            eventChannel.setStreamHandler(ListentocontactsPlugin())
        }
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        result.notImplemented()

    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        methodChannel.setMethodCallHandler(null)
        eventChannel.setStreamHandler(null)
    }

    override fun onListen(arguments: Any?, events: EventSink) {
        contactsObserver = ContactsObserver(handler, events)
      applicationContext?.contentResolver?.registerContentObserver(ContactsContract.Contacts.CONTENT_URI, false, contactsObserver)
    }

    override fun onCancel(p0: Any?) {
        applicationContext?.contentResolver?.unregisterContentObserver(contactsObserver)
    }

}
