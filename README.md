# listentocontacts

This plugin allows you to listen to changes to the user's contacts.

It uses a `ContentObserver` in Android and the `NotificationCenter` observer in iOS.

It does **not** tell you what contact has been added/edited/deleted. It only tells you that some change has been made to the cotacts.

If you want to know what change has been made, then you will have to query the contacts yourself. You may also need to maintain a copy in a local database to compare to.

## Getting Started

### Install the plugin

In your `pubspec.yaml`:

```yaml  
dependencies:  
    listentocontacts: ^0.0.1+5
``` 

### Add the required permissions

#### AndroidManifest.xml

```xml  
<uses-permission android:name="android.permission.READ_CONTACTS" />  
<uses-permission android:name="android.permission.WRITE_CONTACTS" />  
```  

#### Info.plist

```xml  
<key>NSContactsUsageDescription</key>  
<string>This app requires contacts access to function properly.</string>  
```  


### Ask for permission

This example uses [permission_handler](https://pub.dartlang.org/packages/permission_handler), but you can do this however you want.

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


For help getting started with Flutter, view our 
[online documentation](https://flutter.dev/docs), which offers tutorials, 
samples, guidance on mobile development, and a full API reference.
