cmake_minimum_required(VERSION 2.8.3)

add_library(
    base_algorythm_lib
    SHARED
    src/main/cpp/simpleCPP.cpp )

include_directories(src/main/cpp)