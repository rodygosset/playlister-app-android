# Contribution Guide

This file contains indications for contributors to this project.

### Layout Integration

This consists in converting a frame present in the [Figma project](https://www.figma.com/file/0Y38eFUIdXLOKgUo0S2uns/Playlister-App) into a layout inside the Android Studio project.

To get started, you can follow these steps:

- Create a new *layout ressource file* in the `app/res/layout` directory.

- *Make sure the file name matches the following format* : `activity_<page-name>.xml`.  
It must be lower case, and spaces must be replaced with `_`.

- *Make the root element of the layout a LinearLayout* (refer to existing layout files, such as [this one](https://github.com/rodygosset/playlister-app-android/blob/main/app/src/main/res/layout/activity_home.xml)).  
To be safe, you may reuse the root `<LinearLayout/>` present in all the existing activity layouts, and keep its properties the same.

- In each activity layout, *keep the navigation bar exactly the same* by copying the xml code found in the [`app/res/layout/activity_home.xml`](https://github.com/rodygosset/playlister-app-android/blob/main/app/src/main/res/layout/activity_home.xml).  
Only its content, whether navigation buttons or a form, and its `android:layout_weight` attribute may change from one activity to another.

For all activity layouts that contain a form, like all the '*Edit < data-model >*' pages, you can refer to [`app/res/layout/activity_login.xml`](https://github.com/rodygosset/playlister-app-android/blob/main/app/src/main/res/layout/activity_login.xml) and [`app/res/layout/activity_signup.xml`](https://github.com/rodygosset/playlister-app-android/blob/main/app/src/main/res/layout/activity_signup.xml)

For all activity layouts that display a data fecthed from the API, like the `Songs` or the `Genres` activity layouts, you can refer to [`app/res/layout/activity_home.xml`](https://github.com/rodygosset/playlister-app-android/blob/main/app/src/main/res/layout/activity_home.xml).