# listentocontacts

This plugin allows you to listen to changes to the user's contacts.

It uses a `ContentObserver` in Android and the `NotificationCenter` in iOS.

## Getting Started

### Install the plugin

In your `pubspec.yaml`:

```yaml  
dependencies:  
    listentocontacts: ^0.0.1+3
``` 

### Add the required permissions in the AndroidManifest.xml and Info.plist file


```xml  
<uses-permission android:name="android.permission.READ_CONTACTS" />  
<uses-permission android:name="android.permission.WRITE_CONTACTS" />  
```  

```xml  
<key>NSContactsUsageDescription</key>  
<string>This app requires contacts access to function properly.</string>  
```  


### Ask for permission

This example uses [permission_handler](https://pub.dartlang.org/packages/permission_handler) but you can do this however you want.

```dart
Future<PermissionStatus> _getPermission() async {
    final PermissionStatus permission = await Permission.contacts.status;
    if (permission != PermissionStatus.granted &&
        permission != PermissionStatus.denied) {
      final Map<Permission, PermissionStatus> permissionStatus =
      await [Permission.contacts].request();
      return permissionStatus[Permission.contacts] ??
          PermissionStatus.undetermined;
    } else {
      return permission;
    }
  }

final PermissionStatus permissionStatus = await _getPermission();

```

### Initialise the listener
```dart
Listentocontacts().onContactsChanged.listen((_) {
      // do what you want to do here when your contacts have changed
    });
```

Note that this plugin does not query your contacts. You can use one of the other available packages for this.

For help getting started with Flutter, view our 
[online documentation](https://flutter.dev/docs), which offers tutorials, 
samples, guidance on mobile development, and a full API reference.
