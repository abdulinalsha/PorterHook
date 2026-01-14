package com.example.porterhook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        
        // Porter app ka package name check
        if (!lpparam.packageName.equals("com.theporter.android.driverapp")) {
            return;
        }

        // OkHttp Header hook kar rahe hain taaki version spoof ho sake
        XposedHelpers.findAndHookMethod(
            "okhttp3.Request$Builder", 
            lpparam.classLoader, 
            "addHeader", 
            String.class, 
            String.class, 
            new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    String key = (String) param.args[0];
                    
                    // Version check karke purana version bypass karna
                    if ("version".equals(key) || "versioncode".equals(key)) {
                        param.args[1] = "552"; // Naya version code
                    }
                    
                    if ("versionname".equals(key)) {
                        param.args[1] = "5.139.1"; // Naya version name
                    }
                }
            }
        );
    }
                                  }
