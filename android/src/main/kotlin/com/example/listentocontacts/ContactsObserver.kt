import android.content.ContentResolver
import android.database.ContentObserver
import android.os.Handler
import android.provider.ContactsContract
import io.flutter.plugin.common.EventChannel

class ContactsObserver(handler: Handler, private val eventSink: EventChannel.EventSink) : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean) {
            eventSink.success(true)
        }
    }
