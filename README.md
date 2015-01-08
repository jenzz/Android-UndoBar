Android - UndoBar
==============
As seen in Google's [Gmail](https://play.google.com/store/apps/details?id=com.google.android.gm) app.

* Simple Builder pattern
* Includes [three styles](https://github.com/jenzz/Android-UndoBar/blob/develop/library/src/main/java/com/jensdriller/libs/undobar/UndoBar.java#L24-50) (incl. Material Design [Snackbar](http://www.google.com/design/spec/components/snackbars-toasts.html))
* Supports **API Level >= 8**
(using [nineoldandroids](http://nineoldandroids.com/))
* **I18N** (about ~100 languages)

Screenshots
-----------
* pre KitKat

![alt text](https://raw.github.com/jenzz/Android-UndoBar/master/assets/Screenshot1.png "Undo Bar")
![alt text](https://raw.github.com/jenzz/Android-UndoBar/master/assets/Screenshot2.png "Undo Bar Pressed")

* KitKat

![alt text](https://raw.github.com/jenzz/Android-UndoBar/master/assets/Screenshot3.png "Undo Bar KitKat")
![alt text](https://raw.github.com/jenzz/Android-UndoBar/master/assets/Screenshot4.png "Undo Bar KitKat Pressed")

* Lollipop and later

![alt text](https://raw.github.com/jenzz/Android-UndoBar/master/assets/Screenshot5.png "Undo Bar Lollipop")
![alt text](https://raw.github.com/jenzz/Android-UndoBar/master/assets/Screenshot6.png "Undo Bar Lollipop Pressed")

Usage
-----
* It's as simple as:

```java
new UndoBar.Builder(this)//
  .setMessage("X items deleted.")//
  .setListener(this)//
  .show();
```

* You can also **explicitly** use one of the styles shown above. This is useful, for example, if you want to show a **consistent Material Design style** across all API levels. By default, it uses the style of the device's current API level. Here's an example:

```java
new UndoBar.Builder(this)//
  .setMessage("X items deleted.")//
  .setListener(this)//
  .setStyle(UndoBar.Style.LOLLIPOP)//
  .show();
```

Download
--------

Grab it via Gradle:

For **API Level >= 15**:

```groovy
compile 'com.github.jenzz.undobar:library:1.3:api15Release@aar'
```

For **API Level < 15** (includes [nineoldandroids](http://nineoldandroids.com/)):

```groovy
compile 'com.github.jenzz.undobar:library:1.3:api8Release@aar'
```

Still using Eclipse? Check out the [master_eclipse](https://github.com/jenzz/Android-UndoBar/tree/master_eclipse) branch and import it as a library project.<br />
Note that the Eclipse implementation is [v1.1](https://github.com/jenzz/Android-UndoBar/releases/tag/v1.1) only and won't receive any future updates.

License
-------
This project is licensed under the [MIT License](https://raw.githubusercontent.com/jenzz/Android-UndoBar/master/LICENSE).
