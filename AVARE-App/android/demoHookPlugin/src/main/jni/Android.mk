LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_SRC_FILES:= helloJni.c

LOCAL_LDLIBS    := -llog

LOCAL_MODULE:= helloJni

include $(BUILD_SHARED_LIBRARY)
