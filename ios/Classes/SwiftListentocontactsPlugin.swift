import Contacts
import Flutter
import UIKit

public class SwiftListentocontactsPlugin: NSObject, FlutterPlugin, FlutterStreamHandler {
  var eventSink: FlutterEventSink?
  public static func register(with registrar: FlutterPluginRegistrar) {
    let instance = SwiftListentocontactsPlugin()

    let methodChannel = FlutterMethodChannel(name: "listentocontacts", binaryMessenger: registrar.messenger())
    registrar.addMethodCallDelegate(instance, channel: methodChannel)

    let eventChannel = FlutterEventChannel(name: "listentocontacts", binaryMessenger: registrar.messenger())
    eventChannel.setStreamHandler(instance)

    if #available(iOS 9, *) {
      NotificationCenter.default.addObserver(forName: NSNotification.Name.CNContactStoreDidChange, object: nil, queue: nil, using: instance.addressBookDidChange(_:))
    }
  }

  public func handle(_: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }

  private func addressBookDidChange(_: Notification) {
    eventSink?(true)
  }

  public func onListen(withArguments _: Any?, eventSink: @escaping FlutterEventSink) -> FlutterError? {
    self.eventSink = eventSink

    return nil
  }

  public func onCancel(withArguments _: Any?) -> FlutterError? {
    eventSink = nil
    return nil
  }
}
