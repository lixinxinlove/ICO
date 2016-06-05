package com.love.lixinxin.ico;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/6/5.
 */
public class ViewUtils {

    public static void inject(Activity activity) {
        //绑定控件
        try {
            bindView(activity);
            bindOnClick(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void bindOnClick(final Activity activity) {

        Class clazz = activity.getClass();
        Method[] declaredMethods = clazz.getDeclaredMethods();

        for (final Method method : declaredMethods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            
            if (onClick != null) {
                
                int resId = onClick.value();
                
                final View view = activity.findViewById(resId);
                
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        method.setAccessible(true);//必须暴力反射
                        try {
                            method.invoke(activity, view);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }
    }

    private static void bindView(Activity activity) throws IllegalAccessException {
        /*
         * 1. 获取Activity的字节码
		 */
        Class c = activity.getClass();
        /*
		 * 2. 获取到该字节码中的所有的Filed 字段
		 */
        Field[] declaredFields = c.getDeclaredFields(); 
        /*
		 * 3. 遍历字段，判断哪些是我们想要的字段（只有添加了ViewInject注解的字段）
		 */
        for (Field field : declaredFields) {
            //获取字段上面的注解
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                /*
				 * 4. 获取当前注解的值
				 */
                int resId = viewInject.value();
				/*
				 * 5.通过调用Activity的findViewById方法，获取当前id为resId的控件
				 */
                View view = activity.findViewById(resId);
				/*
				 * 6. 将当前的view设置给当前的Filed
				 */
                field.setAccessible(true);
                //给Activity对象的filed字段设置值为view对象
                field.set(activity, view);
            }
        }
    }
}
