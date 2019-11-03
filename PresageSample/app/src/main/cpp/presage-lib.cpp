#include <jni.h>
#include <string>
#include <iostream>
#include <fstream>
#include "presage/presageCallback.h"
#include "presage/presage.h"

class ExampleCallback : public PresageCallback
{
public:
    ExampleCallback(const std::string _past_context, const std::string& path) {
        set_stream(_past_context);
        filePath = path;
    }

    std::string get_past_stream() const { return past_context; }
    std::string get_future_stream() const { return empty; }
    void set_stream(const std::string context) { past_context = context; }

private:
    std::string past_context;
    const std::string empty;


};


Presage *presage;
ExampleCallback *callback;

std::string ConvertJString(JNIEnv* env, jstring str)
{
    const jsize len = env->GetStringUTFLength(str);
    const char* strChars = env->GetStringUTFChars(str, (jboolean *)0);

    std::string Result(strChars, len);

    env->ReleaseStringUTFChars(str, strChars);

    return Result;
}



extern "C" void Java_com_avazapp_presagesample_MainActivity_PresageLib(JNIEnv *env,jobject obj, jstring dictPath) {
   std::string context = "sample";
   callback = new ExampleCallback (context,ConvertJString(env,dictPath));
   presage = new Presage(callback);
}

extern "C" JNIEXPORT jobjectArray  JNICALL
Java_com_avazapp_presagesample_MainActivity_getSuggesstionsForWord(JNIEnv *env, jobject obj, jstring word)
{
    std::vector< std::string > predictions;
    callback->set_stream(ConvertJString(env,word));

    // request prediction
    predictions = presage->predict ();
    int count = predictions.size();
    jobjectArray result = env->NewObjectArray(count, env->FindClass("java/lang/String"), NULL);

    for (int i = 0; i < count; i++) {
        jstring nsSuggestion = env->NewStringUTF(predictions[i].c_str());
        if (nsSuggestion == NULL) {
            std::cout << "Failed to convert [%s] to NSString", predictions[i].c_str();
        }else{
            env->SetObjectArrayElement(result,i,nsSuggestion);
        }
    }

    return result;
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


