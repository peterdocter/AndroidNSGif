/*
 * JniWrapper.h
 *
 *  Created on: 2014-8-12
 *      Author: HZY
 */

#ifndef JNIWRAPPER_H_
#define JNIWRAPPER_H_

#include <jni.h>
#include <android/log.h>

#define LOG_TAG "jniLog"
#undef LOG
#define DEBUG

#ifdef DEBUG
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#else
#define LOGD(...) do{}while(0)
#endif

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,LOG_TAG,__VA_ARGS__)

#endif /* JNIWRAPPER_H_ */
