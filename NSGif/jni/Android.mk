JNI_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_PATH := $(JNI_PATH)/nsgif
LOCAL_MODULE := nsgif

LOCAL_C_INCLUDES := \
		$(LOCAL_PATH)/nsgif/ \
		
LOCAL_SRC_FILES := \
		./libnsgif.c \
		./gif_decoder.c
		
LOCAL_LDLIBS := -llog -ljnigraphics

include $(BUILD_SHARED_LIBRARY)

