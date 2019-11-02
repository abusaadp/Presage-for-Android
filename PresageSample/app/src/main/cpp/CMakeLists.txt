# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

file(GLOB CORE_SOURCES "presage/core/*.cpp")
file(GLOB CONTEXT_TRACKERS_SOURCES "presage/core/context_tracker/*.cpp")
file(GLOB TOKENIZER_SOURCES "presage/core/tokenizer/*.cpp")
file(GLOB PREDICTORS_SOURCES "presage/predictors/*.cpp")
file(GLOB DBCONNECTOR_SOURCES "presage/predictors/dbconnector/*.cpp")
file(GLOB SQLITE_SOURCES "presage/predictors/dbconnector/sqlite/*.c")
file(GLOB TIXML_SOURCES "presage/tinyxml/*.cpp")


SET(GCC_COVERAGE_COMPILE_FLAGS "-Winvalid-source-encoding -Wconstant-conversion")

add_definitions(${GCC_COVERAGE_COMPILE_FLAGS})
SET(CMAKE_CXX_FLAGS  "${CMAKE_CXX_FLAGS} ${GCC_COVERAGE_COMPILE_FLAGS}")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library(
        # Sets the name of the library.
        presage-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        native-lib.cpp
        presage/presage.cpp
        ${CORE_SOURCES}
        ${CONTEXT_TRACKERS_SOURCES}
        ${TOKENIZER_SOURCES}
        ${PREDICTORS_SOURCES}
        ${DBCONNECTOR_SOURCES}
        ${SQLITE_SOURCES}
        ${TIXML_SOURCES})

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       presage-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )