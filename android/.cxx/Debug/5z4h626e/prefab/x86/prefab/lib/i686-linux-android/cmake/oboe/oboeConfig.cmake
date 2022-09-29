if(NOT TARGET oboe::oboe)
add_library(oboe::oboe SHARED IMPORTED)
set_target_properties(oboe::oboe PROPERTIES
    IMPORTED_LOCATION "C:/Users/Jimmy/.gradle/caches/transforms-3/1a9afd5a3b0c6acd88c7ec1f302f1b82/transformed/oboe-1.6.1/prefab/modules/oboe/libs/android.x86/liboboe.so"
    INTERFACE_INCLUDE_DIRECTORIES "C:/Users/Jimmy/.gradle/caches/transforms-3/1a9afd5a3b0c6acd88c7ec1f302f1b82/transformed/oboe-1.6.1/prefab/modules/oboe/include"
    INTERFACE_LINK_LIBRARIES ""
)
endif()

