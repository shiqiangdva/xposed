package com.sq.xp_rawquery_test;

import android.util.Log;
import android.widget.Toast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by Qs05 on 2017/3/21.
 */

public class Module implements IXposedHookLoadPackage{
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        hookQuery(loadPackageParam);
//        hookRawQuery(loadPackageParam);
//        hookRawQueryWithFactory(loadPackageParam);
    }

    private void hookQuery(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        try{
            Class hookClass = loadPackageParam.classLoader.loadClass("com.tencent.mmdb.database.SQLiteDatabase");
            XposedBridge.hookAllMethods(hookClass, "query", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                    StringBuilder sb = new StringBuilder();
                    for (Object arg : param.args) {
                        if (arg instanceof  String) {
                            sb.append("<==string==>");
                            sb.append(arg.toString());
                        } else  if (arg instanceof  String[]) {
                            String []subArgs = (String []) arg;
                            for (String str : subArgs) {
                                sb.append("<==[ ]==>");
                                sb.append(str);

                            }
                        }
                    }
                    XposedBridge.log("hookQuery的数据为：" + sb.toString());
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);

                }
            });
        }catch (Exception e){

        }

    }

    private void hookRawQuery(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        try {
            Class myClass = loadPackageParam.classLoader.loadClass("com.tencent.mmdb.database.SQLiteDatabase");
            XposedBridge.hookAllMethods(myClass, "rawQuery", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Object arg : param.args) {
                        if (arg instanceof String){
                            stringBuilder.append("----string----");
                            stringBuilder.append(arg);
                    }else if (arg instanceof String[]){
                            String[] subArgs = (String[]) arg;
                            for (String src : subArgs) {
                                stringBuilder.append("----[ ]----");
                                stringBuilder.append(src);
                            }
                        }
                    }
                    XposedBridge.log("hookRawQuery的数据为：" + stringBuilder.toString());

                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
//                    XposedBridge.log("hookRawQuery的结果为：" + param.getResult().toString());
                }
            });
        }catch (Exception e){

        }
    }

    private void hookRawQueryWithFactory(XC_LoadPackage.LoadPackageParam loadPackageParam) {

        try {
            Class secondClass = loadPackageParam.classLoader.loadClass("com.tencent.mmdb.database.SQLiteDatabase");
            XposedBridge.hookAllMethods(secondClass, "rawQueryWithFactory", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Object arg : param.args) {
                        if (arg instanceof String){
                            stringBuilder.append("++++string++++");
                            stringBuilder.append(arg);
                        }else if (arg instanceof String[]){
                            String[] src = (String[]) arg;
                            for (String s : src) {
                                stringBuilder.append("++++[ ]++++");
                                stringBuilder.append(s);
                            }
                        }
                    }
                    XposedBridge.log("hookRawQueryWithFactory的数据为：" + stringBuilder);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
//                    XposedBridge.log("hookRawQueryWithFactory的结果为：" + param.getResult().toString());
                }
            });
        }catch (Exception e){

        }

    }


}
