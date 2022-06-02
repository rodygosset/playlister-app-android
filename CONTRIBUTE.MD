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
   - The `android:layout_weight` attribute is used to scale the height of the XML element.  
   In a layout containing 2 elements, the element with the greatest `android:layout_weight` will take up most of the available space. For this attribute to have any effect, the elements must have `android:layout_height` set to `0dp`.

   - [Official documentation on `LinearLayout` and `android:layout_weight`](https://developer.android.com/guide/topics/ui/layout/linear)

   - [Quick article on the subject](https://www.educative.io/edpresso/what-is-layout-weight-in-android)

- Each piece of text that appears on screen must be declared in [`app/res/values/strings/strings.xml`](https://github.com/rodygosset/playlister-app-android/blob/main/app/src/main/res/values/strings.xml), and be available to use in English and in French. Please use the [translation editor](https://developer.android.com/studio/write/translations-editor) to declare each piece of text as a string value and provide both the English (as the default value) and the French translation.

For all activity layouts that contain a form, like all the '*Edit < data-model >*' pages, you can refer to [`app/res/layout/activity_login.xml`](https://github.com/rodygosset/playlister-app-android/blob/main/app/src/main/res/layout/activity_login.xml) and [`app/res/layout/activity_signup.xml`](https://github.com/rodygosset/playlister-app-android/blob/main/app/src/main/res/layout/activity_signup.xml)

For all activity layouts that display a data fecthed from the API, like the [`Songs`](https://www.figma.com/proto/0Y38eFUIdXLOKgUo0S2uns/Playlister-App?node-id=39%3A131&starting-point-node-id=138%3A2&scaling=scale-down) or the [`Genres`](https://www.figma.com/proto/0Y38eFUIdXLOKgUo0S2uns/Playlister-App?node-id=59%3A2&starting-point-node-id=138%3A2&scaling=scale-down) frames in the Figma project, you can refer to [`app/res/layout/activity_home.xml`](https://github.com/rodygosset/playlister-app-android/blob/main/app/src/main/res/layout/activity_home.xml).