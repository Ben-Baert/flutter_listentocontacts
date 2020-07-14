import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

class Listentocontacts {
  final EventChannel _eventChannel;
  Stream<void> _onContactsChanged;
  static Listentocontacts _instance;

  factory Listentocontacts() {
    if (_instance == null) {
      /// Initializes the plugin and starts listening for potential changes to contacts.
      final EventChannel eventChannel = const EventChannel('listentocontacts');
      _instance = Listentocontacts.private(eventChannel);
    }
    return _instance;
  }

  /// This constructor is only used for testing and shouldn't be accessed by
  /// users of the plugin. It may break or change at any time.
  @visibleForTesting
  Listentocontacts.private(this._eventChannel);

  /// Fires whenever the contacts are changed.
  /// No distinction can be made between a new contact added
  /// a contact being edited and a contact being deleted.
  /// The native APIs simply don't allow for this.
  Stream<void> get onContactsChanged {
    if (_onContactsChanged == null)
      _onContactsChanged = _eventChannel.receiveBroadcastStream();
    return _onContactsChanged;
  }
}
