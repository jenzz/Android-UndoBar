Android - UndoBar
==============
As seen in Google's [Gmail](https://play.google.com/store/apps/details?id=com.google.android.gm) app.

* Simple Builder pattern
* Includes pre & post **KitKat** design
* Supports **API Level >= 8**
(using [nineoldandroids](http://nineoldandroids.com/))

Screenshots
-----------
* pre KitKat

![alt text](https://raw.github.com/jenzz/Android-UndoBar/master/assets/Screenshot1.png "Undo Bar")
![alt text](https://raw.github.com/jenzz/Android-UndoBar/master/assets/Screenshot2.png "Undo Bar Pressed")

* KitKat and later

![alt text](https://raw.github.com/jenzz/Android-UndoBar/master/assets/Screenshot3.png "Undo Bar KitKat")
![alt text](https://raw.github.com/jenzz/Android-UndoBar/master/assets/Screenshot4.png "Undo Bar KitKat Pressed")

Usage
-----
* It's as simple as:

```java
new UndoBar.Builder(this)//
  .setMessage("X items deleted.")//
  .setListener(this)//
  .show();
```

Download
--------

Grab it via Gradle:

For **API Level >= 15**:

```groovy
compile 'com.github.jenzz.undobar:library:1.1:api15Release@aar'
```

For **API Level < 15** (includes [nineoldandroids](http://nineoldandroids.com/)):

```groovy
compile 'com.github.jenzz.undobar:library:1.1:api8Release@aar'
```

Still using Eclipse? Check out the [master_eclipse](https://github.com/jenzz/Android-UndoBar/tree/master_eclipse) branch and import it as a library project.<br />
Note that the Eclipse implementation is [v1.1](https://github.com/jenzz/Android-UndoBar/releases/tag/v1.1) only and won't receive any future updates.

License
-------
This project is licensed under the [MIT License](https://raw.githubusercontent.com/jenzz/Android-UndoBar/master/LICENSE).
