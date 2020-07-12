import 'dart:async';

import 'package:flutter/services.dart';

class Listentocontacts {
  static const MethodChannel _methodChannel =
      const MethodChannel('listentocontacts');

   static const EventChannel _eventChannel = const EventChannel("listentocontacts");

   Stream<void> _onContactsChanged;



   Stream<void> get onContactsChanged {
     if(_onContactsChanged == null)
      _onContactsChanged = _eventChannel.receiveBroadcastStream();
     return _onContactsChanged;
  }
}
