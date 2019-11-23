package com.aranteknoloji.compiler;

final class NameStore {

    private NameStore() {
    }

    static String getPackageName() {
        return "com.aranteknoloji.intent.navigation";
    }

    static String getGeneratedMethodName(String clzName) {
        return "launch" + clzName;
    }

    static class Package {
        static final String ANDROID_CONTENT = "android.content";
        static final String ANDROID_APPLICATION = "android.app";
    }

    static class Class {
        // Android
        static final String ANDROID_INTENT = "Intent";
        static final String ANDROID_CONTEXT = "Context";
        static final String ANDROID_APPLICATION = "Application";
    }

    static class Method {
        // Android
        static final String ANDROID_VIEW_ON_CLICK = "onClick";

        // Binder
        static final String BIND_VIEWS = "bindViews";
        static final String BIND_ON_CLICKS = "bindOnClicks";
        static final String BIND = "bind";
    }

    static class Variable {
        static final String ANDROID_INTENT = "intent";
        static final String ANDROID_CONTEXT = "context";
        static final String ANDROID_APPLICATION = "application";
    }
}

