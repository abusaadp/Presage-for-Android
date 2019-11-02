#include <jni.h>
#include <string>

std::string ConvertJString(JNIEnv* env, jstring str)
{
    const jsize len = env->GetStringUTFLength(str);
    const char* strChars = env->GetStringUTFChars(str, (jboolean *)0);

    std::string Result(strChars, len);

    env->ReleaseStringUTFChars(str, strChars);

    return Result;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_avazapp_presagesample_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_avazapp_presagesample_MainActivity_mulFromJNI(
        JNIEnv *env,
        jobject obj, jstring value, jint mul) {

    std::string sValue1 = ConvertJString( env, value );
    try {
        int res = std::stoi(sValue1) * mul;
        return env->NewStringUTF((std::to_string(res)).c_str());
    } catch (int x) {
        return env->NewStringUTF("Please wait");
    }

}
