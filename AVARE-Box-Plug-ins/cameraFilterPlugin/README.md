Camera Filter Plugin
--------------------------

This plugin hooks the Android Camera API. (https://developer.android.com/reference/android/hardware/Camera)

It hooks the methods:

- open (int id) 
- takePicture (Camera.ShutterCallback shutter, Camera.PictureCallback raw, 
    Camera.PictureCallback postview, Camera.PictureCallback jpeg)
- some preview methods (actually their calls just can be logged)


Notice:
This plugin is just a protoype. For productive use, the Face Detection should be improved and the preview hooks should be fully implemented otherwise apps can use this backdoor to access the camera.
