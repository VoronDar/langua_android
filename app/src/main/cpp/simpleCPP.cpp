

#include <jni.h>
#include <string>

#ifndef _Included_Sample1
#define _Included_Sample1
extern "C"
#endif


class ABD{
    int a = 0;
    int b = 1;
    int c = 3;
};

JNIEXPORT jstring

JNICALL
Java_com_example_langua_activities_mainPlain_getCPPString(JNIEnv *env, jobject/*this*/){
    ABD abd = ABD();
    //jclass localClass = env->FindClass(Object);
    //return env->NewObject(A);
}