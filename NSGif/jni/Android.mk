JNI_PATH := $(call my-dir)

LOCAL_PATH := $(JNI_PATH)/nsgif/

include $(CLEAR_VARS)

LOCAL_C_INCLUDES := $(LOCAL_PATH)/nsgif/

LOCAL_MODULE := nsgif
				
LOCAL_SRC_FILES := \
		./libnsgif.c \
\
		../com_hzy_nsgif_GifDecoder.c
        
LOCAL_LDLIBS := -llog -ljnigraphics

include $(BUILD_SHARED_LIBRARY)

