micFilterPlugin
--------------------------

The micFilterPlugin controls the access to the microphone with the MediaRecorder class. It hooks the following methods:

- start()
- stop()
- setOutputFile(String path)

Notice: This plugin is just a protoype. For productive use, the providing of FakeData should be implemented. Other possibilities to access the microphone aren't controlled in the current version (e.g AudioRecord).
