Camera Filter Plugin
--------------------------

This plugin hooks the Android Camera API. (https://developer.android.com/guide/topics/media/camera)

It hooks the methods:

- open(int id) 
- takePicture (Camera.ShutterCallback shutter, Camera.PictureCallback raw, 
    Camera.PictureCallback postview, Camera.PictureCallback jpeg)
- some preview methods (actually their calls just get logged)


Notice:
This plugin is just a protoype. For productive use, the Face Detection should be improved and the preview hooks should be fully implemented.
