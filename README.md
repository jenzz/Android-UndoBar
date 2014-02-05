Android - UndoBar
==============
As seen in Google's [Gmail](https://play.google.com/store/apps/details?id=com.google.android.gm) app.

* Simple Builder pattern
* Includes pre & post **KitKat** design
* Supports **API Level >= 8**
(using [nineoldandroids](http://nineoldandroids.com/) and the latest [android-support-v4.jar](http://developer.android.com/tools/support-library/index.html))

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

License
-------
This project is licensed under the [MIT License](https://github.com/jenzz/Android-UndoBar/blob/master/LICENSE).
