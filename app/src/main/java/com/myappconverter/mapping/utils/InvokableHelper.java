package com.myappconverter.mapping.utils;

import android.util.Log;

import java.lang.reflect.Method;

public class InvokableHelper {

    private static final String TAG = "InvokableHelper";


    public static Object invoke(Object target, String methodName, Object... parameter) {
        Method method = null;
        Object[] parameters = null;
        if (target == null || methodName == null)
            return null;
        try {
            if (parameter == null)
                method = target.getClass().getMethod(methodName, null);
            else {
                Class<?>[] parameterTypes = new Class<?>[parameter.length];
                for (int i = 0; i < parameter.length; i++) {
                    parameterTypes[i] = parameter[i].getClass();
                }
                method = target.getClass().getMethod(methodName, parameterTypes);
                parameters = parameter;
            }
        } catch (Exception e) {
           Log.e("NoSuchMethodException", methodName);
        }

        Method[] tabMethod;
        if (method == null) {
            tabMethod = target.getClass().getDeclaredMethods();//all methods declared in the class represented by target
            for (Method m : tabMethod) {
                if (m.getName().equals(methodName)) {
                    method = m;
                    Class<?>[] parametersMethod = m.getParameterTypes();
                    if (parameter.length >= parametersMethod.length) {
                        if (parametersMethod.length > 0) {
                            parameters = new Object[parametersMethod.length];
                            int cpt = 0;
                            for (Class clazz : parametersMethod) {
                                parameters[cpt] = parameter[cpt];
                                cpt++;
                            }
                        }
                    } else {
                        parameters = new Object[parametersMethod.length];

                        int cpt = 0;
                        int countParam = parameter.length;
                        for (Class clazz : parametersMethod) {
                            if (cpt < countParam)
                                parameters[cpt] = parameter[cpt];
                            else
                                parameters[cpt] = null;
                            cpt++;
                        }
                    }
                }
            }
            if (method == null) {// all public methods
                tabMethod = target.getClass().getMethods();
                for (Method m : tabMethod) {
                    if (m.getName().equals(methodName)) {
                        method = m;
                        Class<?>[] parametersMethod = m.getParameterTypes();
                        if (parameter.length >= parametersMethod.length) {
                            parameters = new Object[parametersMethod.length];

                            int cpt = 0;
                            for (Class clazz : parametersMethod) {
                                parameters[cpt] = parameter[cpt];
                                cpt++;
                            }
                        }
                    }
                }
            }
        }


        if (method != null) {
            try {
                if (parameters == null) {
                    return method.invoke(target);
                } else {
                    return method.invoke(target, parameters);
                }
            } catch (Exception e) {
                Log.e("Invokation Exception", methodName);
            }

        } else {
            return null;
        }

        return null;
    }


    public static Method getMethod(Object target, String methodName, Object... parameter) {
        Method method = null;
        Object[] parameters = null;
        if (target == null || methodName == null)
            return null;
        try {
            if (parameter == null)
                method = target.getClass().getMethod(methodName, null);
            else {
                Class<?>[] parameterTypes = new Class<?>[parameter.length];
                for (int i = 0; i < parameter.length; i++) {
                    parameterTypes[i] = parameter[i].getClass();
                }
                method = target.getClass().getMethod(methodName, parameterTypes);
                parameters = parameter;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Method[] tabMethod;
        if (method == null) {
            tabMethod = target.getClass().getDeclaredMethods();//all methods declared in the class represented by target
            for (Method m : tabMethod) {
                if (m.getName().equals(methodName)) {
                    method = m;
                    Class<?>[] parametersMethod = m.getParameterTypes();
                    if (parameter.length >= parametersMethod.length) {
                        if (parametersMethod.length > 0) {
                            parameters = new Object[parametersMethod.length];
                            int cpt = 0;
                            for (Class clazz : parametersMethod) {
                                parameters[cpt] = parameter[cpt];
                                cpt++;
                            }
                        }
                    } else {
                        parameters = new Object[parametersMethod.length];

                        int cpt = 0;
                        int countParam = parameter.length;
                        for (Class clazz : parametersMethod) {
                            if (cpt < countParam)
                                parameters[cpt] = parameter[cpt];
                            else
                                parameters[cpt] = null;
                            cpt++;
                        }
                    }
                }
            }
            if (method == null) {// all public methods
                tabMethod = target.getClass().getMethods();
                for (Method m : tabMethod) {
                    if (method.getName().equals(methodName)) {
                        method = m;
                        Class<?>[] parametersMethod = m.getParameterTypes();
                        if (parameter.length >= parametersMethod.length) {
                            parameters = new Object[parametersMethod.length];

                            int cpt = 0;
                            for (Class clazz : parametersMethod) {
                                parameters[cpt] = parameter[cpt];
                                cpt++;
                            }
                        }
                    }
                }
            }
        }
        return method;
    }
}
